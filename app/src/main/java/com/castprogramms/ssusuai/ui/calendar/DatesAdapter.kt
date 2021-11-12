package com.castprogramms.ssusuai.ui.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.ItemDateBinding

class DatesAdapter: RecyclerView.Adapter<DatesAdapter.DatesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DatesViewHolder {
        return DatesViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_date, parent, false))
    }

    override fun onBindViewHolder(holder: DatesViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = 9

    inner class DatesViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ItemDateBinding.bind(view)

        fun bind(){

        }
    }
}