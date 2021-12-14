package com.castprogramms.ssusuai.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.ItemMessagePrivateBinding
import com.castprogramms.ssusuai.tools.chat.Message
import com.castprogramms.ssusuai.users.Person
import com.google.android.gms.auth.api.signin.GoogleSignIn

class MessageAdapter() : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {
    val messages = mutableListOf<Message>()
    val mutableLiveData = MutableLiveData<Person>()

    fun setMessages(newMessages: List<Message>) {
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
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
                .inflate(R.layout.item_message_private, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messages[position], position)
        holder.setIsRecyclable(false)
    }

    override fun getItemCount() = messages.size

    inner class MessageViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        private val binding = ItemMessagePrivateBinding.bind(view)
        fun bind(message: Message, position: Int) {
            if (position == messages.size-1 ||
                (
                message.date.getMouthAndYear() != messages[position+1].date.getMouthAndYear()
                        || message.date.day != messages[position+1].date.day)){
                binding.dateMessages.visibility = View.VISIBLE
                binding.dateMessages.text = message.date.getTimeForChat()
            }
            val googleSignIn = GoogleSignIn.getLastSignedInAccount(itemView.context)
            if (message.idUser == googleSignIn!!.id) {
                binding.theirMessageBody.visibility = View.GONE
                binding.myMessageBody.visibility = View.VISIBLE
                binding.myMessage.text = message.text
            } else {
                binding.myMessage.visibility = View.GONE
                binding.theirMessage.text = message.text
            }
        }
    }
}