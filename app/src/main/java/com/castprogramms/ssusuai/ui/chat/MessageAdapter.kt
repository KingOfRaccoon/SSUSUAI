package com.castprogramms.ssusuai.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.ItemMessageBinding

class MessageAdapter : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_message, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(position)
        holder.setIsRecyclable(false)
    }

    override fun getItemCount() = Int.MAX_VALUE

    inner class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemMessageBinding.bind(view)

        fun bind(position: Int) {
            if (position % 2 == 0) {
                binding.theirMessage.root.visibility = View.GONE
            } else {
                binding.myMessage.visibility = View.GONE
            }
        }
    }
}