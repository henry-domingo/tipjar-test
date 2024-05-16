package com.example.tipjar.domain.repository

import androidx.lifecycle.LiveData
import com.example.tipjar.domain.model.TipHistory

interface TipRepository {
    suspend fun insertTip(tip: TipHistory)
    suspend fun deleteTip(tip: TipHistory)
    fun getTipsFindAll(): LiveData<List<TipHistory>>
    fun getTipsFindByDate(dateStart: Long, dateEnd: Long): LiveData<List<TipHistory>>
}