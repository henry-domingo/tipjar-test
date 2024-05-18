package com.example.tipjar.domain.usecase.tip

import com.example.tipjar.domain.model.TipHistory
import com.example.tipjar.domain.repository.TipRepository

class CreateTipUseCase(private val repository: TipRepository) {
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