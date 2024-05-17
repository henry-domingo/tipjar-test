package com.example.tipjar.domain.usecase.tip

import com.example.tipjar.data.repository.TipRepositoryImpl
import com.example.tipjar.domain.model.TipHistory

class CreateTipUseCase(private val repository: TipRepositoryImpl) {
    suspend fun invoke(amount: Double, tip: Double, imagePath: String = "") {
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