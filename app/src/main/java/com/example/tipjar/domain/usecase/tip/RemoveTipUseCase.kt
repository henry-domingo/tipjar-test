package com.example.tipjar.domain.usecase.tip

import com.example.tipjar.data.repository.TipRepositoryImpl
import com.example.tipjar.domain.model.TipHistory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoveTipUseCase(private val repository: TipRepositoryImpl) {
    suspend fun invoke(tipToBeRemoved: TipHistory) {
        withContext(Dispatchers.IO) {
            repository.deleteTip(tipToBeRemoved)
        }
    }
}