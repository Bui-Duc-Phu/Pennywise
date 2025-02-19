import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pennywise.Adapter.model.Message
import com.example.pennywise.databinding.ItemChatMeBinding
import com.example.pennywise.databinding.ItemChatOtherBinding

class ChatAdapter : ListAdapter<Message, RecyclerView.ViewHolder>(MessageDiffCallback()) {

    companion object {
        private const val VIEW_TYPE_ME = 1
        private const val VIEW_TYPE_OTHER = 2
        private const val VIEW_TYPE_TYPING = 3
    }

    private var recyclerView: RecyclerView? = null
    private var isTyping = false

    override fun getItemViewType(position: Int): Int {
        val message = getItem(position)
        return when {
            message.isTyping -> VIEW_TYPE_TYPING
            message.isSentByMe -> VIEW_TYPE_ME
            else -> VIEW_TYPE_OTHER
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        this.recyclerView = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ME -> {
                val binding = ItemChatMeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ChatMeViewHolder(binding)
            }
            VIEW_TYPE_OTHER -> {
                val binding = ItemChatOtherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ChatOtherViewHolder(binding)
            }
            else -> {
                val binding = ItemChatOtherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TypingViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = getItem(position)

        when (holder) {
            is ChatMeViewHolder -> holder.bind(message)
            is ChatOtherViewHolder -> holder.bind(message)
            is TypingViewHolder -> holder.bind()
        }
    }

    fun showTypingIndicator() {
        if (!isTyping) {
            isTyping = true
            val typingMessage = Message("Đang nhập...", System.currentTimeMillis().toString(), false, true)
            submitList(currentList + typingMessage)
        }
    }

    fun hideTypingIndicator() {
        if (isTyping) {
            isTyping = false
            submitList(currentList.filterNot { it.isTyping })
        }
    }

    inner class ChatMeViewHolder(private val binding: ItemChatMeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.textGchatMessageMe.text = message.content
        }
    }

    inner class ChatOtherViewHolder(private val binding: ItemChatOtherBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.textGchatMessageOther.text = message.content
        }
    }

    inner class TypingViewHolder(private val binding: ItemChatOtherBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val handler = Handler(Looper.getMainLooper())
            val dots = arrayOf(".", "..", "...")
            var index = 0
            handler.postDelayed(object : Runnable {
                override fun run() {
                    binding.textGchatMessageOther.text = "${dots[index]}"
                    index = (index + 1) % dots.size
                    handler.postDelayed(this, 500)
                }
            }, 500)
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
