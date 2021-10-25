package com.castprogramms.ssusuai.ui.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.castprogramms.ssusuai.R

class NewsAdapter: RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.item_news, parent, false
        ))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = 7

    inner class NewsViewHolder(view: View): RecyclerView.ViewHolder(view){

        fun bind(){

        }
    }
}