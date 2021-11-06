package com.castprogramms.ssusuai.ui.chats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.ItemChatBinding

class ChatsAdapter(val slideDown: () -> Unit) : RecyclerView.Adapter<ChatsAdapter.ChatsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatsViewHolder {
        return ChatsViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat, parent, false))
    }

    override fun onBindViewHolder(holder: ChatsViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = 4

    inner class ChatsViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ItemChatBinding.bind(view)

        fun bind(){
            binding.root.setOnClickListener {
                slideDown()
                it.findNavController().navigate(R.id.action_allTypeChatFragment_to_chatFragment)
            }
        }
    }
}