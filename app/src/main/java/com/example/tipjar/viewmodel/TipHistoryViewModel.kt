package com.example.tipjar.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tipjar.domain.model.TipHistory
import com.example.tipjar.domain.repository.DataStoreRepository
import com.example.tipjar.domain.usecase.tip.RemoveTipUseCase
import com.example.tipjar.domain.usecase.tip.SearchTipUseCase
import com.example.tipjar.util.Constants.DEFAULT_CURRENCY
import com.example.tipjar.util.Constants.SP_CURRENCY_KEY
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File

/**
 * Viewmodel for Tip history view
 *
 * @property removeTipUseCase
 * @property searchTipUseCase
 * @property datastoreRepository
 * @constructor Create empty Tip history view model
 */
class TipHistoryViewModel(
    private val removeTipUseCase: RemoveTipUseCase,
    private val searchTipUseCase: SearchTipUseCase,
    private val datastoreRepository: DataStoreRepository,
) : ViewModel() {

    //coroutine exception handler
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(TipHistoryViewModel::class.java.simpleName, throwable.localizedMessage, throwable)
    }

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

    /**
     * Toggles the date dialog for searching payments
     *
     * @param show
     * @param startDate
     * @param endDate
     */
    fun toggleDateDialog(show: Boolean, startDate: Long? = null, endDate: Long? = null) {
        if (startDate != null && endDate != null) {
            _showDatePicker.value = Triple(show, startDate, endDate)
        } else {
            _showDatePicker.value = Triple(show, null, null)
        }
    }

    /**
     * Toggles the item dialog
     *
     * @param show
     * @param item
     */
    fun toggleItemDialog(show: Boolean, item: TipHistory? = null) {
        _showDialog.value = Pair(show, item)
    }

    /**
     * Called when the user want an item to be deleted.
     * This will remove the item from the database and the image from the file system.
     *
     * @param tipToBeRemoved tip to be removed
     */
    fun onDeletePayment(tipToBeRemoved: TipHistory) {
        viewModelScope.launch(exceptionHandler) {
            removeTipUseCase.invoke(tipToBeRemoved)
            File(filesDirectory, tipToBeRemoved.imagePath).delete()
        }
    }

    /**
     * Called usually on the first load of the viewmodel.
     * This can also be used to search for payments in a specific date range.
     *
     * @param startDate
     * @param endDate
     */
    fun onShowAllPayments(startDate: Long? = null, endDate: Long? = null) {
        viewModelScope.launch(exceptionHandler) {
            if (startDate != null && endDate != null) {
                searchTipUseCase.invoke(startDate, endDate).collectLatest { data ->
                    _payments.tryEmit(data)
                }
            } else {
                searchTipUseCase.invoke().collectLatest { data ->
                    _payments.tryEmit(data)
                }
            }
        }
    }

    /**
     * Gets the currency from the datastore
     *
     */
    fun onGetCurrency() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            datastoreRepository.getString(SP_CURRENCY_KEY, DEFAULT_CURRENCY).collectLatest { data ->
                _currency.tryEmit(data)
            }
        }
    }
}