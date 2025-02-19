package com.example.pennywise.ui.fragment

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.pennywise.databinding.FragmentHomeBinding
import com.example.pennywise.ulti.Mapper
import com.example.pennywise.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus


@AndroidEntryPoint
class Home : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Gắn ViewBinding
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        _init()
        setup()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun setup(){
        homeViewModel.observeAndUpdateTotalExpense(Date.getCurrentMonth().toString())
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun _init() {
        lifecycleScope.launch {
            homeViewModel.totalExpense.collectLatest { expense ->
                binding.tongChiP.text = Mapper.formatNumber(expense)
            }
        }

        lifecycleScope.launch {
            homeViewModel.totalSalary.collectLatest { salary ->
                binding.tongluong.text = Mapper.formatNumber(salary)
            }
        }
    }




}
