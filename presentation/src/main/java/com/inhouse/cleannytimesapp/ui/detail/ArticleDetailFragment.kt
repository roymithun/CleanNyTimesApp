package com.inhouse.cleannytimesapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.inhouse.cleannytimesapp.databinding.FragmentArticleDetailBinding

class ArticleDetailFragment : Fragment() {
    private lateinit var binding: FragmentArticleDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArticleDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val selectedArticle =
            ArticleDetailFragmentArgs.fromBundle(requireArguments()).selectedArticle

        selectedArticle.let {
            binding.article = it
            it.mediaList.firstOrNull()?.let { media ->
                binding.media = media
                media.mediaMetadataList.let { listMediaMetaData ->
                    if (listMediaMetaData.size >= 3) {
                        binding.articlePhoto = listMediaMetaData[2].url
                    }
                }
            }
            binding.executePendingBindings()
        }
    }
}
