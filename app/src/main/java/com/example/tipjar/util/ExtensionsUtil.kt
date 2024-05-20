package com.example.tipjar.util

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.toDateString(): String {
    val formatter = SimpleDateFormat("yyyy MMMM dd", Locale.getDefault())
    return formatter.format(Date(this))
}

fun Context.createImageFile(): File {
    val imageFileName = System.currentTimeMillis()
    val image = File.createTempFile(
        "$imageFileName", /* prefix */
        ".img",
        filesDir
    )
    return image
}

fun Context.getFilePath(fileName: String): String = File(filesDir, fileName).absolutePath

inline fun <reified T> Gson.fromJson(json: String) =
    fromJson<T>(json, object : TypeToken<T>() {}.type)
