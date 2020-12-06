package ru.mikhailskiy.intensiv.ui.feed

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.feed_fragment.*
import kotlinx.android.synthetic.main.feed_header.*
import kotlinx.android.synthetic.main.search_toolbar.view.*
import ru.mikhailskiy.intensiv.MainActivity
import ru.mikhailskiy.intensiv.MovieFinderApp
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.data.MovieModel
import ru.mikhailskiy.intensiv.data.MovieWrapper
import ru.mikhailskiy.intensiv.ui.afterTextChanged
import ru.mikhailskiy.intensiv.ui.movie_details.ARG_MOVIE_ID
import timber.log.Timber

class FeedFragment : Fragment() {
    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.feed_fragment, container, false)
    }

    private val compoDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movies_recycler_view.layoutManager = LinearLayoutManager(context)
        movies_recycler_view.adapter = adapter.apply { addAll(listOf()) }

        //todo to implement presenter or something else
        //todo to add pagination

        if (adapter.itemCount == 0) {
            loadDataAndInitMovieSections()
        } else {
            movies_recycler_view.adapter = adapter
        }

        initSearchBar()
    }

    private fun loadDataAndInitMovieSections() {
        val obs1 = MovieFinderApp.instance?.getRestApi()?.getPopularMovies()
        val obs2 = MovieFinderApp.instance?.getRestApi()?.getWatchingMovies()
        val obs3 = MovieFinderApp.instance?.getRestApi()?.getNewMovies()

        val dis = Observable.zip(obs1, obs2, obs3,
            Function3<MovieWrapper?, MovieWrapper?, MovieWrapper?, List<List<MovieModel>?>> { t1, t2, t3 ->
                return@Function3 listOf(t1.result, t2.result, t3.result)
            }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete { (activity as MainActivity).showProgress(false) }
            .doOnSubscribe{ (activity as MainActivity).showProgress(true) }
            .doOnError { it.printStackTrace() }
            .subscribe {
                initSection(it[0], R.string.popular)
                initSection(it[1], R.string.watching)
                initSection(it[2], R.string.upcoming)
            }

        dis?.let { compoDisposable.add(dis) }
    }

    private fun initSection(movies: List<MovieModel>?, sectionHeaderId: Int) {
        if(movies == null){
            return
        }

        val moviesList = listOf(
            MainCardContainer(
                sectionHeaderId,
                movies.map {
                    MovieItem(it) { movie ->
                        openMovieDetails(
                            movie
                        )
                    }
                }.toList()
            )
        )
        movies_recycler_view.adapter = adapter.apply { addAll(moviesList) }
    }

    private fun initSearchBar() {
        search_toolbar.search_edit_text.afterTextChanged {
            Timber.d(it.toString())
            if (it.toString().length > 3) {
                openSearch(it.toString())
            }
        }
    }

    private fun openMovieDetails(movie: MovieModel) {
        val options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }

        val bundle = Bundle()
        bundle.putSerializable(ARG_MOVIE_ID, movie.id)
        findNavController().navigate(R.id.movie_details_fragment, bundle, options)
    }

    private fun openSearch(searchText: String) {
        val options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }

        val bundle = Bundle()
        bundle.putString("search", searchText)
        findNavController().navigate(R.id.search_dest, bundle, options)
    }

    override fun onPause() {
        super.onPause()
        compoDisposable.clear()
    }

    override fun onStop() {
        super.onStop()
        search_toolbar.clear()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    companion object {
//        const val API_KEY = BuildConfig.THE_MOVIE_DATABASE_API
    }
}