package com.castprogramms.ssusuai.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.ItemVisitedEventBinding

class VisitedEventAdapter: RecyclerView.Adapter<VisitedEventAdapter.VisitedEventViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VisitedEventViewHolder {
        return VisitedEventViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_visited_event, parent, false))
    }

    override fun onBindViewHolder(holder: VisitedEventViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = 2

    inner class VisitedEventViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = ItemVisitedEventBinding.bind(view)
        fun bind(){

        }
    }
}