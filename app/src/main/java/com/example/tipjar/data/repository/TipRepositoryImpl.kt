package com.example.tipjar.data.repository

import androidx.lifecycle.LiveData
import com.example.tipjar.data.db.TipHistoryDao
import com.example.tipjar.domain.model.TipHistory
import com.example.tipjar.domain.repository.TipRepository

class TipRepositoryImpl(private val tipDao: TipHistoryDao) : TipRepository {
    override suspend fun insertTip(tip: TipHistory) = tipDao.insertTip(tip)

    override suspend fun deleteTip(tip: TipHistory) = tipDao.deleteTip(tip)

    override fun getTipsFindAll(): LiveData<List<TipHistory>> = tipDao.findAll()

    override fun getTipsFindByDate(dateStart: Long, dateEnd: Long): LiveData<List<TipHistory>> =
        tipDao.findByDate(dateStart, dateEnd)

}