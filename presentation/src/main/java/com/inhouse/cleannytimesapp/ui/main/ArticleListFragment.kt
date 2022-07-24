package com.inhouse.cleannytimesapp.ui.main

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.inhouse.cleannytimesapp.R
import com.inhouse.cleannytimesapp.databinding.FragmentArticleListBinding
import com.inhouse.cleannytimesapp.model.ArticleItem
import com.inhouse.cleannytimesapp.ui.main.adapter.ArticleListAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ArticleListFragment : Fragment() {
    lateinit var binding: FragmentArticleListBinding
    lateinit var articleListAdapter: ArticleListAdapter
    private val viewModel: ArticleListViewModel by viewModels()

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
                    viewModel.showArticleDetail(article)
                }
            })
            rvArticleList.layoutManager = LinearLayoutManager(requireContext())
            rvArticleList.adapter = articleListAdapter
        }

        // register observers
        viewModel.navigateToArticleDetail.observe(viewLifecycleOwner) {
            it?.let {
                findNavController().navigate(
                    ArticleListFragmentDirections.actionListToDetailFragment(
                        it
                    )
                ).also {
                    viewModel.doneNavigationToDetail()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.options_menu, menu)
        val searchView = SearchView(requireContext())
        menu.findItem(R.id.search).apply {
            setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
            actionView = searchView
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                var searchText = newText
                searchText = "%$searchText%"
//                articleListViewModel.articleList(searchText)
//                    .observe(viewLifecycleOwner) {
//                        it?.let { articleListAdapter.submitList(it) }
//                    }
                return false
            }
        })
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
}