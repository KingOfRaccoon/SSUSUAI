package com.castprogramms.ssusuai.ui.inalbum

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.castprogramms.ssusuai.MainActivity
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.InAlbumFragmentBinding
import com.castprogramms.ssusuai.ui.gallery.PhotosAdapter
import com.google.android.flexbox.*

class InAlbumFragment : Fragment(R.layout.in_album_fragment) {
    private lateinit var binding: InAlbumFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.setHasOptionsMenu(true)
        binding = InAlbumFragmentBinding.bind(view)
        binding.root.startNestedScroll(0)
        (requireActivity() as MainActivity).setHtmlText("Название альбома")
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val flexboxLayoutManager = FlexboxLayoutManager(requireContext()).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            alignItems = AlignItems.STRETCH
            justifyContent = JustifyContent.CENTER
        }

        binding.recyclerPhotosInAlbum.apply {
            postponeEnterTransition()
            doOnPreDraw {
                startPostponedEnterTransition()
            }
            layoutManager = flexboxLayoutManager
            adapter = PhotosAdapter()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            findNavController().popBackStack()
        return super.onOptionsItemSelected(item)
    }
}