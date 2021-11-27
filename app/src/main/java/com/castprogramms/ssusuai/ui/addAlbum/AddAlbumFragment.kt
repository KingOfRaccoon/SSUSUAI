package com.castprogramms.ssusuai.ui.addAlbum

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.castprogramms.ssusuai.MainActivity
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.AddAlbumFragmentBinding
import com.castprogramms.ssusuai.databinding.FragmentNewsBinding
import com.castprogramms.ssusuai.repository.Resource
import com.castprogramms.ssusuai.ui.news.NewsViewModel
import com.castprogramms.ssusuai.users.Admin
import com.castprogramms.ssusuai.users.CommonUser
import com.google.android.gms.auth.api.signin.GoogleSignIn
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddAlbumFragment : Fragment(R.layout.add_album_fragment) {
    lateinit var binding: AddAlbumFragmentBinding
    private val viewModel: NewsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).setHtmlText("Новый альбом")
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as MainActivity?)?.slideUp()
        binding = AddAlbumFragmentBinding.bind(view)

    }
}