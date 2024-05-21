package com.example.tipjar.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.tipjar.R
import com.example.tipjar.domain.model.Currency
import com.example.tipjar.domain.repository.DataStoreRepository
import com.example.tipjar.domain.usecase.JSONAssetToObjectListUseCase
import com.example.tipjar.domain.usecase.tip.CreateTipUseCase
import com.example.tipjar.util.AppScreen
import com.example.tipjar.util.Constants.DEFAULT_CURRENCY
import com.example.tipjar.util.Constants.SP_CURRENCY_KEY
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.InputStream

/**
 * View Model for New Payment
 *
 * @property createTipUseCase
 * @property jsonAssetToObjectListUseCase
 * @property datastoreRepository
 * @constructor Create empty New payment view model
 */
class NewPaymentViewModel(
    private val createTipUseCase: CreateTipUseCase,
    private val jsonAssetToObjectListUseCase: JSONAssetToObjectListUseCase,
    private val datastoreRepository: DataStoreRepository,
) : ViewModel() {

    //coroutine exception handler
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(TipHistoryViewModel::class.java.simpleName, throwable.localizedMessage, throwable)
    }

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

    //currency list json state
    private val _currencyList = MutableStateFlow((listOf<Currency>()))
    val currencyList = _currencyList.asStateFlow()

    //currency search result state
    private val _searchResults = MutableStateFlow((listOf<Currency>()))
    val searchResults = _searchResults.asStateFlow()

    /**
     * Shows rationale
     *
     * @param show
     */
    fun showRationale(show: Boolean) {
        _showRationale.value = show
    }

    /**
     * Toggles image capture
     *
     */
    fun toggleImageCapture() {
        _takeImage.value = !_takeImage.value
    }

    /**
     * Update amount. This will also update the tip
     *
     * @param newAmount
     */
    fun updateAmount(newAmount: Double) {
        _amount.value = newAmount
        _amountError.value = -1
        compute()
    }

    /**
     * Increment people counter. This will also update the tip
     *
     */
    fun incrementPeople() {
        _peopleCount.value++
        compute()
    }

    /**
     * Decrement people counter. This will also update the tip
     *
     */
    fun decrementPeople() {
        _peopleCount.value--
        compute()
    }

    /**
     * Update tip percent. This will also update the tip
     *
     * @param newTipPercent
     */
    fun updateTipPercent(newTipPercent: Double) {
        _tipPercent.value = newTipPercent
        _tipError.value = -1
        compute()
    }

    /**
     * Called when the save button is clicked.
     * This will save the tip to the database.
     * Upon saving, it will navigate to the payment list screen.
     *
     * @param imagePath defaults to an empty string if there is no image to save
     * @param navController
     */
    fun onSavePayment(imagePath: String = "", navController: NavHostController) {
        if (_amount.value == 0.0) {
            _amountError.value = R.string.error_amount
            return
        }

        _isSaving.value = true
        viewModelScope.launch(exceptionHandler) {
            createTipUseCase.invoke(_amount.value, _totalTip.value, imagePath)
            _isSaving.value = false
            navController.navigate(AppScreen.PAYMENT_LIST.name)
        }
    }

    /**
     * Called when the currency is clicked. This will update the currency state.
     *
     */
    fun onGetCurrency() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            datastoreRepository.getString(SP_CURRENCY_KEY, DEFAULT_CURRENCY).collectLatest { data ->
                _currency.tryEmit(data)
            }
        }
    }

    /**
     * Loads currency list from JSON asset. This will also update the currency list state.
     *
     * @param inputStream
     */
    fun loadCurrencyList(inputStream: InputStream) {
        viewModelScope.launch(exceptionHandler) {
            val data = jsonAssetToObjectListUseCase.invoke(
                Currency::class.java,
                inputStream = inputStream
            ).values.toList()
            _currencyList.value = data
            _searchResults.value = data
        }
    }

    /**
     * Called when the search text changes. This will update the search results state.
     *
     * @param term
     */
    fun onSearchCurrency(term: String) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            if (term.isNotBlank()) {
                _searchResults.value = _currencyList.value.filter {
                    it.code.contains(term, true) || it.name.contains(
                        term,
                        true
                    ) || it.symbol.contains(term, true)
                }
            } else {
                _searchResults.value = _currencyList.value
            }
        }
    }

    /**
     * Called when a currency is selected. This will update the currency state.
     *
     * @param currency
     */
    fun onSaveCurrency(currency: String) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            datastoreRepository.putString(SP_CURRENCY_KEY, currency)
        }
    }

    /**
     * Computes the total tip and per person tip.
     *
     */
    private fun compute() {
        _totalTip.value = _amount.value * (_tipPercent.value / 100)
        _perPersonTip.value = _totalTip.value / _peopleCount.value
    }
}