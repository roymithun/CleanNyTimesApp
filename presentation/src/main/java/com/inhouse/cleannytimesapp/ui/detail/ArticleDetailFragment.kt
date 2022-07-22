package com.inhouse.cleannytimesapp.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.inhouse.cleannytimesapp.R

class ArticleDetailFragment : Fragment(R.layout.fragment_article_detail) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val selectedArticle =
//            ArticleDetailFragmentArgs.fromBundle(requireArguments()).selectedArticle
//        val webView: WebView = view.findViewById(R.id.web_view_article)
//        webView.loadUrl(selectedArticle.url)
    }
}