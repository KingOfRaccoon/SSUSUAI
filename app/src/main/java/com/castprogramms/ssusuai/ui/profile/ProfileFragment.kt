package com.castprogramms.ssusuai.ui.profile

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.FragmentProfileBinding
import com.castprogramms.ssusuai.repository.Resource
import com.google.android.gms.auth.api.signin.GoogleSignIn
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val viewModel: ProfileViewModel by viewModel()
    private lateinit var binding : FragmentProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        val googleAccount = GoogleSignIn.getLastSignedInAccount(requireContext())
        if (googleAccount != null)
            viewModel.getUser(googleAccount.id).observe(viewLifecycleOwner, {
                when (it) {
                    is Resource.Error -> {

                    }
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        if (it.data != null) {
                            binding.fullNameProfile.text = it.data.getFullName()
                            setUserImg(it.data.img)
                        }
                    }
                }
            })
        binding.recyclerVisitedEvents.adapter = VisitedEventAdapter()
    }

    private fun setUserImg(img: String) {
        binding.imageProfile.background = null
        Glide.with(binding.imageProfile)
            .load(Uri.parse(img))
            .into(binding.imageProfile)
    }
}