package com.castprogramms.ssusuai.ui.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.RegistrationActivity
import com.castprogramms.ssusuai.databinding.FragmentSplashBinding
import com.castprogramms.ssusuai.repository.Resource
import com.google.android.gms.auth.api.signin.GoogleSignIn
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : Fragment(R.layout.fragment_splash) {
    val viewModel: SplashViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSplashBinding.bind(view)

        val googleAccount = GoogleSignIn.getLastSignedInAccount(requireContext())
        if (googleAccount?.id != null) {
            viewModel.getUser(googleAccount.id!!).observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> {
                        findNavController()
                            .navigate(R.id.action_splashFragment_to_registrationFragment)
                    }
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        (requireActivity() as RegistrationActivity).goToMainActivity()
                    }
                }
            }
        } else {
            findNavController().navigate(R.id.action_splashFragment_to_authenticationFragment)
        }
    }
}