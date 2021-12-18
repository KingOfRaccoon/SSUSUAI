package com.castprogramms.ssusuai.ui.chat

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
    lateinit var binding: FragmentChatBinding
    private var typeChats = TypeChats.PublicChat
    private var idChat = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idChat = requireArguments().getString("idChat").toString()
        typeChats = requireArguments().getSerializable("typeChat") as TypeChats
        (requireActivity() as MainActivity).setHtmlText("Диалог")
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
        binding = FragmentChatBinding.bind(view)
        binding.recyclerMessages.scrollToPosition(0)
        val adapter = MessageAdapter()
        binding.recyclerMessages.adapter = adapter
        binding.recyclerMessages.edgeEffectFactory = BounceEdgeEffectFactory()
        binding.recyclerMessages.layoutManager = LinearLayoutManager(requireContext()).apply {
            stackFromEnd = true
            reverseLayout = true
            isSmoothScrollbarEnabled = true
            orientation = LinearLayoutManager.VERTICAL
        }

        binding.userNameText.text =
            Editable.Factory.getInstance().newEditable(viewModel.messages[idChat].orEmpty())
        binding.recyclerMessages.scrollToPosition(0)
        ((requireActivity() as MainActivity).setIsChat(true))
        val googleAccount = GoogleSignIn.getLastSignedInAccount(requireContext())

        when (typeChats) {
            TypeChats.PersonalChat -> {
                viewModel.mutableLiveDataChat.observe(viewLifecycleOwner, {
                    when (it) {
                        is Resource.Error -> {}
                        is Resource.Loading -> {}
                        is Resource.Success -> {
                            if (it.data != null) {
                                val chat = it.data as PersonalChat
                                viewModel.getPersonWithoutLiveData(if (chat.idFirstUser == googleAccount?.id) chat.idSecondUser else chat.idFirstUser)
                                val count = adapter.itemCount
                                adapter.setMessages(chat.messages.reversed())
                                if (count != 0)
                                    binding.recyclerMessages.smoothScrollToPosition(0)
                                else
                                    binding.recyclerMessages.scrollToPosition(0)
                            }
                        }
                    }
                })
            }

            TypeChats.PublicChat -> {

            }
        }

        viewModel.mutableLiveDataOtherUser.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {
                    if (it.data != null) {
                        (requireActivity() as MainActivity).setHtmlText(it.data.getFullName())
                        (requireActivity() as MainActivity).setCustomImage(it.data.img)
                        adapter.mutableLiveData.postValue(it.data)
                    }
                }
            }
        }

        binding.buttonSend.setOnClickListener {
            if (binding.userNameText.text?.trim().toString().isNotEmpty()
                && googleAccount?.id != null
            ) {
                viewModel.addMessage(
                    idChat,
                    Message(
                        binding.userNameText.text?.trim().toString(),
                        googleAccount.id!!
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

    override fun onPause() {
        super.onPause()
        viewModel.messages[idChat] = binding.userNameText.text.toString()
    }
}