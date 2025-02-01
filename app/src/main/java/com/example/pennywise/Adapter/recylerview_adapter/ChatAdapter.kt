import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pennywise.Adapter.model.Message
import com.example.pennywise.databinding.ItemChatMeBinding
import com.example.pennywise.databinding.ItemChatOtherBinding

class ChatAdapter :
    ListAdapter<Message, RecyclerView.ViewHolder>(MessageDiffCallback()) {

    companion object {
        private const val VIEW_TYPE_ME = 1
        private const val VIEW_TYPE_OTHER = 2
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).isSentByMe) VIEW_TYPE_ME else VIEW_TYPE_OTHER
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
        val message = getItem(position)
        if (holder is ChatMeViewHolder) {
            holder.bind(message)
        } else if (holder is ChatOtherViewHolder) {
            holder.bind(message)
        }
    }

    override fun submitList(list: List<Message>?) {
        val filteredList = filterAlternatingMessages(list ?: emptyList())
        super.submitList(filteredList)
    }

    private fun filterAlternatingMessages(messages: List<Message>): List<Message> {
        if (messages.isEmpty()) return emptyList()

        val result = mutableListOf<Message>()
        var lastType: Boolean? = null

        for (message in messages) {
            if (lastType == null || lastType != message.isSentByMe) {
                result.add(message)
                lastType = message.isSentByMe
            }
        }
        return result
    }

    // ViewHolder for user messages
    inner class ChatMeViewHolder(private val binding: ItemChatMeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.textGchatMessageMe.text = message.content
            binding.textGchatTimestampMe.text = message.timestamp
        }
    }

    // ViewHolder for messages from others
    inner class ChatOtherViewHolder(private val binding: ItemChatOtherBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.textGchatMessageOther.text = message.content
            binding.textGchatTimestampOther.text = message.timestamp
        }
    }

    class MessageDiffCallback : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.timestamp == newItem.timestamp
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }
    }
}


