package com.example.tipjar.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.tipjar.domain.repository.DataStoreRepository
import com.example.tipjar.domain.usecase.JSONAssetToObjectListUseCase
import com.example.tipjar.domain.usecase.tip.CreateTipUseCase
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class NewPaymentViewModelTest {
    private lateinit var createTipUseCase: CreateTipUseCase
    private lateinit var jsonAssetToObjectListUseCase: JSONAssetToObjectListUseCase
    private lateinit var datastoreRepository: DataStoreRepository
    private lateinit var target: NewPaymentViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        createTipUseCase = mockk()
        jsonAssetToObjectListUseCase = mockk()
        datastoreRepository = mockk()
        target =
            NewPaymentViewModel(
                createTipUseCase,
                jsonAssetToObjectListUseCase,
                datastoreRepository
            )
    }

    @Test
    fun showRationale_givenTrue_expectTrue() {
        //Given
        val expected = true
        //When
        target.showRationale(expected)
        //Then
        Assert.assertEquals(expected, target.showRationale.value)
    }

    @Test
    fun showRationale_givenFalse_expectFalse() {
        //Given
        val expected = false
        //When
        target.showRationale(expected)
        //Then
        Assert.assertEquals(expected, target.showRationale.value)
    }

    @Test
    fun toggleImageCapture_expectOppositeValue() {
        //Given
        val expected = !target.takeImage.value
        //When
        target.toggleImageCapture()
        //Then
        Assert.assertEquals(expected, target.takeImage.value)
    }

    @Test
    fun updateAmount_given10Amount_expect10AndNoError() {
        //Given
        val expectedAmount = 10.0
        val expectedAmountError = -1
        //When
        target.updateAmount(expectedAmount)
        //Then
        Assert.assertEquals(expectedAmount, target.amount.value, 0.0)
        Assert.assertEquals(expectedAmountError, target.amountError.value)
    }

    @Test
    fun incrementPeople_givenInitialState_expect2() {
        //Given
        val expectedPeople = 2
        //When
        target.incrementPeople()
        //Then
        Assert.assertEquals(expectedPeople, target.peopleCount.value)
    }

    @Test
    fun decrementPeople_givenInitialState_expect0() {
        //Given
        val expectedPeople = 0
        //When
        target.decrementPeople()
        //Then
        Assert.assertEquals(expectedPeople, target.peopleCount.value)
    }

    @Test
    fun updateTipPercent_given10Percent_expect10AndNoError() {
        //Given
        val expectedTipPercent = 10.0
        val expectedTipPercentError = -1
        //When
        target.updateTipPercent(expectedTipPercent)
        //Then
        Assert.assertEquals(expectedTipPercent, target.tipPercent.value, 0.0)
        Assert.assertEquals(expectedTipPercentError, target.tipError.value)
    }
}