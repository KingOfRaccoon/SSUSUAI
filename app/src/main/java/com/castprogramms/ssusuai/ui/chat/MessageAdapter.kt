package com.castprogramms.ssusuai.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.ItemMessageBinding
import com.castprogramms.ssusuai.tools.chat.Message
import com.google.android.gms.auth.api.signin.GoogleSignIn

class MessageAdapter : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {
    val messages = mutableListOf<Message>()

    fun setMessages(newMessages: List<Message>){
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback(){
            override fun getOldListSize(): Int {
                return messages.size
            }

            override fun getNewListSize(): Int {
                return newMessages.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return messages.hashCode() == newMessages.hashCode()
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return messages[oldItemPosition] == newMessages[newItemPosition]
            }
        })
        messages.clear()
        messages.addAll(newMessages)
        diff.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_message, parent, false)
        )
    }
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messages[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount() = messages.size

    inner class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemMessageBinding.bind(view)

        fun bind(message: Message) {
            val googleSignIn = GoogleSignIn.getLastSignedInAccount(itemView.context)
            if (message.idUser == googleSignIn!!.id) {
                binding.theirMessage.root.visibility = View.GONE
                binding.messageBodyMy.visibility = View.VISIBLE
//                binding.myMessage.visibility = View.GONE
                binding.messageBodyMy.text = message.text
            } else {
                binding.myMessage.visibility = View.GONE
                binding.theirMessage.messageBody.text = message.text
            }
        }
    }
}