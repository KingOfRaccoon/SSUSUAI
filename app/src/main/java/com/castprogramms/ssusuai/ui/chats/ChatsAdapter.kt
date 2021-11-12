package com.castprogramms.ssusuai.ui.chats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.ItemChatBinding
import com.castprogramms.ssusuai.tools.chat.Chat

class ChatsAdapter<T : Chat>(val slideDown: () -> Unit) :
    RecyclerView.Adapter<ChatsAdapter<T>.ChatsViewHolder>() {
    private val chats = mutableListOf<Pair<String, T>>()

    fun setChats(chats: List<Pair<String, T>>) {
        val chatsDiffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return this@ChatsAdapter.chats.size
            }

            override fun getNewListSize(): Int {
                return chats.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return this@ChatsAdapter.chats[oldItemPosition].first == chats[newItemPosition].first
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return this@ChatsAdapter.chats[oldItemPosition].second == chats[newItemPosition].second
            }
        }

        DiffUtil.calculateDiff(chatsDiffCallback, true).dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatsViewHolder {
        return ChatsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_chat, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ChatsViewHolder, position: Int) {
        holder.bind(chats[position])
    }

    override fun getItemCount() = chats.size

    inner class ChatsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemChatBinding.bind(view)

        fun bind(pair: Pair<String, T>) {
            binding.root.setOnClickListener {
                slideDown()
                it.findNavController().navigate(R.id.action_allTypeChatFragment_to_chatFragment)
            }
        }
    }
}