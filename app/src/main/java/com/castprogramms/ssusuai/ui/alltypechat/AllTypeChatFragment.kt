package com.castprogramms.ssusuai.ui.alltypechat

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.castprogramms.ssusuai.MainActivity
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.FragmentAllTypeChatBinding
import com.castprogramms.ssusuai.ui.chats.ChatsType
import com.google.android.material.tabs.TabLayoutMediator

class AllTypeChatFragment: Fragment(R.layout.fragment_all_type_chat), AddChatCallback {
    lateinit var binding: FragmentAllTypeChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as MainActivity).setHtmlText("Сообщения")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.setHasOptionsMenu(true)
        binding = FragmentAllTypeChatBinding.bind(view)
        binding.viewPager2.adapter = ChatsViewPagerAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = ChatsType.values()[position].text
        }.attach()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.chats_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_chat)
            addChat()
        return super.onOptionsItemSelected(item)
    }

    override fun addChat() {
        when(val typeOfChats : ChatsType = ChatsType.values()[binding.viewPager2.currentItem]){
            ChatsType.Group ->{
                Toast.makeText(requireContext(), typeOfChats.text, Toast.LENGTH_LONG).show()
            }
            ChatsType.PERSONAL -> {
                Toast.makeText(requireContext(), typeOfChats.text, Toast.LENGTH_LONG).show()
            }
        }
    }
}