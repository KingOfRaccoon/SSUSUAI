package com.castprogramms.ssusuai.ui.authentication

import android.animation.Animator
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.animation.*
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.FragmentAuthenticationBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.concurrent.timerTask

class AuthenticationFragment : Fragment(R.layout.fragment_authentication) {
    val viewModel: AuthenticationViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentAuthenticationBinding.bind(view)
        binding.enter.setOnClickListener {
            binding.icon.elevation = binding.enter.elevation
//            signIn()
            binding.enter.doneLoadingAnimation(
                resources.getColor(R.color.white),
                resources.getDrawable(R.drawable.google_icon).toBitmap()
            )
            binding.enter.startMorphAnimation()
            object : CountDownTimer(1000, 1) {
                override fun onTick(p0: Long) {
                    binding.icon.rotation += 10
                }

                override fun onFinish() {
                    binding.enter.revertAnimation {
                        it.setBackgroundResource(R.drawable.background_button)
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

                                    override fun onAnimationCancel(p0: Animator?) {
                                    }

                                    override fun onAnimationRepeat(p0: Animator?) {
                                    }
                                }
                            ).start()
                    }
                }
            }.start()
        }
    }
}