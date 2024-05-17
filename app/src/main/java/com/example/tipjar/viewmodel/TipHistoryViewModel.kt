package com.example.tipjar.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.tipjar.base.BaseViewModel
import com.example.tipjar.domain.model.TipHistory
import com.example.tipjar.domain.repository.DataStoreRepository
import com.example.tipjar.domain.usecase.tip.CreateTipUseCase
import com.example.tipjar.domain.usecase.tip.RemoveTipUseCase
import com.example.tipjar.domain.usecase.tip.SearchTipUseCase
import com.example.tipjar.util.Constants.SP_CURRENCY_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TipHistoryViewModel(
    private val createTipUseCase: CreateTipUseCase,
    private val removeTipUseCase: RemoveTipUseCase,
    private val searchTipUseCase: SearchTipUseCase,
    private val datastoreRepository: DataStoreRepository,
) : BaseViewModel() {
    private var amount = ""
    private var tip = ""
    private var imagePath = ""

    // total tip state
    private val _totalTip = MutableStateFlow(0.0)
    val totalTip = _totalTip.asStateFlow()

    //per person state
    private val _perPersonTip = MutableStateFlow(0.0)
    val perPersonTip = _perPersonTip.asStateFlow()

    //payments state
    private val _payments = MutableStateFlow(emptyList<TipHistory>())
    val payments = _payments.asStateFlow()

    //currency state
    private val _currency = MutableStateFlow("$")
    val currency = _currency.asStateFlow()

    fun onSavePayment(amount: Double, tipPercent: Double) {
        val tip = amount * tipPercent
        val imagePath = ""//TODO
        viewModelScope.launch {
            createTipUseCase.invoke(amount, tip, imagePath)
            //TODO after it's done, navigate to next page
        }
    }

    fun onTapPayment() {
        //TODO show dialog?
    }

    fun onDeletePayment(tipToBeRemoved: TipHistory) {
        viewModelScope.launch(Dispatchers.IO) {
            removeTipUseCase.invoke(tipToBeRemoved)
        }
    }

    fun onShowAllPayments(startDate: Long? = null, endDate: Long? = null) {
        if (startDate != null && endDate != null) {
            viewModelScope.launch(Dispatchers.IO) {
                searchTipUseCase.invoke(startDate, endDate).collectLatest { data ->
                    _payments.tryEmit(data)
                }
            }
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                searchTipUseCase.invoke().collectLatest { data ->
                    _payments.tryEmit(data)
                }
            }
        }
    }

    fun onSaveCurrency(currency: String) {
        viewModelScope.launch(Dispatchers.IO) {
            datastoreRepository.putString(SP_CURRENCY_KEY, currency)
        }
    }

    fun onGetCurrency() {
        viewModelScope.launch(Dispatchers.IO) {
            datastoreRepository.getString(SP_CURRENCY_KEY).collectLatest { data ->
                _currency.tryEmit(data)
            }
        }
    }
}