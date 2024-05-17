package com.example.tipjar.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.tipjar.domain.model.TipHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface TipHistoryDao {
    @Insert
    suspend fun insertTip(tip: TipHistory)

    @Delete
    suspend fun deleteTip(tip: TipHistory)

    @Query("SELECT * FROM tip_history ORDER BY timestamp DESC")
    fun findAll(): Flow<List<TipHistory>>

    @Query("SELECT * FROM tip_history WHERE timestamp BETWEEN :dateStart AND :dateEnd")
    fun findByDate(dateStart: Long, dateEnd: Long): Flow<List<TipHistory>>
}