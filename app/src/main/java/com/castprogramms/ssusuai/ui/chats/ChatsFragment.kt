package com.castprogramms.ssusuai.ui.chats

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.castprogramms.ssusuai.MainActivity
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.FragmentChatsBinding

class ChatsFragment(val chatsType: ChatsType = ChatsType.Group): Fragment(R.layout.fragment_chats) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retainInstance = true
        val binding = FragmentChatsBinding.bind(view)
        (requireActivity() as MainActivity).setHtmlText("Сообщения")
        (requireActivity() as MainActivity).slideUp()
        val adapter = ChatsAdapter{ (requireActivity() as MainActivity).slideDown() }
        binding.recyclerChats.adapter = adapter
        when(chatsType){
            ChatsType.Group -> {

            }
            ChatsType.PERSONAL -> {

            }
        }
    }
}