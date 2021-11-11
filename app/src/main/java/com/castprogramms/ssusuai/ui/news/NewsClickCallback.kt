package com.castprogramms.ssusuai.ui.news

import com.castprogramms.ssusuai.databinding.ItemNewsBinding
import com.castprogramms.ssusuai.tools.New

interface NewsClickCallback {
    fun clickOnNews(position: Int, binding: ItemNewsBinding, new: New)
}