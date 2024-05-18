package com.example.tipjar.domain.usecase.tip

import com.example.tipjar.domain.model.TipHistory
import com.example.tipjar.domain.repository.TipRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class SearchTipUseCase(private val repository: TipRepository) {
    suspend fun invoke(): Flow<List<TipHistory>> {
        return withContext(Dispatchers.IO) {
            repository.getTipsFindAll()
        }
    }

    suspend fun invoke(dateStart: Long, dateEnd: Long): Flow<List<TipHistory>> {
        return withContext(Dispatchers.IO) {
            repository.getTipsFindByDate(dateStart, dateEnd)
        }
    }
}