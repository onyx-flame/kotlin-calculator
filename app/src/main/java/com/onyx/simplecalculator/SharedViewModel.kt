package com.onyx.simplecalculator

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

    val text = MutableLiveData<String>()
    val action = MutableLiveData<String>()

    fun sendText(s: String) {
        text.value = s
    }

    fun sendAction(s: String) {
        action.value = s
    }

    fun clearLiveData() {
        text.value = ""
        action.value = ""
    }
}