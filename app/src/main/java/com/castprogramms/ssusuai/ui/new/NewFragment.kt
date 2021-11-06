package com.castprogramms.ssusuai.ui.new

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.FragmentNewBinding

class NewFragment: Fragment(R.layout.fragment_new) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentNewBinding.bind(view)
        setSharedElementTransitionOnEnter()
        postponeEnterTransition()

        setupFavoriteButton()
        binding.imageNew.apply {
            transitionName = "image_new_card_1"
            startEnterTransitionAfterLoadingImage(R.drawable.test_img_for_news.toString(),this)
        }
    }

    private fun setupFavoriteButton() {

    }

    private fun setSharedElementTransitionOnEnter() {
        sharedElementEnterTransition = TransitionInflater.from(context)
            .inflateTransition(R.transition.shared_element_transition)
    }

    private fun startEnterTransitionAfterLoadingImage(
        imageAddress: String,
        imageView: ImageView
    ) {
        Glide.with(this)
            .load(imageAddress.toInt())
            .dontAnimate() // 1
            .listener(object : RequestListener<Drawable> { // 2
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: com.bumptech.glide.request.target.Target<Drawable>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }
            })
            .into(imageView)
    }
}