package com.example.pennywise.ui.fragment

import ChatAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pennywise.chatNetwork.GetData


import com.example.pennywise.databinding.FragmentChatBinding

import com.example.pennywise.viewModel.ChatViewModel
import com.example.pennywise.viewModel.DeepSeekViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Chat : Fragment() {
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private val chatViewModel: ChatViewModel by viewModels()
    private val viewModel: DeepSeekViewModel by viewModels()
    private lateinit var chatAdapter: ChatAdapter
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
        return binding.root
    }

    private fun setUp(){
        viewModel.response.observe(viewLifecycleOwner) { response ->
            response?.let {
                val apiMessageContent = it.choices[0].message.content
                println("api: $apiMessageContent")

                if (GetData.getStatus(apiMessageContent) == "1000") {
                    val apiResponse = GetData.getExpenseApi(apiMessageContent)
                    println("api: $apiResponse")

                    chatViewModel.addMessageFromOther(
                        apiResponse.messages,
                        System.currentTimeMillis().toString()
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
            val messageContent = binding.messageEditText.text.toString()
            if (messageContent.isNotBlank()) {
                val timestamp = System.currentTimeMillis().toString() // Replace with actual formatted timestamp
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
        viewModel.fetchResults(messageContent)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}