package com.castprogramms.ssusuai.ui.alltypechat

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.castprogramms.ssusuai.MainActivity
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.FragmentAllTypeChatBinding
import com.castprogramms.ssusuai.ui.chats.ChatsType
import com.google.android.material.tabs.TabLayoutMediator

class AllTypeChatFragment: Fragment(R.layout.fragment_all_type_chat) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        val binding = FragmentAllTypeChatBinding.bind(view)
        binding.viewPager2.adapter = ChatsViewPagerAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = ChatsType.values()[position].text
        }.attach()
    }
}