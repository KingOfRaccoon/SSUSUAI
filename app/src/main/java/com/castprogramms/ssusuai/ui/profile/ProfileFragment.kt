package com.castprogramms.ssusuai.ui.profile

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.graphics.drawable.toAdaptiveIcon
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toIcon
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.castprogramms.ssusuai.MainActivity
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.FragmentProfileBinding
import com.castprogramms.ssusuai.repository.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val viewModel: ProfileViewModel by viewModel()
    private lateinit var binding: FragmentProfileBinding
    var uri = Uri.EMPTY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (requireActivity() as MainActivity).setHtmlText("Профиль")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        binding.root.startNestedScroll(0)
        (requireActivity() as MainActivity).slideUp()
        val adapter = VisitedEventAdapter()
        viewModel.getCommonUser().observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Error -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {
                    if (it.data != null) {
                        binding.fullNameProfile.text = it.data.getFullName()
                        setUserImg(it.data.img)
                        if (it.data.visitedEvents.isNotEmpty()) {
                            binding.layoutNoVisitedEvents.visibility = View.GONE
                            binding.recyclerVisitedEvents.visibility = View.VISIBLE
                            adapter.setVisitedEvents(it.data.visitedEvents.toMutableList())
                        }
                        else {
                            setEmptyMessage()
                        }
                    }
                }
            }
        })

        binding.buttonGoToCalendar.setOnClickListener {
            (requireActivity() as MainActivity).centerBNVClick()
        }
        binding.buttonEditProfile.setOnClickListener {
            ViewCompat.setTransitionName(binding.imageProfile, "image")
            val extra = FragmentNavigator.Extras.Builder()
                .addSharedElement(binding.imageProfile, binding.imageProfile.transitionName)
                .build()
            val bundle = Bundle().apply {
                putString("Uri", uri.toString())
            }
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment, bundle, null, extra)
        }
    }

    private fun setEmptyMessage() {
        binding.recyclerVisitedEvents.visibility = View.GONE
        binding.layoutNoVisitedEvents.visibility = View.VISIBLE
    }

    private fun setUserImg(img: String) {
        binding.imageProfile.background = null
        uri = Uri.parse(img)
        Glide.with(binding.imageProfile)
            .load(Uri.parse(img))
            .into(binding.imageProfile)
    }
}