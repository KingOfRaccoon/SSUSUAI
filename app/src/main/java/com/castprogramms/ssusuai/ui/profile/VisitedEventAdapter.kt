package com.castprogramms.ssusuai.ui.profile

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.ItemVisitedEventBinding
import com.castprogramms.ssusuai.tools.Event
import com.castprogramms.ssusuai.tools.time.DataTime

class VisitedEventAdapter : RecyclerView.Adapter<VisitedEventAdapter.VisitedEventViewHolder>() {
    private val visitedEventsList = mutableListOf<Event>()
    fun setVisitedEvents(mutableList: MutableList<Event>) {
        mutableList.sortByDescending {
            it.date.year.toString() + it.date.mouth.toString() + it.date.day.toString() + it.date.time
        }
        val needAddEvent = mutableList - visitedEventsList
        visitedEventsList.clear()
        visitedEventsList.addAll((mutableList + needAddEvent).toSet().toMutableList())
        visitedEventsList.sortByDescending {
            it.date.year.toString() + it.date.mouth.toString() + it.date.day.toString() + it.date.time
        }
        visitedEventsList.forEachIndexed { index, event ->
            if (event in needAddEvent){
                notifyItemInserted(index)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VisitedEventViewHolder {
        return VisitedEventViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_visited_event, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VisitedEventViewHolder, position: Int) {
        holder.bind(visitedEventsList[position])
    }

    override fun getItemCount() = visitedEventsList.size

    inner class VisitedEventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemVisitedEventBinding.bind(view)
        fun bind(event: Event) {
            Glide.with(binding.imageEvent)
                .load(Uri.parse(event.img))
                .into(binding.imageEvent)

            binding.nameEvent.text = event.name
            binding.dateEvent.text = event.date.getServiceTime()
        }
    }
}