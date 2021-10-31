package com.castprogramms.ssusuai.ui.chats

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.castprogramms.ssusuai.MainActivity
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.FragmentChatsBinding

class ChatsFragment: Fragment(R.layout.fragment_chats) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentChatsBinding.bind(view)
        binding.recyclerChats.adapter = ChatsAdapter{ (requireActivity() as MainActivity).slideDown() }
        (requireActivity() as MainActivity).setHtmlText("Сообщения")
        (requireActivity() as MainActivity).slideUp()
    }
}