package com.castprogramms.ssusuai.ui.chat

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import com.castprogramms.ssusuai.MainActivity
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.FragmentChatBinding
import com.castprogramms.ssusuai.databinding.ReplyTextMessageBinding
import com.castprogramms.ssusuai.repository.Resource
import com.castprogramms.ssusuai.tools.chat.*
import com.castprogramms.ssusuai.tools.chat.items.ChatTitleDateItem
import com.castprogramms.ssusuai.tools.chat.items.TextMessagePrivateItem
import com.castprogramms.ssusuai.tools.ui.BounceEdgeEffectFactory
import com.castprogramms.ssusuai.tools.ui.MessageSwipeController
import com.castprogramms.ssusuai.tools.ui.SwipeControllerActions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
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
        (requireActivity() as MainActivity).setHtmlText("")
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
        binding = FragmentChatBinding.bind(view)
        val adapter = GroupAdapter<GroupieViewHolder>()
        val messageSwipeController =
            MessageSwipeController(requireContext(), object : SwipeControllerActions {
                override fun showReplyUI(position: Int) {
                    showReplyView(adapter.getItem(position) as Item)
                }
            })
        val itemTouchHelper = ItemTouchHelper(messageSwipeController)
        itemTouchHelper.attachToRecyclerView(binding.recyclerMessages)

        binding.recyclerMessages.adapter = adapter
        binding.recyclerMessages.edgeEffectFactory = BounceEdgeEffectFactory()

        binding.userNameText.text =
            Editable.Factory.getInstance().newEditable(viewModel.messages[idChat].orEmpty())
        binding.recyclerMessages.scrollToPosition(0)
        ((requireActivity() as MainActivity).setIsChat(true))
        val googleAccount = GoogleSignIn.getLastSignedInAccount(requireContext())

        when (typeChats) {
            TypeChats.PersonalChat -> {
                viewModel.mutableLiveDataChat.observe(viewLifecycleOwner) {
                    when (it) {
                        is Resource.Error -> {}
                        is Resource.Loading -> {}
                        is Resource.Success -> {
                            if (it.data != null) {
                                val chat = it.data as PersonalChat
                                viewModel.getPersonWithoutLiveData(if (chat.idFirstUser == googleAccount?.id) chat.idSecondUser else chat.idFirstUser)
//                                val count = adapter.itemCount
//                                adapter.clear()

                                adapter.updateAsync(setMessages(chat.messages.reversed()), false) {
                                    adapter.notifyDataSetChanged()
                                    if (adapter.itemCount != 0)
                                        binding.recyclerMessages.smoothScrollToPosition(0)
                                    else
                                        binding.recyclerMessages.scrollToPosition(0)
                                }
//
//                                adapter.addAll(setMessages(chat.messages.reversed()))
//                                adapter.setMessages(chat.textMessages.reversed())
                            }
                        }
                    }
                }
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
                        viewModel.mutableLiveDataOtherUserName.postValue(it.data.getFullName())
                    }
                }
            }
        }

        viewModel.getCurrentPerson().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {
                    if (it.data != null) {
                        viewModel.mutableLiveDataCurrentUserName.postValue(it.data.getFullName())
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
                    TextMessage(
                        binding.userNameText.text?.trim().toString(),
                        googleAccount.id.toString()
                    )
                )
                binding.userNameText.text?.clear()
            }
        }
    }

    private fun showReplyView(item: Item) {
        val layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        when (item) {
            is TextMessagePrivateItem -> {
                val view = layoutInflater.inflate(R.layout.reply_text_message, null)
                val itemBinding = ReplyTextMessageBinding.bind(view)
                itemBinding.textAnswerMessageReply.text = item.textMessage.text
                val googleSignInAccount = viewModel.getGoogleAccount()
                if (googleSignInAccount != null) {
                    if (googleSignInAccount.id == item.textMessage.idUser) {
                        viewModel.mutableLiveDataCurrentUserName.observe(viewLifecycleOwner) {
                            itemBinding.textNameUserAnswerMessageReply.text = it
                        }
                    } else {
                        viewModel.mutableLiveDataOtherUserName.observe(viewLifecycleOwner) {
                            itemBinding.textNameUserAnswerMessageReply.text = it
                        }
                    }
                }
                view.layoutParams = layoutParams
                binding.containerForReply.addView(view)
                binding.containerForReply.visibility = View.VISIBLE
            }
        }

        binding.userTextContainer.requestFocus()
        val inputMethodManager =
            requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(binding.userNameText, InputMethodManager.SHOW_FORCED)
    }

    private fun setMessages(listMessages: List<Message>): MutableList<Item> {
        val messagesItem = mutableListOf<Item>()
        listMessages.forEachIndexed { index, message ->
            val answerMessage = listMessages.find { it.id == message.idAnswerMessage }
            when (message) {
                is TextMessage -> {
                    messagesItem.add(
                        TextMessagePrivateItem(
                            message,
                            answerMessage,
                            { lifecycleOwner: LifecycleOwner, observer: Observer<String> ->
                                viewModel.mutableLiveDataCurrentUserName.observe(
                                    lifecycleOwner,
                                    observer
                                )
                            }
                        ) { lifecycleOwner: LifecycleOwner, observer: Observer<String> ->
                            viewModel.mutableLiveDataOtherUserName.observe(
                                lifecycleOwner, observer
                            )
                        }
                    )
                }
            }

            if (index == listMessages.lastIndex ||
                (message.date.getMouthAndYear() != listMessages[index + 1].date.getMouthAndYear()
                        || message.date.day != listMessages[index + 1].date.day)
            ) {
                messagesItem.add(ChatTitleDateItem(ChatTitleDate(message.date.getTimeForChat())))
            }
        }
        return messagesItem
    }

    override fun onPause() {
        super.onPause()
        viewModel.messages[idChat] = binding.userNameText.text.toString()
    }
}