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
import com.castprogramms.ssusuai.users.Person
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatsFragment(
) : Fragment(R.layout.fragment_chats) {
    val viewModel: ChatsViewModel by viewModel()

    override fun onStart() {
        super.onStart()

        loadUserData()
        println("start")
    }
    lateinit var chatsType: ChatsType
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chatsType = requireArguments().getSerializable("chat_type") as ChatsType
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentChatsBinding.bind(view)

        println("create view")
        (requireActivity() as MainActivity).setHtmlText("Сообщения")
        (requireActivity() as MainActivity).slideUp()

        binding.recyclerChats.edgeEffectFactory = BounceEdgeEffectFactory()
        println(chatsType.name)
        when (chatsType) {
            ChatsType.Group -> {
                val adapter =
                    ChatsAdapter<PublicChat>( { (requireActivity() as MainActivity).slideDown() }) {
                        viewModel.getUser(it)
                    }
                binding.recyclerChats.adapter = adapter
                viewModel.liveDataPublicChats.observe(viewLifecycleOwner) {
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
                }
            }
            ChatsType.PERSONAL -> {
                val adapter =
                    ChatsAdapter<PersonalChat>({ (requireActivity() as MainActivity).slideDown() }) {
                        viewModel.getUser(
                            it
                        )
                    }
                binding.recyclerChats.adapter = adapter
                viewModel.liveDataPersonalChats.observe(viewLifecycleOwner) {
                    when (it) {
                        is Resource.Error -> {
                        }
                        is Resource.Loading -> {
                        }
                        is Resource.Success -> {
                            println(it.data)
                            if (it.data != null)
                                adapter.setChats(it.data)
                        }
                    }
                }
            }
        }

    }

    private fun loadUserData() {
        viewModel.getUser<Person>().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    if (it.data != null) {
                        when (chatsType) {
                            ChatsType.PERSONAL -> {
                                viewModel.getPersonalChats(it.data.idsPersonalChat)
                                println(it.data.idsPersonalChat)
                            }
                            ChatsType.Group ->
                                viewModel.getPublicChats(it.data.idsPublicChat)
                        }
                    }
                }
            }
        }
    }
}