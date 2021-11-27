package com.castprogramms.ssusuai.ui.calendar

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.FragmentCalendarBinding
import com.castprogramms.ssusuai.tools.time.DataTime
import com.castprogramms.ssusuai.tools.ui.CenterSmoothScroller
import java.util.*
import com.castprogramms.ssusuai.MainActivity

class CalendarFragment : Fragment(R.layout.fragment_calendar){
    private var currentIndex = 0
    lateinit var binding: FragmentCalendarBinding
    var dx = 0f
    var dy = 0f

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCalendarBinding.bind(view)
        val smoothScroller = CenterSmoothScroller(requireContext())
        val currentDate = DataTime.now()
        binding.textDay.text = currentDate.day.toString()
        binding.textDayWeek.text = currentDate.getDayOfWeek()
        binding.textMouthAndYear.text = currentDate.getMouthAndYear()
        val datesLayoutManager = LinearLayoutManager(requireContext(), HORIZONTAL, false)
        val datesAdapter = DatesAdapter(datesLayoutManager, object : DatesAdapter.OnDatesClickListener{
            override fun clickOnDate(position: Int) {
                currentIndex = position
                smoothScroller.targetPosition = position
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.recyclerDates.layoutManager?.startSmoothScroll(
                        smoothScroller
                    )
                }, 2)
            }
        })
        datesAdapter.dates = generateDatesAdapter()
        binding.recyclerEventsSoon.adapter = SoonEventAdapter()
        binding.recyclerEvents.adapter = EventAdapter()
        binding.recyclerDates.layoutManager = datesLayoutManager
        binding.recyclerDates.adapter = datesAdapter


//
//        binding.recyclerEvents.setOnTouchListener { v, event ->
//            when (event.action) {
//                MotionEvent.ACTION_DOWN -> {
//                    dx = v.x - event.rawX
//                    dy = v.y - event.rawY
//                    binding.root.isEnableScrolling = false
//                }
//                MotionEvent.ACTION_MOVE -> v.animate()
//                    .x(event.rawX + dx)
////                    .y(event.rawY + dy)
//                    .setDuration(0)
//                    .start()
//                MotionEvent.ACTION_UP -> {
//                    binding.root.isEnableScrolling = true
//                }
//                else -> return@setOnTouchListener false
//            }
//
//            return@setOnTouchListener true
//        }

        datesAdapter.dates.forEachIndexed { index, it ->
            if (it.day == currentDate.day
                && it.getMouthAndYear() == currentDate.getMouthAndYear()) {
                currentIndex = index
                smoothScroller.targetPosition = index
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.recyclerDates.layoutManager?.startSmoothScroll(
                        smoothScroller
                    )
                }, 2)
            }
        }

        binding.buttonToday.setOnClickListener {
            datesAdapter.dates.forEachIndexed { index, it ->
                if (it.day == currentDate.day
                    && it.getMouthAndYear() == currentDate.getMouthAndYear()) {
                    currentIndex = index

                    smoothScroller.targetPosition = index
                    Handler(Looper.getMainLooper()).postDelayed({
                        binding.recyclerDates.layoutManager?.startSmoothScroll(
                            smoothScroller
                        )
                    }, 2)
                    datesAdapter.setCurrentItem(index)
                }
            }
        }

        (requireActivity() as MainActivity).setHtmlText("Календарь мероприятий")
    }

    private fun generateDatesAdapter(): MutableList<DataTime> {
        val dates = mutableListOf<DataTime>()
        for (i in -7..30) {
            dates.add(DataTime(Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, i) }))
        }

        return dates
    }

}