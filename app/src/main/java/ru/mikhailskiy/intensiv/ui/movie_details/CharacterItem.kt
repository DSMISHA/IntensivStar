package ru.mikhailskiy.intensiv.ui.movie_details

import com.squareup.picasso.Picasso
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_character.*
import kotlinx.android.synthetic.main.item_with_text.*
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.data.Character
import ru.mikhailskiy.intensiv.data.Movie

class CharacterItem(
    private val content: Character/*,
    private val onClick: ((character: Character) -> Unit)?*/
) : Item() {

    override fun getLayout() = R.layout.item_character

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.rv_character_name.text = content.name
//        viewHolder.movie_rating.rating = content.rating
//        viewHolder.content.setOnClickListener {
//            //todo
//        }

        // TODO Получать из модели
        Picasso.get()
            .load("https://m.media-amazon.com/images/M/MV5BYTk3MDljOWQtNGI2My00OTEzLTlhYjQtOTQ4ODM2MzUwY2IwXkEyXkFqcGdeQXVyNTIzOTk5ODM@._V1_.jpg")
            .into(viewHolder.iv_character_image)
    }
}