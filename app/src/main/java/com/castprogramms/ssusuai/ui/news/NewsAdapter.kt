package com.castprogramms.ssusuai.ui.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.ItemNewsBinding
import com.castprogramms.ssusuai.tools.NeedTools.toTransitionGroup

class NewsAdapter: RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.item_news, parent, false
        ))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = 17

    inner class NewsViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = ItemNewsBinding.bind(view)
        fun bind(position: Int) {
            binding.imageNewInCard.transitionName = "test$position"
            binding.root.setOnClickListener {
                val extra = FragmentNavigatorExtras(binding.imageNewInCard.toTransitionGroup())
                navigate(NewsFragmentDirections.actionNewsFragmentToNewFragment().apply {
                    setPosition(position)
                }, extra)
            }
        }

        private fun navigate(destination: NavDirections, extraInfo: FragmentNavigator.Extras) =
            with(itemView.findNavController()) {
                // 1
                currentDestination?.getAction(destination.actionId)
                    ?.let {
                        navigate(destination, extraInfo) //2
                    }

            }
    }
}