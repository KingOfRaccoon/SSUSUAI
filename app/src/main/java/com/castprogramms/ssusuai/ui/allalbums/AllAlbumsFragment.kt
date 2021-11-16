package com.castprogramms.ssusuai.ui.allalbums

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.castprogramms.ssusuai.MainActivity
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.FragmentAllAlbumsBinding
import com.castprogramms.ssusuai.databinding.FragmentGalleryBinding

class AllAlbumsFragment : Fragment(R.layout.fragment_all_albums) {
    private lateinit var binding: FragmentAllAlbumsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAllAlbumsBinding.bind(view)

        binding.root.startNestedScroll(0)
        (requireActivity() as MainActivity).setHtmlText("Альбомы")
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}