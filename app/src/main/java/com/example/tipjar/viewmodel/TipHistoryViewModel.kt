package com.example.tipjar.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.tipjar.base.BaseViewModel
import com.example.tipjar.domain.model.TipHistory
import com.example.tipjar.domain.repository.DataStoreRepository
import com.example.tipjar.domain.usecase.tip.RemoveTipUseCase
import com.example.tipjar.domain.usecase.tip.SearchTipUseCase
import com.example.tipjar.util.Constants.DEFAULT_CURRENCY
import com.example.tipjar.util.Constants.SP_CURRENCY_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File

class TipHistoryViewModel(
    private val removeTipUseCase: RemoveTipUseCase,
    private val searchTipUseCase: SearchTipUseCase,
    private val datastoreRepository: DataStoreRepository,
) : BaseViewModel() {
    var filesDirectory: File? = null

    //payments state
    private val _payments = MutableStateFlow(emptyList<TipHistory>())
    val payments = _payments.asStateFlow()

    //currency state
    private val _currency = MutableStateFlow("$")
    val currency = _currency.asStateFlow()

    //pop-up state
    private val _showDialog = MutableStateFlow<Pair<Boolean, TipHistory?>>(Pair(false, null))
    val showDialog = _showDialog.asStateFlow()

    //date pop-up state
    private val _showDatePicker =
        MutableStateFlow<Triple<Boolean, Long?, Long?>>(Triple(false, null, null))
    val showDatePicker = _showDatePicker.asStateFlow()

    fun toggleDateDialog(show: Boolean, startDate: Long? = null, endDate: Long? = null) {
        if (startDate != null && endDate != null) {
            _showDatePicker.value = Triple(show, startDate, endDate)
        } else {
            _showDatePicker.value = Triple(show, null, null)
        }
    }

    fun toggleItemDialog(show: Boolean, item: TipHistory? = null) {
        _showDialog.value = Pair(show, item)
    }

    fun onDeletePayment(tipToBeRemoved: TipHistory) {
        viewModelScope.launch(Dispatchers.IO) {
            removeTipUseCase.invoke(tipToBeRemoved)
            File(filesDirectory, tipToBeRemoved.imagePath).delete()
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

    fun onGetCurrency() {
        viewModelScope.launch(Dispatchers.IO) {
            datastoreRepository.getString(SP_CURRENCY_KEY, DEFAULT_CURRENCY).collectLatest { data ->
                _currency.tryEmit(data)
            }
        }
    }
}