package com.example.pennywise.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.pennywise.Adapter.recylerview_adapter.TabLabelAdapter
import com.example.pennywise.data.entity.ExpenseEntity
import com.example.pennywise.data.entity.WalletEntity
import com.example.pennywise.databinding.FragmentHomeBinding // Tự động sinh ra từ layout `fragment_home.xml`
import com.example.pennywise.viewModel.DeepSeekViewModel
import com.example.pennywise.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Home : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!! // Truy cập binding an toàn
    private val viewModel: DeepSeekViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Gắn ViewBinding
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        _init()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun _init() {

        lifecycleScope.launch {
            val userMessage = "list"
            viewModel.response.observe(viewLifecycleOwner) { response ->
                response?.let {
                    println( it.choices[0].message.content)
                }
            }
            viewModel.fetchResults(userMessage)
        }
    }

}
