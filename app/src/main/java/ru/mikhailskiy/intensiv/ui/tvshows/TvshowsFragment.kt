package ru.mikhailskiy.intensiv.ui.tvshows

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_tv_shows.*
import ru.mikhailskiy.intensiv.MovieFinderApp
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.data.TvShowModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class TvShowsFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TvShowsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private var disposable: Disposable? = null

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_shows, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Добавляем recyclerView
        tvshows_recycler_view.layoutManager = LinearLayoutManager(context)
        if(adapter.itemCount == 0){
            disposable = MovieFinderApp.instance?.getRestApi()?.getTvShows()?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe {
                    if (it?.result != null) {
                        initTvshowsAdapter(it.result)
                    } else {
                        initTvshowsAdapter(listOf())
                    }
                }
        }else{
            tvshows_recycler_view.adapter = adapter
        }
    }

    private fun initTvshowsAdapter(popularTvShows: List<TvShowModel>) {
//        tvshows_recycler_view.adapter = adapter.apply { addAll(listOf()) }


        val seriesList = popularTvShows.map {
            Tvshowitem(it) { movie ->
                openMovieDetails(movie)
            }
        }.toList()

        tvshows_recycler_view.adapter = adapter.apply { addAll(seriesList) }
    }

    private fun openMovieDetails(movie: TvShowModel) {
        val options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }

        //todo click to drtails
        /*val bundle = Bundle()
        bundle.putSerializable(ARG_MOVIE, movie)
        findNavController().navigate(R.id.movie_details_fragment, bundle, options)*/
    }

    override fun onPause() {
        super.onPause()
        disposable?.let { if(!it.isDisposed) it.dispose() }
    }
}