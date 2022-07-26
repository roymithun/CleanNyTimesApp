package com.inhouse.cleannytimesapp.ui.main

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.inhouse.cleannytimesapp.R
import com.inhouse.cleannytimesapp.base.extension.textChanges
import com.inhouse.cleannytimesapp.databinding.FragmentArticleListBinding
import com.inhouse.cleannytimesapp.model.ArticleItem
import com.inhouse.cleannytimesapp.ui.main.adapter.ArticleListAdapter
import com.inhouse.cleannytimesapp.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ArticleListFragment : Fragment(), MavericksView {
    lateinit var binding: FragmentArticleListBinding
    lateinit var articleListAdapter: ArticleListAdapter
    private val viewModel: ArticleListViewModel by fragmentViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArticleListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = this@ArticleListFragment.viewLifecycleOwner
            articleListViewModel = viewModel

            // configure recycler view
            articleListAdapter = ArticleListAdapter(object : ArticleListAdapter.OnClickListener {
                override fun onClick(article: ArticleItem) {
                    viewModel.navigateToArticleDetail(article)
                }
            })
            rvArticleList.layoutManager = LinearLayoutManager(requireContext())
            rvArticleList.adapter = articleListAdapter
        }
    }

    @OptIn(FlowPreview::class)
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.options_menu, menu)
        val searchView = SearchView(requireContext())
        menu.findItem(R.id.search).apply {
            setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
            actionView = searchView
        }

        searchView.textChanges().debounce(Constants.DEBOUNCE_TIMEOUT)
            .onEach {
                it?.let {
                    if (it.isNotEmpty()) {
                        viewModel.searchArticles("%$it%")
                    }
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onStop() {
        super.onStop()
        hideKeyboard()
    }

    private fun hideKeyboard() {
        val imm: InputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    override fun invalidate() = withState(viewModel) {
        // do nothing for now
    }
}
