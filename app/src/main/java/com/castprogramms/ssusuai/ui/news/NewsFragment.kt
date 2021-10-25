package com.castprogramms.ssusuai.ui.news

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.FragmentNewsBinding

class NewsFragment: Fragment(R.layout.fragment_news) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentNewsBinding.bind(view)
        binding.recyclerNews.adapter = NewsAdapter()

    }
}