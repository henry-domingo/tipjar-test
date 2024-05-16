package com.example.tipjar.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    private val loading: MutableLiveData<Boolean> by lazy { MutableLiveData() }
    val loadingLiveData: LiveData<Boolean> by lazy { loading }

}