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
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.movie_details_fragment.*
import ru.mikhailskiy.intensiv.BuildConfig
import ru.mikhailskiy.intensiv.MovieFinderApp
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.data.Cast
import ru.mikhailskiy.intensiv.data.MovieModel
import ru.mikhailskiy.intensiv.network.SimpleSubscriber

const val ARG_MOVIE_ID = "movieId"

class MovieDetailsFragment : Fragment() {

    private var movieId: Int? = null
    var movie: MovieModel? = null
    private var cast: Cast? = null

    private val compoDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    companion object {
        @JvmStatic
        fun newInstance(movieId: Int) =
            MovieDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_MOVIE_ID, movieId)
                }
            }
    }

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieId = it.getInt(ARG_MOVIE_ID)
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

        if(movie == null){
            loadDetailsData()
        }
    }

    private fun loadDetailsData(){
        if(movieId == null) return

        val dis = MovieFinderApp.instance?.getRestApi()?.getDetails(movieId!!)?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeWith(object : DetailsObserver(){})

        val dis2 = MovieFinderApp.instance?.getRestApi()?.getMovieCast(movieId!!)?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeWith(object : CastObserver(){})

        dis?.let { compoDisposable.add(dis) }
        dis2?.let { compoDisposable.add(dis2) }
    }

    private open inner class DetailsObserver : SimpleSubscriber<MovieModel>(){
        override fun onNext(it: MovieModel) {
            movie = it
            initPrimaryInfo()
        }
    }

    private open inner class CastObserver : SimpleSubscriber<Cast>(){
        override fun onNext(it: Cast) {
            cast = it
            initCastAdapter()
        }
    }

    private fun initPrimaryInfo(){
        loadImage()
        tv_movie_name.text = movie?.title
        tv_description.text = movie?.overview
        tv_studio.text = getProductionString()
        tv_genre.text = getGenresString()
        tv_year.text = movie?.releaseDate?.substring(0, 4)
        movie?.voteAverage?.let { movie_rating_details.rating = it/2 }
        onWatchClicked()
    }

    private fun loadImage(){
        if(!movie?.poster.isNullOrEmpty()) {
            Picasso.get()
                .load(BuildConfig.BASE_POSTER_URL + movie?.poster)
                .into(iv_movie_image)
        }
    }

    private fun getProductionString(): String{
        var sProd = ""
        movie?.productionCompanies?.forEach {
            sProd += it.title + ", "
        }
        return sProd.dropLast(2)
    }

    private fun getGenresString(): String{
        var sGenres = ""
        movie?.genres?.forEach {
            sGenres += it.title + ", "
        }
        return sGenres.dropLast(2)
    }


    private fun initCastAdapter(){
        rv_characters.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        val  charactersList = cast?.cast?.map {
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

    override fun onPause() {
        super.onPause()
        compoDisposable.clear()
    }
}