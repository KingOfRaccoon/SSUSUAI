package com.castprogramms.ssusuai.ui.allalbums

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.castprogramms.ssusuai.MainActivity
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.FragmentAllAlbumsBinding
import com.castprogramms.ssusuai.databinding.FragmentGalleryBinding
import com.castprogramms.ssusuai.tools.Event
import com.castprogramms.ssusuai.tools.time.DataTime

class AllAlbumsFragment : Fragment(R.layout.fragment_all_albums) {
    private lateinit var binding: FragmentAllAlbumsBinding

    val list : List<Event> = listOf(
        Event("Event number ONE", date = DataTime(2021, 6, 12, "12:30", 0)),
        Event("The big Buuuuuuuuulk!", date = DataTime(2021, 4, 2, "12:00", 0)),
        Event("Event number ONE", date = DataTime(2021, 1, 11, "20:30", 0)),
        Event("What's happened?", date = DataTime(2020, 12, 12, "12:30", 0)),
        Event("Event number ONE", date = DataTime(2020, 11, 24, "17:30", 0)),
        Event("What's happened?", date = DataTime(2020, 7, 1, "125:00", 0)),
        Event("Event number ONE", date = DataTime(2020, 5, 9, "12:30", 0))
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAllAlbumsBinding.bind(view)
        binding.root.startNestedScroll(0)
        (requireActivity() as MainActivity).setHtmlText("Альбомы")
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        binding.recyclerAlbums.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerAlbums.adapter = AllAlbumAdapter(list)

        binding.allCardAlbumBig.setOnClickListener {
            findNavController().navigate(R.id.action_allAlbumsFragment_to_inAlbumFragment)
        }

        binding.allCardAlbumSmall1.setOnClickListener {
            findNavController().navigate(R.id.action_allAlbumsFragment_to_inAlbumFragment)
        }

        binding.allCardAlbumSmall2.setOnClickListener {
            findNavController().navigate(R.id.action_allAlbumsFragment_to_inAlbumFragment)
        }
    }
}