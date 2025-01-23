package com.example.pennywise.Adapter.recylerview_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pennywise.Adapter.model.Message
import com.example.pennywise.databinding.ItemChatMeBinding
import com.example.pennywise.databinding.ItemChatOtherBinding


class ChatAdapter(private val messages: List<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_ME = 1
        private const val VIEW_TYPE_OTHER = 2
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].isSentByMe) VIEW_TYPE_ME else VIEW_TYPE_OTHER
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ME) {
            val binding = ItemChatMeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ChatMeViewHolder(binding)
        } else {
            val binding = ItemChatOtherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ChatOtherViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        if (holder is ChatMeViewHolder) {
            holder.bind(message)
        } else if (holder is ChatOtherViewHolder) {
            holder.bind(message)
        }
    }

    override fun getItemCount(): Int = messages.size

    // ViewHolder cho tin nhắn của người dùng
    inner class ChatMeViewHolder(private val binding: ItemChatMeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.textGchatMessageMe.text = message.content
            binding.textGchatTimestampMe.text = message.timestamp
        }
    }

    // ViewHolder cho tin nhắn của người khác
    inner class ChatOtherViewHolder(private val binding: ItemChatOtherBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.textGchatMessageOther.text = message.content
            binding.textGchatTimestampOther.text = message.timestamp
            binding.textGchatUserOther.text = message.userName ?: "Unknown"
        }
    }
}
