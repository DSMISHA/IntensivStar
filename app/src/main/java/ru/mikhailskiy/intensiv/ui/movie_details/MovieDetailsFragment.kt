package ru.mikhailskiy.intensiv.ui.movie_details

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.feed_fragment.*
import kotlinx.android.synthetic.main.movie_details_fragment.*
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.data.Movie
import ru.mikhailskiy.intensiv.network.MovieApiClient

const val ARG_MOVIE = "movie"

class MovieDetailsFragment : Fragment() {

    private var movie: Movie? = null

    companion object {
        @JvmStatic
        fun newInstance(movie: Movie) =
            MovieDetailsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_MOVIE, movie)
                }
            }
    }

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movie = it.getSerializable(ARG_MOVIE) as Movie?
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.movie_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //fixme
        MovieApiClient.apiClient.getMovies()


        tv_movie_name.text = movie?.title
        tv_description.text = movie?.description
        tv_studio.text = movie?.studio
        tv_genre.text = movie?.genre
        tv_year.text = movie?.year.toString()
        onWatchClicked()

        if(adapter.itemCount == 0){
            initCharacterAdapter()
        }else{
            movies_recycler_view.adapter = adapter
        }
    }


    private fun initCharacterAdapter(){
        rv_characters.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        val  charactersList = movie?.characters?.map {
            CharacterItem(it)
        }?.toList()

        if(charactersList != null) {
            rv_characters.adapter = adapter.apply { addAll(charactersList) }
        }
    }


    private fun onWatchClicked(){
        btn_watch.setOnClickListener {
            val backgroundColor = getColor(R.color.blueDark)
            backgroundColor?.let { iv_btn_watch.setBackgroundColor(it) }
            iv_btn_watch.isEnabled = false

            Handler(Looper.getMainLooper()).postDelayed({
                val backgroundColor1 = getColor(R.color.detailsBtnGray)
                backgroundColor1?.let { iv_btn_watch.setBackgroundColor(it) }
                iv_btn_watch.isEnabled = true
            }, 200)
        }
    }

    private fun getColor(colorId: Int): Int? {
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            context?.getColor(colorId)
        } else {
            context?.resources?.getColor(colorId)
        }
    }

}