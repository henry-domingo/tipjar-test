package com.example.tipjar.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.toDateString(): String {
    val formatter = SimpleDateFormat("yyyy MMMM dd", Locale.getDefault())
    return formatter.format(Date(this))
}