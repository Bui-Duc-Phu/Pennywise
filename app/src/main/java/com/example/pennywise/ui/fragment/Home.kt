package com.example.pennywise.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.pennywise.databinding.FragmentHomeBinding // Tự động sinh ra từ layout `fragment_home.xml`
import com.example.pennywise.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Home : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!! // Truy cập binding an toàn
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Gắn ViewBinding
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _init()


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Giải phóng binding để tránh memory leak
    }
    private fun _init() {

    }
}
