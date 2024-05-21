package com.example.tipjar.data.repository

import com.example.tipjar.data.db.TipHistoryDao
import com.example.tipjar.domain.model.TipHistory
import com.example.tipjar.domain.repository.TipRepository
import kotlinx.coroutines.flow.Flow

/**
 * Implementation of [TipRepository]
 *
 * @property tipDao
 * @constructor Create empty Tip repository impl
 */
class TipRepositoryImpl(private val tipDao: TipHistoryDao) : TipRepository {
    override suspend fun insertTip(tip: TipHistory) = tipDao.insertTip(tip)

    override suspend fun deleteTip(tip: TipHistory) =
        tipDao.deleteTip(tip)


    override fun getTipsFindAll(): Flow<List<TipHistory>> =
        tipDao.findAll()


    override fun getTipsFindByDate(dateStart: Long, dateEnd: Long): Flow<List<TipHistory>> =
        tipDao.findByDate(dateStart, dateEnd)

}