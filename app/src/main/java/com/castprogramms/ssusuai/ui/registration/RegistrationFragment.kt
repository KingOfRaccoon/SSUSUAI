package com.castprogramms.ssusuai.ui.registration

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.format.DateFormat
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.setPadding
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.RegistrationActivity
import com.castprogramms.ssusuai.databinding.FragmentRegistrationBinding
import com.castprogramms.ssusuai.repository.Resource
import com.castprogramms.ssusuai.tools.Utils.collapse
import com.castprogramms.ssusuai.tools.Utils.expand
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.datepicker.MaterialDatePicker
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegistrationFragment : Fragment(R.layout.fragment_registration) {
    private val viewModel: RegistrationViewModel by viewModel()
    private lateinit var binding: FragmentRegistrationBinding
    private var imageUri = Uri.EMPTY

    private val cropActivityForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){
            val data = CropImage.getActivityResult(it.data).uri
            binding.imageUserRegistration.background = null
            binding.imageUserRegistration.setPadding(0)
            viewModel.googleAccount?.let { account ->
                viewModel.loadPhotoUser(data, account.id.toString())
            }
        }
    }

    private val activityForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK){
            val data = it.data
            if (data != null){
                val imageUri = data.data
                if (imageUri != null)
                    cropActivityForResult.launch(
                        CropImage.activity(imageUri)
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .getIntent(requireContext())
                    )
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegistrationBinding.bind(view)
        if (viewModel.googleAccount?.photoUrl != null)
            Glide.with(requireContext())
                .load(viewModel.googleAccount!!.photoUrl)
                .into(binding.imageUserRegistration)
        binding.datePicker.setOnClickListener {
            binding.datePicker.error = null
            createDatePicker {
                val date = DateFormat.format("dd.MM.yyyy", it).toString()
                viewModel.setBirthday(date)
                binding.date.text = date
            }.show(childFragmentManager, "tag")
        }
        binding.checkboxAdmin.setOnClickListener {
            if ((it as MaterialCheckBox).isChecked)
                expand(binding.firstPinView)
            else
                collapse(binding.firstPinView)
        }
        binding.imageUserRegistration.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI
            )
            intent.putExtra("return-data", true)
            activityForResult.launch(intent)
        }
        viewModel.lifeDataLoadPhoto.observe(viewLifecycleOwner){
            when(it){
                is Resource.Error -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {
                    if (it.data != null) {
                        imageUri = it.data
                        Glide.with(requireContext())
                            .load(it.data)
                            .transform(CircleCrop())
                            .into(binding.imageUserRegistration)
                    }
                }
            }
        }

        validate()
        val googleAccount = viewModel.googleAccount
        binding.doneButton.setOnClickListener {
            if (googleAccount?.id != null) {
                if (checkEmpty())
                    viewModel.createPerson(
                        googleAccount.id!!,
                        if (imageUri == Uri.EMPTY)
                            if (googleAccount.photoUrl != null)
                                googleAccount.photoUrl.toString()
                                else ""
                        else imageUri.toString(),
                        binding.firstPinView.text.toString(),
                        binding.checkboxAdmin.isChecked
                    ).observe(viewLifecycleOwner) {
                        when (it) {
                            is Resource.Error -> {
                                if (it.message != null)
                                    Toast.makeText(
                                        requireContext(),
                                        it.message.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                            }
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                println(it.data)
                                (requireActivity() as RegistrationActivity).goToMainActivity()
                            }
                        }
                    }
            }
        }
    }

    private fun checkEmpty(): Boolean {
        val listChecks = mutableListOf<Boolean>()
        if (binding.userNameText.text?.trim()?.isNotEmpty() != true) {
            listChecks.add(false)
            binding.userTextContainer.error = "Введите имя"
        }

        if (binding.surname.text?.trim()?.isNotEmpty() != true) {
            listChecks.add(false)
            binding.surnameContainer.error = "Введите фамилию"
        }

        if (binding.date.text.isEmpty()) {
            listChecks.add(false)
            binding.datePicker.error = "Укажите дату рождения"
        }

        return !listChecks.contains(false)
    }

    private fun validate() {
        binding.userNameText.addTextChangedListener {
            binding.userTextContainer.error = null
            if (it != null)
                viewModel.setName(it.trim().toString())
        }

        binding.surname.addTextChangedListener {
            binding.surnameContainer.error = null
            if (it != null)
                viewModel.setSurname(it.trim().toString())
        }
    }

    private fun createDatePicker(date: (millis: Long) -> Unit): MaterialDatePicker<Long> {
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Выбор даты рождения")
                .build()
        datePicker.addOnPositiveButtonClickListener {
            date(it)
        }
        return datePicker
    }
}