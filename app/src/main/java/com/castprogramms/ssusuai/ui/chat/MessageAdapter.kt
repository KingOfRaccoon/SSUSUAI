package com.castprogramms.ssusuai.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.ItemMessageBinding

class MessageAdapter : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_message, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(position)
        holder.setIsRecyclable(false)
    }

    override fun getItemCount() = 600000000

    inner class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemMessageBinding.bind(view)

        fun bind(position: Int) {
            if (position % 2 == 0) {
                binding.theirMessage.root.visibility = View.GONE
            } else {
                binding.myMessage.visibility = View.GONE
            }
        }
    }
}
//class MessageAdapter(var context: Context) : BaseAdapter() {
//    var messages: MutableList<Message> = ArrayList()
//    fun add(message: Message) {
//        messages.add(message)
//        notifyDataSetChanged() // to render the list we need to notify
//    }
//
//    fun clear(){
//        messages.clear()
//        notifyDataSetChanged()
//    }
//
//    override fun getCount(): Int {
//        return messages.size
//    }
//
//    override fun getItem(i: Int): Any {
//        return messages[i]
//    }
//
//    override fun getItemId(i: Int): Long {
//        return i.toLong()
//    }
//
//    // This is the backbone of the class, it handles the creation of single ListView row (chat bubble)
//    override fun getView(i: Int, convertView: View?, viewGroup: ViewGroup?): View {
//        val view: View
//        val holder = MessageViewHolder()
//        val messageInflater = LayoutInflater.from(context)
//        val message = messages[i]
//        if (message.belongsToCurrentUser) { // this message was sent by us so let's create a basic chat bubble on the right
//            view = messageInflater.inflate(R.layout.my_message, null)
//            holder.messageBody = view.findViewById(R.id.message_body)
//            view.tag = holder
//            holder.messageBody!!.text = message.text
//        } else { // this message was sent by someone else so let's create an advanced chat bubble on the left
//            view = messageInflater.inflate(R.layout.their_message, null)
//            holder.avatar = view.findViewById(R.id.avatar) as View
//            holder.name = view.findViewById(R.id.name)
//            holder.messageBody = view.findViewById(R.id.message_body)
//            view.tag = holder
//            holder.name!!.text = message.memberData.name
//            holder.messageBody!!.text = message.text
//            val drawable = holder.avatar!!.background as GradientDrawable
//            drawable.setColor(Color.parseColor(message.memberData.color))
//        }
//        return view
//    }
//}
//
//internal class MessageViewHolder {
//    var avatar: View? = null
//    var name: TextView? = null
//    var messageBody: TextView? = null
//}