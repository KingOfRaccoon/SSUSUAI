package com.castprogramms.ssusuai.ui.addchat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.ItemAddUserPersonalChatBinding
import com.castprogramms.ssusuai.users.Person

class AddPersonalUserAdapter(val callback: AddPersonalChatCallback): RecyclerView.Adapter<AddPersonalUserAdapter.AddPrivateUserViewHolder>() {
    val users = mutableListOf<Pair<String, Person>>()

    fun setData(newUsers: List<Pair<String, Person>>){
        val diff = DiffUtil.calculateDiff(object: DiffUtil.Callback(){
            override fun getOldListSize(): Int {
                return users.size
            }

            override fun getNewListSize(): Int {
                return newUsers.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return users[oldItemPosition].first == newUsers[newItemPosition].first
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return users[oldItemPosition].second == newUsers[newItemPosition].second
            }
        }, true)

        users.clear()
        users.addAll(newUsers)
        diff.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddPrivateUserViewHolder {
        return AddPrivateUserViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_add_user_personal_chat, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AddPrivateUserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount() = users.size

    inner class AddPrivateUserViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = ItemAddUserPersonalChatBinding.bind(view)

        fun bind(pair: Pair<String, Person>) {
            binding.fullNameAddChat.text = pair.second.getFullName()
            Glide.with(itemView)
                .load(pair.second.img)
                .into(binding.imageUserAddChat)
            binding.buttonAddChat.setOnClickListener {
                callback.addPersonalChat(pair)
            }
        }
    }
}