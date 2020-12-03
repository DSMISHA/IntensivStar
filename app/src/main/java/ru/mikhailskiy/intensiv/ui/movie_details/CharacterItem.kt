package ru.mikhailskiy.intensiv.ui.movie_details

import com.squareup.picasso.Picasso
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_character.*
import ru.mikhailskiy.intensiv.BuildConfig
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.data.Character

class CharacterItem(
    private val content: Character/*,
//    private val onClick: ((character: Character) -> Unit)?*/
) : Item() {

    override fun getLayout() = R.layout.item_character

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.rv_character_name.text = content.name
        loadImage(viewHolder)

//        viewHolder.content.setOnClickListener {
//            //todo
//        }
    }


    private fun loadImage(viewHolder: GroupieViewHolder){
        if(!content.image.isNullOrEmpty()) {
            Picasso.get()
                .load(BuildConfig.BASE_POSTER_URL + content.image)
                .into(viewHolder.iv_character_image)
        }
    }

}