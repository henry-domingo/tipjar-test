package com.example.tipjar.domain.usecase.tip

import com.example.tipjar.domain.model.TipHistory
import com.example.tipjar.domain.repository.TipRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Use case to create a tip
 *
 * @property ioDispatcher
 * @property repository
 * @constructor Create empty Create tip use case
 */
class CreateTipUseCase(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val repository: TipRepository
) {
    suspend fun invoke(amount: Double, tip: Double, imagePath: String = "") {
        withContext(ioDispatcher) {
            repository.insertTip(
                TipHistory(
                    timestamp = System.currentTimeMillis(),
                    amount = amount,
                    tip = tip,
                    imagePath = imagePath,
                )
            )
        }
    }
}