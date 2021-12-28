package com.castprogramms.ssusuai.ui.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.ItemNewsBinding
import com.castprogramms.ssusuai.tools.New

class NewsAdapter(val callback: NewsClickCallback) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    var news = mutableListOf<New>()
    set(value) {
        field = value.sortedByDescending { it.date.getTimeAndDate() }.toMutableList()
        notifyDataSetChanged()
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

            Glide.with(itemView.context)
                .load(new.titleImg)
                .transform(CenterCrop())
                .into(binding.imageNewInCard)
            binding.titleNew.text = new.title
            binding.bodyNew.text = new.body
            binding.newDate.text = new.date.getServiceTime()
        }
    }
}