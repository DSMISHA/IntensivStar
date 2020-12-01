package ru.mikhailskiy.intensiv.ui.search

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.feed_header.*
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.ui.SearchBar

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SearchFragment : Fragment(), SearchContract.View {
    private var param1: String? = null
    private var param2: String? = null

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

        search_toolbar.setOnTextChangeListener(object: SearchBar.OnTextChangeListener{
            override fun onTextChange(text: String) {
                presenter.searchRequest(text.trim())
            }
        })
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

    override fun showSearchResult() {
        TODO("Not yet implemented")
    }

    override fun showInputError() {
        animateViewShake(search_toolbar)
    }

    private fun animateViewShake(view: View?) {
        if(view == null) return
        val property = "translationX"
        ObjectAnimator.ofFloat(view, property, 0f, 30f, -20f, 20f, -10f, 10f, 0f).setDuration(600)
            .start()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }
}