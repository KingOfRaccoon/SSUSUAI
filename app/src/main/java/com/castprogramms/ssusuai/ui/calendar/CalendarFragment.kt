package com.castprogramms.ssusuai.ui.calendar

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView
import com.castprogramms.ssusuai.MainActivity
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.FragmentCalendarBinding
import com.castprogramms.ssusuai.tools.ui.BounceEdgeEffectFactory
import com.castprogramms.ssusuai.tools.ui.SpanningLinearLayoutManager

class CalendarFragment: Fragment(R.layout.fragment_calendar) {
    lateinit var binding: FragmentCalendarBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding = FragmentCalendarBinding.bind(view)
        binding.recyclerEventsSoon.adapter = SoonEventAdapter()
        binding.recyclerEvents.adapter = EventAdapter()
        binding.recyclerDates.adapter = DatesAdapter()
        binding.recyclerDates.layoutManager = SpanningLinearLayoutManager(requireContext(), HORIZONTAL, false).apply {
        }
//        binding.recyclerEventsSoon.edgeEffectFactory = BounceEdgeEffectFactory()
        (requireActivity() as MainActivity).setHtmlText("Календарь мероприятий")
    }
}