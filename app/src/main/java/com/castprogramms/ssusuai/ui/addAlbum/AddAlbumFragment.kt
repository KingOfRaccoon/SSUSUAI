package com.castprogramms.ssusuai.ui.addAlbum

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.castprogramms.ssusuai.MainActivity
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.ui.news.NewsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddAlbumFragment : Fragment(R.layout.fragment_add_album) {
//    lateinit var binding: Add
    private val viewModel: NewsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).setHtmlText("Новый альбом")
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as MainActivity?)?.slideUp()
//        binding = AddAlbumFragmentBinding.bind(view)

    }
}