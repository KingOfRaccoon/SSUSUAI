package com.castprogramms.ssusuai.ui.calendar

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import com.castprogramms.ssusuai.MainActivity
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.FragmentCalendarBinding
import com.castprogramms.ssusuai.repository.Resource
import com.castprogramms.ssusuai.tools.time.DataTime
import com.castprogramms.ssusuai.tools.ui.CenterSmoothScroller
import com.castprogramms.ssusuai.users.Admin
import com.castprogramms.ssusuai.users.CommonUser
import com.google.android.gms.auth.api.signin.GoogleSignIn
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class CalendarFragment : Fragment(R.layout.fragment_calendar) {
    private val viewModel: CalendarViewModel by viewModel()
    private var currentIndex = 0
    lateinit var binding: FragmentCalendarBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCalendarBinding.bind(view)
        val smoothScroller = CenterSmoothScroller(requireContext())
        val currentDate = DataTime.now()
        binding.textDay.text = currentDate.day.toString()
        binding.textDayWeek.text = currentDate.getDayOfWeekText()
        binding.textMouthAndYear.text = currentDate.getMouthAndYear()
        val datesLayoutManager = LinearLayoutManager(requireContext(), HORIZONTAL, false)
        val datesAdapter =
            DatesAdapter(datesLayoutManager, object : DatesAdapter.OnDatesClickListener {
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
        val user = GoogleSignIn.getLastSignedInAccount(requireContext())
        if (user != null) {
            viewModel.getUser(user.id).observe(viewLifecycleOwner, {
                when (it) {
                    is Resource.Error -> {}
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        when (it.data) {
                            is CommonUser -> {
                                setHasOptionsMenu(false)
                            }
                            is Admin -> {
                                setHasOptionsMenu(true)
                            }
                        }
                    }
                }
            })
        }
        datesAdapter.dates = generateDatesAdapter()
        binding.recyclerEventsSoon.adapter = SoonEventAdapter()
        binding.recyclerEvents.adapter = EventAdapter()
        binding.recyclerDates.layoutManager = datesLayoutManager
        binding.recyclerDates.adapter = datesAdapter
        println(binding.recyclerDates.getChildAt(0))


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
                && it.getMouthAndYear() == currentDate.getMouthAndYear()
            ) {
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
                    && it.getMouthAndYear() == currentDate.getMouthAndYear()
                ) {
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.add_event_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add_event -> {
                findNavController().navigate(R.id.action_calendarFragment_to_addEventFragment2)
            }
        }
        return true
    }
}