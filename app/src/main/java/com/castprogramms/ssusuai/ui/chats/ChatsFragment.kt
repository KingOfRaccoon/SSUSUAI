package com.castprogramms.ssusuai.ui.chats

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.castprogramms.ssusuai.MainActivity
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.FragmentChatsBinding
import com.castprogramms.ssusuai.repository.Resource
import com.castprogramms.ssusuai.tools.chat.PersonalChat
import com.castprogramms.ssusuai.tools.chat.PublicChat
import com.castprogramms.ssusuai.tools.ui.BounceEdgeEffectFactory
import com.castprogramms.ssusuai.ui.alltypechat.AddChatCallback
import com.castprogramms.ssusuai.users.Admin
import com.castprogramms.ssusuai.users.CommonUser
import com.castprogramms.ssusuai.users.Person
import com.castprogramms.ssusuai.users.TypeOfPerson
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatsFragment(
    private val chatsType: ChatsType = ChatsType.Group
) : Fragment(R.layout.fragment_chats) {
    val viewModel: ChatsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retainInstance = true
        val binding = FragmentChatsBinding.bind(view)
        (requireActivity() as MainActivity).setHtmlText("Сообщения")
        (requireActivity() as MainActivity).slideUp()

        binding.recyclerChats.edgeEffectFactory = BounceEdgeEffectFactory()
        loadUserData()
        when (chatsType) {
            ChatsType.Group -> {
                val adapter =
                    ChatsAdapter<PublicChat> { (requireActivity() as MainActivity).slideDown() }
                binding.recyclerChats.adapter = adapter
                viewModel.liveDataPublicChats.observe(viewLifecycleOwner, {
                    when (it) {
                        is Resource.Error -> {
                        }
                        is Resource.Loading -> {
                        }
                        is Resource.Success -> {
                            if (it.data != null)
                                adapter.setChats(it.data)
                        }
                    }
                })
            }
            ChatsType.PERSONAL -> {
                val adapter =
                    ChatsAdapter<PersonalChat> { (requireActivity() as MainActivity).slideDown() }
                binding.recyclerChats.adapter = adapter
                viewModel.liveDataPersonalChats.observe(viewLifecycleOwner, {
                    when (it) {
                        is Resource.Error -> {
                        }
                        is Resource.Loading -> {
                        }
                        is Resource.Success -> {
                            if (it.data != null)
                                adapter.setChats(it.data)
                        }
                    }
                })
            }
        }

    }

    private fun loadUserData() {
        viewModel.getUser<Person>().observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    if (it.data != null) {
                        when (chatsType) {
                            ChatsType.Group ->
                                viewModel.getPersonalChats(it.data.idsPersonalChat)
                            ChatsType.PERSONAL ->
                                viewModel.getPublicChats(it.data.idsPublicChat)
                        }
                    }
                }
            }
        })
    }
}