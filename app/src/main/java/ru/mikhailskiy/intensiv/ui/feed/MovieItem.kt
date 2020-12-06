package ru.mikhailskiy.intensiv.ui.feed

import com.squareup.picasso.Picasso
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_with_text.*
import ru.mikhailskiy.intensiv.BuildConfig
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.data.Movie
import ru.mikhailskiy.intensiv.data.MovieModel

class MovieItem(
    private val content: MovieModel,
    private val onClick: (movie: MovieModel) -> Unit
) : Item() {

    override fun getLayout() = R.layout.item_with_text

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
        content.rating?.let { viewHolder.movieRating.rating = it }
    }

    private fun loadImage(viewHolder: GroupieViewHolder){
        if(!content.poster.isNullOrEmpty()) {
            Picasso.get()
                .load(BuildConfig.BASE_POSTER_URL + content.poster)
                .into(viewHolder.image_preview)
        }
    }

}