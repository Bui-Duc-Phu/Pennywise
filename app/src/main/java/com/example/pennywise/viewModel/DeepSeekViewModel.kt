package com.example.pennywise.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pennywise.chatNetwork.DeepSeekRepository
import com.example.pennywise.chatNetwork.DeepSeekResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeepSeekViewModel @Inject constructor(private val repository: DeepSeekRepository) : ViewModel() {

    private val _response = MutableLiveData<DeepSeekResponse>()
    val response: LiveData<DeepSeekResponse> get() = _response

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun fetchResults(userMessage: String) {
        viewModelScope.launch {
            try {
                val result = repository.getDeepSeekResults(userMessage)
                _response.value = result
            } catch (e: Exception) {
                _errorMessage.value = ""
                _errorMessage.value = "Server đang bận"
                Log.e("DeepSeekViewModel", "Error: ${e.message}")
            }
        }
    }
}
