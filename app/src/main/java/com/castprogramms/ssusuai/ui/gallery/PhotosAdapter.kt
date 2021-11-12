package com.castprogramms.ssusuai.ui.gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.ItemPhotoBinding
import com.google.android.flexbox.FlexboxLayoutManager

class PhotosAdapter : RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder>() {

    companion object {
        private val PHOTO_IMAGE_IDS = intArrayOf(
            R.drawable.test_photo,
            R.drawable.test_photo,
            R.drawable.test_photo,
            R.drawable.test_photo,
            R.drawable.test_photo,
            R.drawable.test_photo,
            R.drawable.test_photo,
            R.drawable.test_photo,
            R.drawable.test_photo,
            R.drawable.test_photo,
            R.drawable.test_photo,
            R.drawable.test_photo,
        )
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhotosAdapter.PhotosViewHolder {
        return PhotosViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_photo, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PhotosAdapter.PhotosViewHolder, position: Int) {
        val pos = position % PHOTO_IMAGE_IDS.size
        holder.onBind(PHOTO_IMAGE_IDS[pos])
    }

    override fun getItemCount() = PHOTO_IMAGE_IDS.size * 4

    inner class PhotosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemPhotoBinding.bind(itemView)
        fun onBind(@DrawableRes drawableRes: Int) {
            binding.photo.setImageResource(drawableRes)
            val lp = binding.photo.layoutParams
            if (lp is FlexboxLayoutManager.LayoutParams) {
                lp.flexGrow = 1f
            }
        }
    }
}