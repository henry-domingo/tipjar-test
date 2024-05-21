package com.example.tipjar.domain.usecase.tip

import com.example.tipjar.domain.model.TipHistory
import com.example.tipjar.domain.repository.TipRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * Use case for Searching tips
 *
 * @property ioDispatcher
 * @property repository
 * @constructor Create empty Search tip use case
 */
class SearchTipUseCase(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val repository: TipRepository
) {
    suspend fun invoke(): Flow<List<TipHistory>> {
        return withContext(ioDispatcher) {
            repository.getTipsFindAll()
        }
    }

    suspend fun invoke(dateStart: Long, dateEnd: Long): Flow<List<TipHistory>> {
        return withContext(ioDispatcher) {
            repository.getTipsFindByDate(dateStart, dateEnd)
        }
    }
}