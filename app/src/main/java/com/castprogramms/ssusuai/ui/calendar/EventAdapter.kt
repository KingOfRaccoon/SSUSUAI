package com.castprogramms.ssusuai.ui.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.ItemEventBinding

class EventAdapter: RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event, parent, false))
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = 3

    inner class EventViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ItemEventBinding.bind(view)

        fun bind(){
            binding.root.setOnClickListener {
                Toast.makeText(itemView.context, "dataTime", Toast.LENGTH_SHORT).show()
            }
        }
    }
}