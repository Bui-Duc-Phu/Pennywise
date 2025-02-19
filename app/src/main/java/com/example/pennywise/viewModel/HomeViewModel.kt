package com.example.pennywise.viewModel

import Date
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pennywise.Adapter.model.HomeList
import com.example.pennywise.Adapter.model.TabLabel
import com.example.pennywise.Adapter.recylerview_adapter.HomeListAdapter
import com.example.pennywise.Adapter.recylerview_adapter.TabLabelAdapter
import com.example.pennywise.data.entity.ExpenseEntity
import com.example.pennywise.data.entity.WalletEntity
import com.example.pennywise.data.service.ExpenseService
import com.example.pennywise.data.service.WalletService

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val walletService: WalletService,
    private val expenseService: ExpenseService
) : ViewModel() {
    private val _totalExpense  =  MutableStateFlow<String>("2345")
    val totalExpense: StateFlow<String> get() = _totalExpense

    fun updateTotalExpense(newExpense: String) {
        _totalExpense.value = newExpense
    }

    private val _totalSalary  =  MutableStateFlow<String>("12345")
    val totalSalary: StateFlow<String> get() = _totalSalary

    fun updateTotalSalary(newExpense: String) {
        _totalSalary.value = newExpense
    }


   suspend fun getWalletByMonth(Month:String): WalletEntity? {
      return walletService.getWalletByMoth(month = Month)
   }

    fun observeAndUpdateTotalExpense(month: String) {
        viewModelScope.launch {
            walletService.observeTotalExpense(month).collectLatest { totalExpense ->
                totalExpense?.let {
                    updateTotalExpense(it.toString())
                    walletService.updateTotalExpense(Date.getCurrentMonthFormat(),it)
                }
            }
        }
    }
}
