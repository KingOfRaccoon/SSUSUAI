package com.castprogramms.ssusuai.ui.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.ItemNewsBinding
import com.castprogramms.ssusuai.tools.New

class NewsAdapter(val callback: NewsClickCallback) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    val news = mutableListOf<New>()

    init {
        repeat(17) {
            news.add(
                New(
                    "A big-big-big Neeeeews name name!",
                    "Body 2: Lorem ipsum dolor sit amet, consectetur adipiscingit, sed do  tempor incididunt fkgjfkgjkjgfkjg",
                    titleImg = R.drawable.test_img_for_news.toString()
                )
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_news, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(news[position])
    }

    override fun getItemCount() = news.size

    inner class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemNewsBinding.bind(view)
        fun bind(new: New) {
            binding.root.setOnClickListener {
                callback.clickOnNews(adapterPosition, binding, new)
            }
        }
    }
}