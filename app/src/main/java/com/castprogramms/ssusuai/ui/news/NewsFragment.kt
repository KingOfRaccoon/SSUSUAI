package com.castprogramms.ssusuai.ui.news

import android.os.Bundle
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import com.castprogramms.ssusuai.MainActivity
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.FragmentNewsBinding
import com.castprogramms.ssusuai.tools.ui.BounceEdgeEffectFactory
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

class NewsFragment : Fragment(R.layout.fragment_news) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).setHtmlText("Лента новостей")
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        val binding = FragmentNewsBinding.bind(view)
        binding.recyclerNews.adapter = NewsAdapter()
        binding.recyclerNews.edgeEffectFactory = BounceEdgeEffectFactory()
        binding.recyclerNews.apply {
            postponeEnterTransition()
            viewTreeObserver
                .addOnPreDrawListener {
                    startPostponedEnterTransition()
                    true
                }
        }
        binding.recyclerNews.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }
}