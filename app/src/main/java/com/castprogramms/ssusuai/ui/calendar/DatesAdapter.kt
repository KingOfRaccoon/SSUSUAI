package com.castprogramms.ssusuai.ui.calendar

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.ItemDateBinding
import com.castprogramms.ssusuai.tools.time.DataTime

class DatesAdapter: RecyclerView.Adapter<DatesAdapter.DatesViewHolder>() {
    var dates = mutableListOf<DataTime>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DatesViewHolder {
        return DatesViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_date, parent, false))
    }

    override fun onBindViewHolder(holder: DatesViewHolder, position: Int) {
        holder.bind(dates[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount() = dates.size

    inner class DatesViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ItemDateBinding.bind(view)

        fun bind(dataTime: DataTime){
            val currentDay = DataTime.now()
            if (dataTime.getMouthAndYear() == currentDay.getMouthAndYear() && dataTime.day == currentDay.day){
                binding.root.setBackgroundResource(R.drawable.current_day_background)
                binding.textDayNumber.setTextColor(Color.WHITE)
                binding.textDaySymbol.setTextColor(Color.WHITE)
            }
            else
                binding.root.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
            binding.textDaySymbol.text = dataTime.getShortcutDayOfWeek()
            binding.textDayNumber.text = dataTime.day.toString()
        }
    }
}