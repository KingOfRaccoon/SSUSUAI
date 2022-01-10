package com.castprogramms.ssusuai.tools.chat.items

import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.ItemChatDateBinding
import com.castprogramms.ssusuai.tools.chat.ChatTitleDate
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item

class ChatTitleDateItem(private val chatTitleDate: ChatTitleDate): Item(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        val binding = ItemChatDateBinding.bind(viewHolder.itemView)
        binding.dateMessages.text = chatTitleDate.date
    }

    override fun getLayout() = R.layout.item_chat_date
}