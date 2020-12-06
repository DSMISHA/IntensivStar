package ru.mikhailskiy.intensiv.ui.search

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.feed_fragment.*
import kotlinx.android.synthetic.main.feed_header.*
import ru.mikhailskiy.intensiv.MainActivity
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.data.MovieModel
import ru.mikhailskiy.intensiv.ui.afterTextChanged
import ru.mikhailskiy.intensiv.ui.feed.MainCardContainer
import ru.mikhailskiy.intensiv.ui.feed.MovieItem
import ru.mikhailskiy.intensiv.ui.movie_details.ARG_MOVIE_ID

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SearchFragment : Fragment(), SearchContract.View {
    private var param1: String? = null
    private var param2: String? = null

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }
    private val presenter: SearchContract.Presenter by lazy { SearchPresenter(/*repository*/) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        presenter.attachView(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchTerm = requireArguments().getString("search")
        search_toolbar.setText(searchTerm)

        search_toolbar.getEditable()?.afterTextChanged {
            it?.let{
                presenter.searchRequest(it.toString().trim())
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun showSearchResult(movies: List<MovieModel>?) {
        val moviesList: List<MainCardContainer>
        adapter.clear()

        if(movies.isNullOrEmpty()){
            moviesList = listOf(
                MainCardContainer(R.string.search_title, listOf())
            )
        }else{
            moviesList = listOf(
                MainCardContainer(
                    R.string.search_title,
                    movies.map {
                        MovieItem(it) { movie ->
                            openMovieDetails(
                                movie
                            )
                        }
                    }.toList()
                )
            )
        }

        movies_recycler_view.adapter = adapter.apply { addAll(moviesList) }
    }

    override fun showInputError() {
        animateViewShake(search_toolbar)
    }

    override fun showProgress(isVisible: Boolean) {
        activity?.runOnUiThread {
            (activity as MainActivity).showProgress(isVisible)
        }
    }

    private fun animateViewShake(view: View?) {
        if(view == null) return
        val property = "translationX"
        ObjectAnimator.ofFloat(view, property, 0f, 30f, -20f, 20f, -10f, 10f, 0f).setDuration(600)
            .start()
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

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }
}