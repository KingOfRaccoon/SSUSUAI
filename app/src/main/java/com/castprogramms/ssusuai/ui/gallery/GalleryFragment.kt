package com.castprogramms.ssusuai.ui.gallery

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.castprogramms.ssusuai.MainActivity
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.FragmentGalleryBinding
import com.castprogramms.ssusuai.repository.Resource
import com.castprogramms.ssusuai.users.Admin
import com.castprogramms.ssusuai.users.CommonUser
import com.google.android.flexbox.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import org.koin.androidx.viewmodel.ext.android.viewModel

class GalleryFragment : Fragment(R.layout.fragment_gallery) {
    private val viewModel: GalleryViewModel by viewModel()
    private lateinit var binding: FragmentGalleryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (requireActivity() as MainActivity).setHtmlText("Галерея")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.add_album_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add_album -> {
                findNavController().navigate(R.id.action_galleryFragment_to_addAlbumFragment)
            }
        }
        return true
    }

    override fun onStart() {
        super.onStart()
        (requireActivity() as MainActivity).setHtmlText("Галерея")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGalleryBinding.bind(view)
        binding.root.startNestedScroll(0)

        val flexboxLayoutManager = FlexboxLayoutManager(requireContext()).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            alignItems = AlignItems.STRETCH
            justifyContent = JustifyContent.CENTER
        }

        binding.recyclerPhotos.apply {
            postponeEnterTransition()
            doOnPreDraw {
                startPostponedEnterTransition()
            }
            layoutManager = flexboxLayoutManager
            adapter = PhotosAdapter()
        }
        val userId = GoogleSignIn.getLastSignedInAccount(requireContext()).id
        viewModel.getUser(userId).observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Error -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {
                    if (userId != null) {
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
            }
        })

        binding.allAlbums.setOnClickListener {
            findNavController().navigate(R.id.action_galleryFragment_to_allAlbumsFragment)
        }
        binding.cardAlbumBig.setOnClickListener {
            findNavController().navigate(R.id.action_galleryFragment_to_inAlbumFragment)
        }
        binding.cardAlbumSmall1.setOnClickListener {
            findNavController().navigate(R.id.action_galleryFragment_to_inAlbumFragment)
        }
        binding.cardAlbumSmall2.setOnClickListener {
            findNavController().navigate(R.id.action_galleryFragment_to_inAlbumFragment)
        }
    }
}