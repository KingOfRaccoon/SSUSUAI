package com.castprogramms.ssusuai.ui.registration

import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.RegistrationActivity
import com.castprogramms.ssusuai.databinding.FragmentRegistrationBinding
import com.castprogramms.ssusuai.repository.Resource
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.material.datepicker.MaterialDatePicker
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegistrationFragment : Fragment(R.layout.fragment_registration) {
    private val viewModel: RegistrationViewModel by viewModel()
    private lateinit var binding: FragmentRegistrationBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegistrationBinding.bind(view)
        binding.datePicker.setOnClickListener {
            binding.datePicker.error = null
            createDatePicker {
                val date = DateFormat.format("dd.MM.yyyy", it).toString()
                viewModel.setBirthday(date)
                binding.date.text = date
            }.show(childFragmentManager, "tag")
        }
        validate()
        val googleAccount = GoogleSignIn.getLastSignedInAccount(requireContext())
        binding.doneButton.setOnClickListener {
            if (googleAccount != null) {
                if (checkEmpty())
                    viewModel.createPerson(googleAccount.id, googleAccount.photoUrl.toString())
                        .observe(viewLifecycleOwner) {
                            when (it) {
                                is Resource.Error -> {
                                    Toast.makeText(
                                        requireContext(),
                                        it.message.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                is Resource.Loading -> {}
                                is Resource.Success -> {
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