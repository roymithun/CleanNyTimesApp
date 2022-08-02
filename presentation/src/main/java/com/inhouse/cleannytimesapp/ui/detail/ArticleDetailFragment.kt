package com.inhouse.cleannytimesapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.inhouse.cleannytimesapp.databinding.FragmentArticleDetailBinding
import com.inhouse.cleannytimesapp.util.Constants.MEDIA_META_DATA_SIZE

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

        binding.article = selectedArticle

        selectedArticle.mediaList.firstOrNull()?.let { media ->
            binding.media = media
            media.mediaMetadataList.let { listMediaMetaData ->
                if (listMediaMetaData.size >= MEDIA_META_DATA_SIZE) {
                    binding.articlePhoto = listMediaMetaData[2].url
                }
            }
        }
        binding.executePendingBindings()
    }
}
