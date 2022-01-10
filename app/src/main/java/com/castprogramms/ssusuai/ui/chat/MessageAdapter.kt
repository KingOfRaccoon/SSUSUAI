package com.castprogramms.ssusuai.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.ItemMessagePrivateBinding
import com.castprogramms.ssusuai.tools.chat.TextMessage
import com.google.android.gms.auth.api.signin.GoogleSignIn

class MessageAdapter() : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {
    val messages = mutableListOf<TextMessage>()
    val mutableLiveDataCurrentUserName = MutableLiveData<String>()
    val mutableLiveDataOtherUserName = MutableLiveData<String>()

    fun setMessages(newTextMessages: List<TextMessage>) {
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return messages.size
            }

            override fun getNewListSize(): Int {
                return newTextMessages.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return messages.hashCode() == newTextMessages.hashCode()
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return messages[oldItemPosition] == newTextMessages[newItemPosition]
            }
        })
        messages.clear()
        messages.addAll(newTextMessages)
        diff.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_message_private, parent, false),
            parent.findViewTreeLifecycleOwner()
        )
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messages[position], position)
        holder.setIsRecyclable(false)
    }

    override fun getItemCount() = messages.size

    inner class MessageViewHolder(view: View, val livecycle: LifecycleOwner?) :
        RecyclerView.ViewHolder(view) {
        private val binding = ItemMessagePrivateBinding.bind(view)
        private val googleSignIn = GoogleSignIn.getLastSignedInAccount(itemView.context)
        fun bind(textMessage: TextMessage, position: Int) {
            val answerMessage = messages.find { it.id == textMessage.idAnswerMessage }

            if (position == messages.size-1
                || (textMessage.date.getMouthAndYear() != messages[position+1].date.getMouthAndYear()
                || textMessage.date.day != messages[position+1].date.day)){
//                binding.dateMessages.visibility = View.VISIBLE
//                binding.dateMessages.text = textMessage.date.getTimeForChat()
            }

            if (textMessage.idUser == googleSignIn!!.id) {
                binding.theirMessageBody.visibility = View.GONE
                binding.myMessageBody.visibility = View.VISIBLE
                binding.myMessage.text = textMessage.text
                if (answerMessage != null){
                    binding.containerAnswerMessage.visibility = View.VISIBLE
                    binding.containerAnswerMessageTheir.visibility = View.GONE
                    livecycle?.let {
                        mutableLiveDataCurrentUserName.observe(it){
                            binding.textNameUserAnswerMessage.text = it
                        }
                    }
                    binding.textAnswerMessage.text = answerMessage.text
                }
            } else {
                binding.myMessage.visibility = View.GONE
                binding.theirMessage.text = textMessage.text
                if (answerMessage != null){
                    binding.containerAnswerMessage.visibility = View.GONE
                    binding.containerAnswerMessageTheir.visibility = View.VISIBLE
                    livecycle?.let {
                        mutableLiveDataOtherUserName.observe(it){
                            binding.textNameUserAnswerMessageTheir.text = it
                        }
                    }
                    binding.textAnswerMessageTheir.text = answerMessage.text
                }
            }
        }
    }
}