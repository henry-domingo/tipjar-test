package com.example.tipjar.domain.usecase

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream

/**
 * Use case for reading a JSON asset to a list object
 *
 * @property ioDispatcher
 * @constructor Create empty Json asset to object list use case
 */
class JSONAssetToObjectListUseCase(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun <T> invoke(clazz: Class<T>, inputStream: InputStream): HashMap<String, T> {
        return withContext(ioDispatcher) {
            val jsonString: String
            try {
                jsonString = inputStream.bufferedReader()
                    .use {
                        it.readText()
                    }
                val type =
                    TypeToken.getParameterized(HashMap::class.java, String::class.java, clazz).type
                Gson().fromJson(jsonString, type)
            } catch (ex: Exception) {
                ex.printStackTrace()
                hashMapOf()
            }
        }
    }

}