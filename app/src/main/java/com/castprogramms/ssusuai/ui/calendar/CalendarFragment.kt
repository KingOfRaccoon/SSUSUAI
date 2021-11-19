package com.castprogramms.ssusuai.ui.calendar

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView
import com.castprogramms.ssusuai.MainActivity
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.FragmentCalendarBinding
import com.castprogramms.ssusuai.tools.time.DataTime
import com.castprogramms.ssusuai.tools.ui.BounceEdgeEffectFactory
import com.castprogramms.ssusuai.tools.ui.CenterSmoothScroller
import com.castprogramms.ssusuai.tools.ui.SpanningLinearLayoutManager
import java.util.*

class CalendarFragment : Fragment(R.layout.fragment_calendar) {
    lateinit var binding: FragmentCalendarBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCalendarBinding.bind(view)
        val currentDate = DataTime.now()
        binding.textDay.text = currentDate.day.toString()
        binding.textDayWeek.text = currentDate.getDayOfWeek()
        binding.textMouthAndYear.text = currentDate.getMouthAndYear()

        val datesAdapter = DatesAdapter()
        datesAdapter.dates = generateDatesAdapter()
        binding.recyclerEventsSoon.adapter = SoonEventAdapter()
        binding.recyclerEvents.adapter = EventAdapter()
        binding.recyclerDates.layoutManager =
            LinearLayoutManager(requireContext(), HORIZONTAL, false)
        binding.recyclerDates.adapter = datesAdapter
        val smoothScroller = CenterSmoothScroller(requireContext())
        datesAdapter.dates.forEachIndexed { index, it ->
            if (it == currentDate) {
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
                if (it == currentDate) {
                    smoothScroller.targetPosition = index
                    Handler(Looper.getMainLooper()).postDelayed({
                        binding.recyclerDates.layoutManager?.startSmoothScroll(
                            smoothScroller
                        )
                    }, 2)
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

        Log.e("test", DataTime.now().dayOfWeek.toString())

        return dates
    }
}