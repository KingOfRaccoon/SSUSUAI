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
import com.castprogramms.ssusuai.repository.Resource
import com.castprogramms.ssusuai.tools.chat.Message
import com.castprogramms.ssusuai.tools.chat.PersonalChat
import com.castprogramms.ssusuai.tools.chat.PublicChat
import com.castprogramms.ssusuai.tools.chat.TypeChats
import com.castprogramms.ssusuai.tools.ui.BounceEdgeEffectFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatFragment : Fragment(R.layout.fragment_chat) {
    val viewModel: ChatViewModel by viewModel()
    private var typeChats = TypeChats.PublicChat
    private var idChat = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idChat = requireArguments().getString("idChat").toString()
        typeChats = requireArguments().getSerializable("typeChat") as TypeChats
        when (typeChats) {
            TypeChats.PersonalChat -> {
                viewModel.getChat<PersonalChat>(idChat, typeChats)
            }
            TypeChats.PublicChat -> {
                viewModel.getChat<PublicChat>(idChat, typeChats)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.setHasOptionsMenu(true)
        val binding = FragmentChatBinding.bind(view)
        val adapter = MessageAdapter()
        binding.recyclerMessages.adapter = adapter
        binding.recyclerMessages.edgeEffectFactory = BounceEdgeEffectFactory()
        binding.recyclerMessages.layoutManager = LinearLayoutManager(requireContext()).apply {
            stackFromEnd = true
            reverseLayout = true
            isSmoothScrollbarEnabled = true
            orientation = LinearLayoutManager.VERTICAL
        }
        binding.recyclerMessages.scrollToPosition(0)
        ((requireActivity() as MainActivity).setIsChat(true))

        when (typeChats) {
            TypeChats.PersonalChat -> {
                viewModel.mutableLiveDataChat.observe(viewLifecycleOwner, {
                    when (it) {
                        is Resource.Error -> {

                        }
                        is Resource.Loading -> {

                        }
                        is Resource.Success -> {
                            if (it.data != null) {
                                val chat = it.data as PersonalChat
                                adapter.setMessages(chat.messages.reversed())
                                binding.recyclerMessages.smoothScrollToPosition(0)
                            }
                        }
                    }
                })
            }
            TypeChats.PublicChat -> {

            }
        }

        binding.buttonSend.setOnClickListener {
            if (binding.userNameText.text?.trim().toString().isNotEmpty()
                && GoogleSignIn.getLastSignedInAccount(requireContext()) != null
            ) {
                viewModel.addMessage(
                    idChat,
                    Message(
                        binding.userNameText.text?.trim().toString(),
                        GoogleSignIn.getLastSignedInAccount(requireContext()).id
                    )
                )
                binding.userNameText.text?.clear()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            findNavController().popBackStack()
        return super.onOptionsItemSelected(item)
    }
}