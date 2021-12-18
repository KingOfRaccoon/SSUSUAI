package com.castprogramms.ssusuai.ui.alltypechat

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.castprogramms.ssusuai.ui.chats.ChatsFragment
import com.castprogramms.ssusuai.ui.chats.ChatsType

class ChatsViewPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    override fun getItemCount() = ChatsType.values().size

    override fun createFragment(position: Int) = ChatsFragment().apply {
        arguments = Bundle().apply {
            putSerializable("chat_type", ChatsType.values()[position])
        }
    }
}