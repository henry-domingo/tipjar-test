package com.example.tipjar.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tipjar.domain.model.TipHistory

const val TIP_DB = "tip_jar_database"

/**
 * Tip Room database
 *
 * @constructor Create empty Tip database
 */
@Database(entities = [TipHistory::class], version = 1, exportSchema = false)
abstract class TipDatabase : RoomDatabase() {
    abstract fun getTipHistoryDao(): TipHistoryDao
}