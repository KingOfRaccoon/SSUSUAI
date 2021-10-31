package com.castprogramms.ssusuai.ui.calendar

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.castprogramms.ssusuai.MainActivity
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.FragmentCalendarBinding

class CalendarFragment: Fragment(R.layout.fragment_calendar) {
    lateinit var binding: FragmentCalendarBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCalendarBinding.bind(view)
        binding.recyclerEventsSoon.adapter = SoonEventAdapter()
        binding.recyclerEvents.adapter = EventAdapter()
        binding.recyclerDates.adapter = DatesAdapter()
        (requireActivity() as MainActivity).setHtmlText("Календарь мероприятий")
    }
}