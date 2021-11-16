package com.castprogramms.ssusuai.ui.allalbums

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.ItemAlbumBinding
import com.castprogramms.ssusuai.tools.Event

class AllAlbumAdapter(private var value: List<Event>) :
    RecyclerView.Adapter<AllAlbumAdapter.AllAlbumsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int
    ): AllAlbumsViewHolder {
       val view = LayoutInflater.from(parent.context)
           .inflate(R.layout.item_album, parent, false)
        return AllAlbumsViewHolder(view)
    }

    override fun onBindViewHolder(holder: AllAlbumAdapter.AllAlbumsViewHolder, position: Int) {
        holder.bind(value[position])
    }

    override fun getItemCount() : Int = value.size

    inner class AllAlbumsViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = ItemAlbumBinding.bind(view)

        fun bind(event: Event){
            binding.albumData.text = "${event.date.day}." + "${event.date.mouth}." + "${event.date.year}"
            binding.titleAlbums.text = event.name
            Glide.with(itemView)
                .load(event.img)
                .into(binding.imgForAlbum)
            binding.cardAlbumInList.setOnClickListener {
                it.findNavController().navigate(R.id.action_allAlbumsFragment_to_inAlbumFragment)
            }
        }
    }
}