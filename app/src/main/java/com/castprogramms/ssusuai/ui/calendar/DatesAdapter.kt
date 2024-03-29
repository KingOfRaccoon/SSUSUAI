package com.castprogramms.ssusuai.ui.calendar

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.ItemDateBinding
import com.castprogramms.ssusuai.tools.time.DataTime

class DatesAdapter(
    val layoutManager: LinearLayoutManager,
    private val onDatesClickListener: OnDatesClickListener
) :
    RecyclerView.Adapter<DatesAdapter.DatesViewHolder>() {
    var currentPosition = -1
    var dates = mutableListOf<DataTime>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DatesViewHolder {
        return DatesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_date, parent, false)
        ).apply {
//            parent.post {
                val height: Int = parent.measuredWidth / 7
                val view: View = this.itemView
                view.layoutParams.width = height
//            }
        }
    }

    override fun onBindViewHolder(holder: DatesViewHolder, position: Int) {
        holder.bind(dates[position], position)
        holder.setIsRecyclable(false)
    }

    override fun getItemCount() = dates.size

    inner class DatesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemDateBinding.bind(view)
        private val currentDay = DataTime.now()

        fun bind(dataTime: DataTime, position: Int) {
            binding.root.setOnClickListener {
                setCurrentItem(position)
                onDatesClickListener.clickOnDate(position)
            }

            if (currentPosition == -1 && dataTime.day == currentDay.day
                && dataTime.getMouthAndYear() == currentDay.getMouthAndYear()
            ) {
                currentPosition = position
                binding.containerDate.setBackgroundResource(R.drawable.current_day_background)
                binding.textDayNumber.setTextColor(Color.WHITE)
                binding.textDaySymbol.setTextColor(Color.WHITE)
            }

            binding.textDaySymbol.text = dataTime.getShortcutDayOfWeek()
            binding.textDayNumber.text = dataTime.day.toString()

            if (position == currentPosition){
                binding.containerDate.setBackgroundResource(R.drawable.current_day_background)
                binding.textDayNumber.setTextColor(Color.WHITE)
                binding.textDaySymbol.setTextColor(Color.WHITE)
            }

        }
    }

    interface OnDatesClickListener {
        fun clickOnDate(position: Int)
    }

    private fun getViewByPosition(pos: Int): View? {
        val firstListItemPosition: Int = layoutManager.findFirstVisibleItemPosition()
        val lastListItemPosition: Int = firstListItemPosition + layoutManager.childCount - 1
        return if (pos < firstListItemPosition || pos > lastListItemPosition) {
            null
        } else {
            val childIndex = pos - firstListItemPosition
            layoutManager.getChildAt(childIndex)
        }
    }

    private fun select(position: Int) {
//        dates.get(position).setStatus(true) //updating dataset
        if (currentPosition >= 0) {
            deselect(currentPosition)
        }
        val targetView = getViewByPosition(position)
        if (targetView != null) {
            // change the appearance
            val binding = ItemDateBinding.bind(targetView)
//            if ()
            binding.containerDate.setBackgroundResource(R.drawable.current_day_background)
            binding.textDayNumber.setTextColor(Color.WHITE)
            binding.textDaySymbol.setTextColor(Color.WHITE)
            binding.containerDate.elevation = 20f
        }
        onDatesClickListener.clickOnDate(position)
        currentPosition = position
    }


    private fun deselect(position: Int) {
//        dataSet.get(position).setStatus(false) //updating dataset
        if (getViewByPosition(position) != null) {
            val targetView = getViewByPosition(position)
            if (targetView != null) {
                // change the appearance
                val binding = ItemDateBinding.bind(targetView)
                binding.containerDate.setBackgroundResource(R.drawable.background_item_date)
                binding.textDayNumber.setTextColor(Color.parseColor("#212525"))
                binding.textDaySymbol.setTextColor(Color.parseColor("#BCC1CD"))
                binding.containerDate.elevation = 5f
            }
        }
        currentPosition = -1
    }

    fun setCurrentItem(position: Int){
        select(position)
    }
}