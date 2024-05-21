package com.example.tipjar.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.tipjar.domain.model.TipHistory
import kotlinx.coroutines.flow.Flow

/**
 * Interface for database access for TipHistory
 *
 * @constructor Create empty Tip history dao
 */
@Dao
interface TipHistoryDao {
    /**
     * Inserts new tip history
     *
     * @param tip
     */
    @Insert
    suspend fun insertTip(tip: TipHistory)

    /**
     * Deletes tip history
     *
     * @param tip Tip to be deleted
     */
    @Delete
    suspend fun deleteTip(tip: TipHistory)

    /**
     * Retrieve all entries
     *
     * @return Flow of entry list
     */
    @Query("SELECT * FROM tip_history ORDER BY timestamp DESC")
    fun findAll(): Flow<List<TipHistory>>

    /**
     * Retrieve all entries filtered with the given date range
     *
     * @param dateStart
     * @param dateEnd
     * @return Flow of entry list
     */
    @Query("SELECT * FROM tip_history WHERE timestamp BETWEEN :dateStart AND :dateEnd ORDER BY timestamp DESC")
    fun findByDate(dateStart: Long, dateEnd: Long): Flow<List<TipHistory>>
}