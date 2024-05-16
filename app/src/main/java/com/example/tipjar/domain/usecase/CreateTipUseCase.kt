package com.example.tipjar.domain.usecase

import com.example.tipjar.domain.model.TipHistory
import com.example.tipjar.domain.repository.TipRepository

class CreateTipUseCase(private val repository: TipRepository) {
    suspend operator fun invoke(newTip: TipHistory){
        return repository.insertTip(newTip)
    }
}