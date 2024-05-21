package com.example.tipjar.domain.repository

import com.example.tipjar.domain.model.TipHistory
import kotlinx.coroutines.flow.Flow

/**
 * Tip repository
 *
 * @constructor Create empty Tip repository
 */
interface TipRepository {
    suspend fun insertTip(tip: TipHistory)
    suspend fun deleteTip(tip: TipHistory)
    fun getTipsFindAll(): Flow<List<TipHistory>>
    fun getTipsFindByDate(dateStart: Long, dateEnd: Long): Flow<List<TipHistory>>
}