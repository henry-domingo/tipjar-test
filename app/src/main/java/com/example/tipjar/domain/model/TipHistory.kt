package com.example.tipjar.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "tip_history", primaryKeys = ["timestamp"])
data class TipHistory(
    @ColumnInfo(name = "timestamp")
    var timestamp: Long = 0L,
    @ColumnInfo(name = "amount")
    var amount: Double = 0.0,
    @ColumnInfo(name = "tip")
    var tip: Double = 0.0,
    @ColumnInfo(name = "image_path")
    var imagePath: String = "",
) : Parcelable