package com.castprogramms.ssusuai.ui.chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.ItemChatBinding
import com.castprogramms.ssusuai.repository.Resource
import com.castprogramms.ssusuai.tools.chat.Chat
import com.castprogramms.ssusuai.tools.chat.PersonalChat
import com.castprogramms.ssusuai.tools.chat.TypeChats
import com.castprogramms.ssusuai.users.Person
import com.google.android.gms.auth.api.signin.GoogleSignIn

class ChatsAdapter<T : Chat>(
    val slideDown: () -> Unit,
    val getUser: (String) -> MutableLiveData<Resource<Person>>
) :
    RecyclerView.Adapter<ChatsAdapter<T>.ChatsViewHolder>() {
    private val chats = mutableListOf<Pair<String, T>>()

    fun setChats(chats: List<Pair<String, T>>) {
        val chatsDiffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return this@ChatsAdapter.chats.size
            }

            override fun getNewListSize(): Int {
                return chats.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return this@ChatsAdapter.chats[oldItemPosition].first == chats[newItemPosition].first
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return this@ChatsAdapter.chats[oldItemPosition].second == chats[newItemPosition].second
            }
        }
        this.chats.clear()
        this.chats.addAll(chats.toSet().toList())
        DiffUtil.calculateDiff(chatsDiffCallback, true).dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatsViewHolder {
        return ChatsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_chat, parent, false),
            parent.findViewTreeLifecycleOwner()
        )
    }

    override fun onBindViewHolder(holder: ChatsViewHolder, position: Int) {
        holder.bind(chats[position])
    }

    override fun getItemCount() = chats.size

    inner class ChatsViewHolder(view: View, val lifecycleOwner: LifecycleOwner?) : RecyclerView.ViewHolder(view) {
        private val binding = ItemChatBinding.bind(view)

        fun bind(pair: Pair<String, T>) {
            binding.root.setOnClickListener {
                slideDown()
                it.findNavController().navigate(
                    R.id.action_allTypeChatFragment_to_chatFragment,
                    Bundle().apply {
                        putString("idChat", pair.first)
                        putSerializable("typeChat", if (pair.second is PersonalChat) TypeChats.PersonalChat else TypeChats.PublicChat)
                    }
                )
            }

            val googleSignIn = GoogleSignIn.getLastSignedInAccount(itemView.context)
            if (googleSignIn != null) {
                when (pair.second) {
                    is PersonalChat -> {
                        val chat = pair.second as PersonalChat
                        val otherUserId = if (chat.idFirstUser == googleSignIn.id) chat.idSecondUser else chat.idFirstUser
                        lifecycleOwner?.let {
                            getUser(otherUserId).observe(lifecycleOwner, {
                                when(it){
                                    is Resource.Error -> {}
                                    is Resource.Loading -> {}
                                    is Resource.Success -> {
                                        if (it.data != null) {
                                            Glide.with(itemView)
                                                .load(it.data.img)
                                                .into(binding.imageUserChatCard)
                                            binding.textUserFullName.text = it.data.getFullName()
                                            if (chat.messages.isNotEmpty()) {
                                                val lastMessage = chat.messages.last()
                                                binding.textLastMessage.text =
                                                    if (lastMessage.idUser == googleSignIn.id) "Вы: " + lastMessage.text else lastMessage.text
                                            }
                                        }
                                    }
                                }
                            })
                        }
                    }
                }
            }
        }
    }
}