package com.example.tipjar.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.tipjar.R
import com.example.tipjar.base.BaseViewModel
import com.example.tipjar.domain.repository.DataStoreRepository
import com.example.tipjar.domain.usecase.tip.CreateTipUseCase
import com.example.tipjar.util.AppScreen
import com.example.tipjar.util.Constants.DEFAULT_CURRENCY
import com.example.tipjar.util.Constants.SP_CURRENCY_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NewPaymentViewModel(
    private val createTipUseCase: CreateTipUseCase,
    private val datastoreRepository: DataStoreRepository,
) : BaseViewModel() {

    // total tip state
    private val _totalTip = MutableStateFlow(0.0)
    val totalTip = _totalTip.asStateFlow()

    //amount state
    private val _amount = MutableStateFlow(0.0)
    val amount = _amount.asStateFlow()

    //people count state
    private val _peopleCount = MutableStateFlow(1)
    val peopleCount = _peopleCount.asStateFlow()

    //per person state
    private val _perPersonTip = MutableStateFlow(0.0)
    val perPersonTip = _perPersonTip.asStateFlow()

    //currency state
    private val _currency = MutableStateFlow("$")
    val currency = _currency.asStateFlow()

    //tip percent state
    private val _tipPercent = MutableStateFlow(0.0)
    val tipPercent = _tipPercent.asStateFlow()

    //take image state
    private val _takeImage = MutableStateFlow(false)
    val takeImage = _takeImage.asStateFlow()

    //take image state
    private val _showRationale = MutableStateFlow(false)
    val showRationale = _showRationale.asStateFlow()

    //saving state
    private val _isSaving = MutableStateFlow(false)
    val isSaving = _isSaving.asStateFlow()

    //error states
    private val _amountError = MutableStateFlow(-1)
    val amountError = _amountError.asStateFlow()
    private val _tipError = MutableStateFlow(-1)
    val tipError = _tipError.asStateFlow()

    fun showRationale(show: Boolean) {
        _showRationale.value = show
    }

    fun toggleImageCapture() {
        _takeImage.value = !_takeImage.value
    }

    fun updateAmount(newAmount: Double) {
        _amount.value = newAmount
        _amountError.value = -1
        compute()
    }

    fun incrementPeople() {
        _peopleCount.value++
        compute()
    }

    fun decrementPeople() {
        _peopleCount.value--
        compute()
    }

    fun updateTipPercent(newTipPercent: Double) {
        _tipPercent.value = newTipPercent
        _tipError.value = -1
        compute()
    }

    fun onSavePayment(imagePath: String = "", navController: NavHostController) {
        if (_amount.value == 0.0) {
            _amountError.value = R.string.error_amount
            return
        }

        _isSaving.value = true
        viewModelScope.launch(Dispatchers.IO) {
            createTipUseCase.invoke(_amount.value, _totalTip.value, imagePath)
            viewModelScope.launch(Dispatchers.Main) {
                _isSaving.value = false
                navController.navigate(AppScreen.PAYMENT_LIST.name)
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

    private fun compute() {
        _totalTip.value = _amount.value * (_tipPercent.value / 100)
        _perPersonTip.value = _totalTip.value / _peopleCount.value
    }
}