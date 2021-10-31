package com.castprogramms.ssusuai.ui.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.ItemSoonEventBinding

class SoonEventAdapter: RecyclerView.Adapter<SoonEventAdapter.SoonEventViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoonEventViewHolder {
        return SoonEventViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_soon_event, parent, false))
    }

    override fun onBindViewHolder(holder: SoonEventViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = 6

    inner class SoonEventViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ItemSoonEventBinding.bind(view)

        fun bind(){

        }
    }
}