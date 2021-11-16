package com.castprogramms.ssusuai.ui.addchat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.ItemSymbolAllUsersPersonalChatBinding
import com.castprogramms.ssusuai.users.Person

class AddPersonalChatAdapter(val callback: AddPersonalChatCallback) :
    RecyclerView.Adapter<AddPersonalChatAdapter.AddPrivateChatViewHolder>() {
    val users = mutableMapOf<Char, List<Pair<String, Person>>>()

    fun setAddUsers(addUsers: List<Pair<String, Person>>) {
        val newUsers = addUsers.sortedBy { it.second.getFullName() }.groupBy {
            it.second.surname.first()
        }.toSortedMap()

        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return users.size
            }

            override fun getNewListSize(): Int {
                return newUsers.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return users.toList()[oldItemPosition].first == newUsers.toList()[newItemPosition].first
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return users.toList()[oldItemPosition].second == newUsers.toList()[newItemPosition].second
            }
        }, true)

        users.clear()
        users.putAll(newUsers)
        diff.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddPrivateChatViewHolder {
        return AddPrivateChatViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_symbol_all_users_personal_chat, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AddPrivateChatViewHolder, position: Int) {
        holder.bind(users.toList()[position])
    }

    override fun getItemCount() = users.size

    inner class AddPrivateChatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemSymbolAllUsersPersonalChatBinding.bind(view)
        fun bind(pair: Pair<Char, List<Pair<String, Person>>>) {
            binding.symbol.text = pair.first.toString()
            val adapter = AddPersonalUserAdapter(callback)
            binding.recyclerAddUsers.adapter = adapter
            adapter.setData(pair.second)
        }
    }
}