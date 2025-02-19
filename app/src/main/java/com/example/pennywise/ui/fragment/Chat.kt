package com.example.pennywise.ui.fragment

import ChatAdapter
import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pennywise.chatNetwork.GetData
import com.example.pennywise.chatNetwork.dto.apiRespone.ApiResponse
import com.example.pennywise.databinding.FragmentChatBinding
import com.example.pennywise.viewModel.ChatViewModel
import com.example.pennywise.viewModel.DeepSeekViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Chat : Fragment() {
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private val chatViewModel: ChatViewModel by viewModels()
    private val viewModel: DeepSeekViewModel by viewModels()
    private lateinit var chatAdapter: ChatAdapter
    private var progressDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        setUp()
        _init()

        observeIsTyping()
        return binding.root
    }

    private fun observeIsTyping(){
        lifecycleScope.launch {
            chatViewModel.isTyping.collect{ isTyping->
                if(!isTyping) {
                    binding.messageEditText.isEnabled = true
                }else{
                    binding.messageEditText.isEnabled = false
                }
            }
        }
    }





    private fun setUp(){
        viewModel.response.observe(viewLifecycleOwner) { response ->
            response?.let {
                val apiMessageContent = it.choices[0].message.content
                val apiResponse = GetData.getExpenseApi(apiMessageContent)
                println("api: $apiResponse")
                lifecycleScope.launch {
                    chatViewModel.addMessageFromOther(
                        apiResponse
                    )
                }
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            error?.let {
                lifecycleScope.launch {
                    chatViewModel.addMessageFromOther(
                        ApiResponse(
                            status = "000",
                            messages = "Server đang bận",
                            result = emptyList()
                        )
                    )
                }
            }
        }
    }

    private fun _init() {
        chatAdapter = ChatAdapter()
        binding.chatRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = chatAdapter
        }

        // Observe messages from the ViewModel
        lifecycleScope.launch {
            chatViewModel.messages.collect { messages ->
                chatAdapter.submitList(messages)
            }
        }

        // Set up send button click listener
        binding.sendButton.setOnClickListener {
            chatViewModel.setLoadingState(isLoading = true)
            val messageContent = binding.messageEditText.text.toString()
            if (messageContent.isNotBlank()) {
                val timestamp = System.currentTimeMillis().toString()
                chatViewModel.addMessageFromUser(messageContent, timestamp)
                sendMessageToApi(messageContent)
                binding.messageEditText.text.clear()
            }
        }
    }

    // Simulate sending message to API
    private fun sendMessageToApi(messageContent: String) {
        if (!chatViewModel.canProcessApiResponse()) return // Chặn nếu không phải lượt của API
        // Trigger API call once
        chatViewModel.showTypingIndicator()
        viewModel.fetchResults(messageContent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}