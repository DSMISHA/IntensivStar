package ru.mikhailskiy.intensiv.ui.feed

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.feed_fragment.*
import kotlinx.android.synthetic.main.feed_header.*
import kotlinx.android.synthetic.main.search_toolbar.view.*
import ru.mikhailskiy.intensiv.MovieFinderApp
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.data.MovieModel
import ru.mikhailskiy.intensiv.network.RestApi
import ru.mikhailskiy.intensiv.ui.afterTextChanged
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
            //todo error handlng
            val dis = MovieFinderApp.instance?.getRestApi()?.getPopularMovies()?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe {

                    if (it?.result != null) {
                        initPopularSection(it.result)
                    } else {
                        initPopularSection(listOf())
                    }
                }

            //todo error handlng
            val dis2 = MovieFinderApp.instance?.getRestApi()?.getNewMovies()?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe {

                    if (it?.result != null) {
                        initUpcomingSection(it.result)
                    } else {
                        initUpcomingSection(listOf())
                    }
                }

            dis?.let { compoDisposable.add(dis) }
            dis2?.let { compoDisposable.add(dis2) }
        } else {
            movies_recycler_view.adapter = adapter
        }

        initSearchBar()
    }

    private fun initPopularSection(popularMovies: List<MovieModel>) {

        // Используя Мок-репозиторий получаем фэйковый список фильмов
        val moviesList = listOf(
            MainCardContainer(
                R.string.recommended,
                popularMovies.map {
                    MovieItem(it) { movie ->
                        openMovieDetails(
                            movie
                        )
                    }
                }.toList()
            )
        )

        movies_recycler_view.adapter = adapter.apply { addAll(moviesList) }

        // Используя Мок-репозиторий получаем фэйковый список фильмов
        // Чтобы отобразить второй ряд фильмов
//        val newMoviesList = listOf(
//            MainCardContainer(
//                R.string.upcoming,
//                MockRepository.getMovies().map {
//                    MovieItem(it) { movie ->
//                        openMovieDetails(movie)
//                    }
//                }.toList()
//            )
//        )
//
//        adapter.apply { addAll(newMoviesList) }
    }

    private fun initUpcomingSection(newMovies: List<MovieModel>) {

        // Используя Мок-репозиторий получаем фэйковый список фильмов
        val moviesList = listOf(
            MainCardContainer(
                R.string.recommended,
                newMovies.map {
                    MovieItem(it) { movie ->
                        openMovieDetails(
                            movie
                        )
                    }
                }.toList()
            )
        )

        movies_recycler_view.adapter = adapter.apply { addAll(moviesList) }

        // Используя Мок-репозиторий получаем фэйковый список фильмов
        // Чтобы отобразить второй ряд фильмов
//        val newMoviesList = listOf(
//            MainCardContainer(
//                R.string.upcoming,
//                MockRepository.getMovies().map {
//                    MovieItem(it) { movie ->
//                        openMovieDetails(movie)
//                    }
//                }.toList()
//            )
//        )
//
//        adapter.apply { addAll(newMoviesList) }
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

   /*     val bundle = Bundle()
        bundle.putSerializable(ARG_MOVIE, movie)
        findNavController().navigate(R.id.movie_details_fragment, bundle, options)*/
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