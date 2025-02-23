package com.example.tipjar.util

import android.content.Context
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Converts a Unix timestamp to a formatted date string.
 *
 * @return
 */
fun Long.toDateString(): String {
    val formatter = SimpleDateFormat("yyyy MMMM dd", Locale.getDefault())
    return formatter.format(Date(this))
}

/**
 * Creates a temporary image file in the app's files directory.
 *
 * @return
 */
fun Context.createImageFile(): File {
    val imageFileName = System.currentTimeMillis()
    val image = File.createTempFile(
        "$imageFileName", /* prefix */
        ".img",
        filesDir
    )
    return image
}

/**
 * Gets the absolute path of a file in the app's files directory.
 *
 * @param fileName
 * @return
 */
fun Context.getFilePath(fileName: String): String = File(filesDir, fileName).absolutePath