package com.castprogramms.ssusuai.ui.calendar

import android.os.Bundle
import android.os.Handler
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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class CalendarFragment : Fragment(R.layout.fragment_calendar) {
    private val viewModel: CalendarViewModel by viewModel()
    private var currentIndex = -1
    lateinit var binding: FragmentCalendarBinding
    lateinit var smoothScroller: CenterSmoothScroller


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCalendarBinding.bind(view)
        smoothScroller = CenterSmoothScroller(requireContext())
        val currentDate = DataTime.now()
        val dates = generateDatesAdapter()
        viewModel.loadEvents(currentDate.toString())
        binding.textDay.text = currentDate.day.toString()
        binding.textDayWeek.text = currentDate.getDayOfWeekText()
        binding.textMouthAndYear.text = currentDate.getMouthAndYear()
        val datesLayoutManager = LinearLayoutManager(requireContext(), HORIZONTAL, false)
        val datesAdapter =
            DatesAdapter(datesLayoutManager, object : DatesAdapter.OnDatesClickListener {
                override fun clickOnDate(position: Int) {
                    if (position != currentIndex) {
                        changeCurrentDay(position)
                        viewModel.loadEvents(dates[position].toString())
                    }
                }
            }).apply {
                this.dates = dates
            }
        val user = GoogleSignIn.getLastSignedInAccount(requireContext())
        if (user?.id != null) {
            viewModel.getUser(user.id!!).observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> {}
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        setHasOptionsMenu(it.data is Admin)
                    }
                }
            }
        }

        val eventsAdapter = EventAdapter()
        binding.recyclerEventsSoon.adapter = SoonEventAdapter()
        binding.recyclerEvents.adapter = eventsAdapter
        binding.recyclerDates.layoutManager = datesLayoutManager
        binding.recyclerDates.adapter = datesAdapter

        datesAdapter.dates.forEachIndexed { index, it ->
            if (it.day == currentDate.day
                && it.getMouthAndYear() == currentDate.getMouthAndYear()
            ) {
                changeCurrentDay(index)
            }
        }

        binding.buttonToday.setOnClickListener {
            datesAdapter.dates.forEachIndexed { index, it ->
                if (it.day == currentDate.day
                    && it.getMouthAndYear() == currentDate.getMouthAndYear()
                ) {
                    changeCurrentDay(index)
                    datesAdapter.setCurrentItem(index)
                    viewModel.loadEvents(dates[index].toString())
                }
            }
        }

        (requireActivity() as MainActivity).setHtmlText("Календарь мероприятий")

        viewModel.liveDataEvents.observe(viewLifecycleOwner){
            when(it){
                is Resource.Error -> {
                    if (it.message == "null"){
                        binding.recyclerEvents.visibility = View.GONE
                        binding.textTitleTime.visibility = View.GONE
                        binding.textTitleEvent.visibility = View.GONE
                        binding.buttonFilterEvent.visibility = View.GONE
                        binding.noEventsContainer.visibility = View.VISIBLE
                    }
                }
                is Resource.Loading -> {
//                    TODO анимация загрузки данных
                }
                is Resource.Success -> {
                    if (it.data != null) {
                        if (it.data.events.isNotEmpty()) {
                            eventsAdapter.events = it.data.events
                            binding.recyclerEvents.visibility = View.VISIBLE
                            binding.textTitleTime.visibility = View.VISIBLE
                            binding.textTitleEvent.visibility = View.VISIBLE
                            binding.buttonFilterEvent.visibility = View.VISIBLE
                            binding.noEventsContainer.visibility = View.GONE
                        } else {
                            binding.recyclerEvents.visibility = View.GONE
                            binding.textTitleTime.visibility = View.GONE
                            binding.textTitleEvent.visibility = View.GONE
                            binding.buttonFilterEvent.visibility = View.GONE
                            binding.noEventsContainer.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun changeCurrentDay(position: Int) {
        currentIndex = position
        smoothScroller.targetPosition = position
        Handler(requireContext().mainLooper).postDelayed({
            binding.recyclerDates.layoutManager?.startSmoothScroll(
                smoothScroller
            )
        }, 2)
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