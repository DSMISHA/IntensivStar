package ru.mikhailskiy.intensiv.ui.tvshows

import com.squareup.picasso.Picasso
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_with_text.*
import ru.mikhailskiy.intensiv.BuildConfig
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.data.Movie
import ru.mikhailskiy.intensiv.data.MovieModel
import ru.mikhailskiy.intensiv.network.TvShowModel

class Tvshowitem(
    private val content: TvShowModel,
    private val onClick: (movie: TvShowModel) -> Unit
) : Item() {

    override fun getLayout() = R.layout.item_with_text_tvshows

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        setTitle(viewHolder)
        setItemClickListener(viewHolder)
        loadImage(viewHolder)
        setRating(viewHolder)
    }

    private fun setTitle(viewHolder: GroupieViewHolder){
        viewHolder.description.text = content.title
    }

    private fun setItemClickListener(viewHolder: GroupieViewHolder){
        viewHolder.content.setOnClickListener {
            onClick.invoke(content)
        }
    }

    private fun setRating(viewHolder: GroupieViewHolder){
        content.voteAverage?.let { viewHolder.movie_rating.rating = it }
    }

    private fun loadImage(viewHolder: GroupieViewHolder){
        if(!content.poster.isNullOrEmpty()) {
            Picasso.get()
                .load(BuildConfig.BASE_POSTER_URL + content.poster)
                .into(viewHolder.image_preview)
        }
    }


    /*private val content: Movie,
    private val onClick: ((movie: Movie) -> Unit)?
) : Item() {

    override fun getLayout() = R.layout.item_with_text_tvshows

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.description.text = content.title
        viewHolder.movie_rating.rating = content.rating
        viewHolder.content.setOnClickListener {
            onClick?.invoke(content)
        }

        // TODO Получать из модели
        Picasso.get()
            .load("https://m.media-amazon.com/images/M/MV5BYTk3MDljOWQtNGI2My00OTEzLTlhYjQtOTQ4ODM2MzUwY2IwXkEyXkFqcGdeQXVyNTIzOTk5ODM@._V1_.jpg")
            .into(viewHolder.image_preview)
    }*/
}