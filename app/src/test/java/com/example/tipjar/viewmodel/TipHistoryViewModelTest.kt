package com.example.tipjar.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.tipjar.domain.model.TipHistory
import com.example.tipjar.domain.repository.DataStoreRepository
import com.example.tipjar.domain.usecase.tip.RemoveTipUseCase
import com.example.tipjar.domain.usecase.tip.SearchTipUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class TipHistoryViewModelTest {
    private lateinit var removeTipUseCase: RemoveTipUseCase
    private lateinit var searchTipUseCase: SearchTipUseCase
    private lateinit var datastoreRepository: DataStoreRepository
    private lateinit var target: TipHistoryViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()

    private val sampleData = TipHistory(
        timestamp = 100L,
        amount = 150.0,
        tip = 1.50,
        imagePath = ""
    )

    private val sampleData2 = TipHistory(
        timestamp = 200L,
        amount = 150.0,
        tip = 1.50,
        imagePath = ""
    )

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        removeTipUseCase = mockk()
        searchTipUseCase = mockk()
        datastoreRepository = mockk()
        target = TipHistoryViewModel(removeTipUseCase, searchTipUseCase, datastoreRepository)
    }

    @Test
    fun toggleDateDialog_givenTrueAndDateRanges_expectTrueWithDateRanges() {
        //Given
        val showDialog = true
        val startDate = 100L
        val endDate = 200L
        val expected = Triple(showDialog, startDate, endDate)
        //When
        target.toggleDateDialog(showDialog, startDate, endDate)
        //Then
        Assert.assertEquals(expected, target.showDatePicker.value)
    }

    @Test
    fun toggleDateDialog_givenFalse_expectOnlyFalse() {
        //Given
        val showDialog = false
        val startDate = null
        val endDate = null
        val expected = Triple(showDialog, startDate, endDate)
        //When
        target.toggleDateDialog(showDialog)
        //Then
        Assert.assertEquals(expected, target.showDatePicker.value)
    }

    @Test
    fun toggleItemDialog_givenTrueAndData_expectsTrueWithData() {
        //Given
        val showDialog = false
        val expected = Pair(showDialog, sampleData)
        //When
        target.toggleItemDialog(showDialog, sampleData)
        //Then
        Assert.assertEquals(expected, target.showDialog.value)
    }

    @Test
    fun toggleItemDialog_givenFalse_expectsFalse() {
        //Given
        val showDialog = false
        val expected = Pair(showDialog, null)
        //When
        target.toggleItemDialog(showDialog)
        //Then
        Assert.assertEquals(expected, target.showDialog.value)
    }

    @Test
    fun onShowAllPayments_givenData_expectsSame() {
        //Given
        val expected = listOf(sampleData, sampleData2)
        val expectedFiltered = listOf(sampleData2)
        coEvery {
            searchTipUseCase.invoke()
        } returns flowOf(expected)
        coEvery {
            searchTipUseCase.invoke(any(), any())
        } returns flowOf(expectedFiltered)

        //When
        target.onShowAllPayments()
        //Then
        Assert.assertEquals(expected, target.payments.value)
    }

    @Test
    fun onShowAllPayments_givenDataRange_expectsFiltered() {
        //Given
        val expected = listOf(sampleData, sampleData2)
        val expectedFiltered = listOf(sampleData2)
        val startDate = 150L
        val endDate = 250L
        coEvery {
            searchTipUseCase.invoke()
        } returns flowOf(expected)
        coEvery {
            searchTipUseCase.invoke(startDate, endDate)
        } returns flowOf(expectedFiltered)

        //When
        target.onShowAllPayments(startDate, endDate)
        //Then
        Assert.assertEquals(expectedFiltered, target.payments.value)
    }
}