package com.castprogramms.ssusuai.ui.pieceofnews

import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.castprogramms.ssusuai.MainActivity
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.FragmentNewBinding
import com.castprogramms.ssusuai.tools.New

class NewFragment : Fragment(R.layout.fragment_new) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animation = TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.move)
        sharedElementReturnTransition = animation
        sharedElementEnterTransition = animation
        (requireActivity() as MainActivity).slideDown()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).setHtmlText("Новость") //TODO выводить название новости
        val binding = FragmentNewBinding.bind(view)
        val new = requireArguments().getSerializable("new") as New
        ViewCompat.setTransitionName(
            binding.imageNew,
            requireArguments().getString("img_card_name")
        )
        postponeEnterTransition()
        startEnterTransitionAfterLoadingImage(new.titleImg, binding.imageNew)

        binding.goToGallery.setOnClickListener {
            findNavController().navigate(R.id.action_newFragment_to_inAlbumFragment)
        }

        binding.textTitleNew.text = new.title
        binding.textBodyNew.text = new.body
        binding.textCounterSeeNew.text = new.sees.size.toString()
        binding.textCounterLikeNew.text = new.likes.size.toString()
    }

    private fun startEnterTransitionAfterLoadingImage(
        imageAddress: String,
        imageView: ImageView
    ) {
        Glide.with(this)
            .load(Uri.parse(imageAddress))
            .dontTransform()
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