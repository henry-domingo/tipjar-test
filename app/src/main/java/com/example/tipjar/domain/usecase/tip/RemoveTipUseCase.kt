package com.example.tipjar.domain.usecase.tip

import com.example.tipjar.domain.model.TipHistory
import com.example.tipjar.domain.repository.TipRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Use case for removing a tip from the database
 *
 * @property repository
 * @constructor Create empty Remove tip use case
 */
class RemoveTipUseCase(private val repository: TipRepository) {
    suspend fun invoke(tipToBeRemoved: TipHistory) {
        withContext(Dispatchers.IO) {
            repository.deleteTip(tipToBeRemoved)
        }
    }
}