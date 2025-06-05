package com.example.myapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {
    private val editText = MutableLiveData<String>()
    private val editTextFirstDemoActivity = MutableLiveData<String>()

    fun getEditText(): MutableLiveData<String> = editText
    fun getEditTextFromFirstDemoActivity(): MutableLiveData<String> = editTextFirstDemoActivity

}