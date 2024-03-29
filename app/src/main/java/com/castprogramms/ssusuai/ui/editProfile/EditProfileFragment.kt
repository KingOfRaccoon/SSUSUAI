package com.castprogramms.ssusuai.ui.editProfile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.MediaStore
import android.text.Editable
import android.view.MenuItem
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.castprogramms.ssusuai.MainActivity
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.FragmentEditProfileBinding
import com.castprogramms.ssusuai.repository.Resource
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {
    lateinit var binding: FragmentEditProfileBinding
    private val viewModel: EditProfileViewModel by viewModel()
    var googleAccount : GoogleSignInAccount? = null
    private val resultActivityGetPhoto = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){
            val dataImg = it.data
            if (dataImg != null) {
                val uri: Uri? = dataImg.data
                binding.userIcon.setImageURI(uri)
                if (uri != null && googleAccount?.id != null) {
                    viewModel.loadPhotoUser(uri, googleAccount?.id!!)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.setHasOptionsMenu(true)
        (requireActivity() as MainActivity).setHtmlText("Редактирование")
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding = FragmentEditProfileBinding.bind(view)
        googleAccount = GoogleSignIn.getLastSignedInAccount(requireContext())
        viewModel.getCommonUser().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {}
                is Resource.Loading -> {
                    binding.progress.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progress.visibility = View.GONE
                    if (it.data != null) {
                        binding.userName.text =
                            Editable.Factory.getInstance().newEditable(it.data.name)
                        binding.lastNameUser.text =
                            Editable.Factory.getInstance().newEditable(it.data.surname)
                        setUserImg(it.data.img)
                    }
                }
            }
        }

        val animImg: Animation = AlphaAnimation(0.3f, 1.0f)
        animImg.duration = 3100

        val countDownTimer = object : CountDownTimer(2900, 3000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.userIcon.startAnimation(animImg)
            }

            override fun onFinish() {
                binding.butTextEdit.visibility = View.GONE
            }
        }
        binding.userIcon.setOnClickListener {
            countDownTimer.start()
            binding.butTextEdit.visibility = View.VISIBLE
        }
        binding.butTextEdit.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            intent.putExtra("return-data", true)
            resultActivityGetPhoto.launch(intent)
        }

        binding.buttonEditNowProfile.setOnClickListener {
            if (!binding.userName.text.isNullOrEmpty() && !binding.lastNameUser.text.isNullOrEmpty() && googleAccount?.id != null){
                viewModel.updateUserFirstName(binding.userName.text.toString(), googleAccount?.id!!)
                viewModel.updateUserSecondName(binding.lastNameUser.text.toString(), googleAccount?.id!!)
                findNavController().popBackStack()
            }
        }
    }

    private fun setUserImg(img: String) {
        binding.userIcon.background = null
        Glide.with(binding.userIcon)
            .load(Uri.parse(img))
            .into(binding.userIcon)
    }

    override fun onPause() {
        super.onPause()
        viewModel.name = binding.userName.text.toString()
        viewModel.surname = binding.lastNameUser.text.toString()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            findNavController().popBackStack()
        return super.onOptionsItemSelected(item)
    }
}
