package com.castprogramms.ssusuai.tools.chat.items

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.ItemMessagePrivateBinding
import com.castprogramms.ssusuai.tools.chat.Message
import com.castprogramms.ssusuai.tools.chat.TextMessage
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item

class TextMessagePrivateItem(
    val textMessage: TextMessage,
    private val answerMessage: Message?,
    val getNameCurrentUser: (LifecycleOwner, Observer<String>) -> Unit,
    val getNameOtherUser: (LifecycleOwner, Observer<String>) -> Unit
) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.setIsRecyclable(false)
        val binding = ItemMessagePrivateBinding.bind(viewHolder.itemView)
        val googleSignIn = GoogleSignIn.getLastSignedInAccount(viewHolder.itemView.context)

        if (textMessage.idUser == googleSignIn!!.id) {
            binding.theirMessageBody.visibility = View.GONE
            binding.myMessageBody.visibility = View.VISIBLE
            binding.myMessage.text = textMessage.text
            if (answerMessage != null) {
                binding.containerAnswerMessage.visibility = View.VISIBLE
                binding.containerAnswerMessageTheir.visibility = View.GONE
                viewHolder.itemView.findViewTreeLifecycleOwner()?.let {
                    getNameCurrentUser(it, Observer<String>{
                        binding.textNameUserAnswerMessage.text = it
                    })
                }
                when(answerMessage){
                    is TextMessage ->
                        binding.textAnswerMessage.text = answerMessage.text
                }
            }
        } else {
            binding.myMessageBody.visibility = View.GONE
            binding.theirMessage.text = textMessage.text
            if (answerMessage != null) {
                binding.containerAnswerMessage.visibility = View.GONE
                binding.containerAnswerMessageTheir.visibility = View.VISIBLE
                viewHolder.itemView.findViewTreeLifecycleOwner()?.let {
                    getNameOtherUser(it, Observer<String> {
                        binding.textNameUserAnswerMessageTheir.text = it
                    })
                }

                when(answerMessage){
                    is TextMessage ->
                        binding.textAnswerMessageTheir.text = answerMessage.text
                }
            }
        }
    }

    override fun getLayout() = R.layout.item_message_private

    override fun isSameAs(other: com.xwray.groupie.Item<*>): Boolean {
        return if (other is TextMessagePrivateItem)
            this.textMessage.id == other.textMessage.id
        else
            false
    }
}