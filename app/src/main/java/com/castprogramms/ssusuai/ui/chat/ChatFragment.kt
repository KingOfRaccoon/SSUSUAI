package com.castprogramms.ssusuai.ui.chat

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.castprogramms.ssusuai.MainActivity
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.FragmentChatBinding

class ChatFragment: Fragment(R.layout.fragment_chat) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.setHasOptionsMenu(true)
        (requireActivity() as MainActivity).supportActionBar
        val binding = FragmentChatBinding.bind(view)
        val adapter = MessageAdapter()
        binding.recyclerMessages.adapter = adapter
        binding.recyclerMessages.layoutManager = LinearLayoutManager(requireContext()).apply {
            reverseLayout = true
            stackFromEnd = true
        }
        binding.recyclerMessages.scrollToPosition(0)
        ((requireActivity() as MainActivity).setIsChat(true))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            findNavController().popBackStack()
        return super.onOptionsItemSelected(item)
    }
}