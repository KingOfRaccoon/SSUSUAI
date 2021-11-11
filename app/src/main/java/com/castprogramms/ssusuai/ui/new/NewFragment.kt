package com.castprogramms.ssusuai.ui.new

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.castprogramms.ssusuai.MainActivity
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.FragmentNewBinding
import com.castprogramms.ssusuai.tools.New
import java.util.concurrent.TimeUnit

class NewFragment : Fragment(R.layout.fragment_new) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setHasOptionsMenu(true)
        val animation = TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.move)
        sharedElementReturnTransition = animation
        sharedElementEnterTransition = animation
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentNewBinding.bind(view)
        val new = requireArguments().getSerializable("new") as New
        (requireActivity() as MainActivity).setHtmlText("Новость") //TODO выводить название новости
        ViewCompat.setTransitionName(binding.imageNew, requireArguments().getString("img_card_name"))
        postponeEnterTransition()
        startEnterTransitionAfterLoadingImage(new.titleImg , binding.imageNew)
    }

    private fun startEnterTransitionAfterLoadingImage(
        imageAddress: String,
        imageView: ImageView
    ) {
        Glide.with(this)
            .load(imageAddress.toInt())
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            findNavController().popBackStack()
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.children.forEach { item ->
            if (item.itemId == android.R.id.home)
                item.icon = item.icon.apply {
                    this.setTint(Color.parseColor("#5481D8"))
                }
        }
        super.onCreateOptionsMenu(menu, inflater)
    }
}