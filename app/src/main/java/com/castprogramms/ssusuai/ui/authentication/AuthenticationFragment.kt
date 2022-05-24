package com.castprogramms.ssusuai.ui.authentication

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.animation.*
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.RegistrationActivity
import com.castprogramms.ssusuai.databinding.FragmentAuthenticationBinding
import com.castprogramms.ssusuai.repository.Resource
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.concurrent.timerTask

class AuthenticationFragment : Fragment(R.layout.fragment_authentication) {
    val viewModel: AuthenticationViewModel by viewModel()
    lateinit var binding: FragmentAuthenticationBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAuthenticationBinding.bind(view)
        binding.enter.setOnClickListener {
            binding.icon.elevation = binding.enter.elevation
            signIn()
            binding.enter.doneLoadingAnimation(
                resources.getColor(R.color.white),
                resources.getDrawable(R.drawable.google_icon).toBitmap()
            )
            binding.enter.startMorphAnimation()
            object : CountDownTimer(1000, 1) {
                override fun onTick(p0: Long) {
                    binding.icon.rotation += 10
                }

                override fun onFinish() {}
            }.start()
        }
    }

    private fun signIn() {
        startActivityForResult(
            Intent(
                GoogleSignIn.getClient(
                    requireContext(),
                    viewModel.gso
                ).signInIntent
            ), 7
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 7) {
            GoogleSignIn.getSignedInAccountFromIntent(data).addOnCompleteListener {
                if (it.isSuccessful) {
                    val rotateAnimation = RotateAnimation(0f, 360f)
                    rotateAnimation.repeatMode = RotateAnimation.INFINITE
                    viewModel.getUser(it.result.id.toString()).observe(viewLifecycleOwner, {
                        when (it) {
                            is Resource.Error -> {
                                setTimerToGoRegistr()
                            }
                            is Resource.Loading -> {
                                binding.icon.startAnimation(rotateAnimation)
                            }
                            is Resource.Success -> {
                                binding.icon.clearAnimation()
                                (requireActivity() as RegistrationActivity).goToMainActivity()
                            }
                        }
                        binding.enter.revertAnimation {
                            binding.enter.setBackgroundResource(R.drawable.background_button)
                            binding.enter.text = "Успех"
                            binding.enter.isClickable = false
                            binding.icon.elevation = binding.enter.elevation
                        }

                        if (binding.icon.rotation % 360 != 0f) {
                            val needRotation =
                                (binding.icon.rotation.toInt() / 360 + 1) * 360 - binding.icon.rotation
                            binding.icon.animate().rotationBy(needRotation).setDuration(300)
                                .setInterpolator(AccelerateDecelerateInterpolator()).setListener(
                                    object : Animator.AnimatorListener {
                                        override fun onAnimationStart(p0: Animator?) {
                                            binding.icon.elevation = binding.enter.elevation
                                        }

                                        override fun onAnimationEnd(p0: Animator?) {
                                            binding.icon.elevation = binding.enter.elevation
                                        }

                                        override fun onAnimationCancel(p0: Animator?) {}

                                        override fun onAnimationRepeat(p0: Animator?) {}
                                    }
                                ).start()
                        }
                    })
                } else {
                    Snackbar.make(requireView(), "Возникла проблема :(", Snackbar.LENGTH_SHORT)
                        .show()
                    Log.e("firebase", it.exception?.message.toString())
                }
            }
        }
    }

    private fun setTimerToGoRegistr() {
        Timer().schedule(
            timerTask {
                binding.enter.post {
                    findNavController()
                        .navigate(R.id.action_authenticationFragment_to_registrationFragment)
                }
            }, 300
        )
    }
}