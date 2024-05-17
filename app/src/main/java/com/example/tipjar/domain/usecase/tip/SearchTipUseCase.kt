package com.example.tipjar.domain.usecase.tip

import com.example.tipjar.data.repository.TipRepositoryImpl
import com.example.tipjar.domain.model.TipHistory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class SearchTipUseCase(private val repository: TipRepositoryImpl) {
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