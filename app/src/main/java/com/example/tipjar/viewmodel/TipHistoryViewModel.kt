package com.example.tipjar.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.tipjar.base.BaseViewModel
import com.example.tipjar.domain.usecase.CreateTipUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TipHistoryViewModel(private val createTipUseCase: CreateTipUseCase) : BaseViewModel() {
    private var amount = ""
    private var tip = ""
    private var imagePath = ""

    // total tip state
    private val _totalTip = MutableStateFlow(0.0)
    val totalTip: MutableStateFlow<Double> = _totalTip

    //per person state
    private val _perPersonTip = MutableStateFlow(0.0)
    val perPersonTip: MutableStateFlow<Double> = _perPersonTip

    fun onSavePayment(amount: Double, peopleCount: Int, tipPercent: Double) {
        viewModelScope.launch {
            //TODO
        }
    }

    fun onTapPayment() {
        //TODO
    }

    fun onDeletePayment() {
        //TODO
    }

    fun onShowAllPayments(startDate: Long? = null, endDate: Long? = null) {
        //TODO show all
        //TODO search
    }
}