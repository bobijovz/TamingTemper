package com.jjapps.tamingtemper.data.remote

import android.content.Context
import com.jjapps.tamingtemper.data.remote.model.LevelDataResponse

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.InputStream
import javax.inject.Inject

class LevelDataSource @Inject constructor(private val context: Context){

    suspend fun getLevelList() : List<LevelDataResponse.Level?>{
        return withContext(Dispatchers.IO) {
            val jsonString = readJsonFromAsset(context = context,"level.json")
            parseJson(jsonString)
        }
    }

    private fun readJsonFromAsset(context: Context, fileName: String): String {
        val inputStream: InputStream = context.assets.open(fileName)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        return String(buffer)

    }

    private fun parseJson(jsonString: String): List<LevelDataResponse.Level?> {
        val json = Json { ignoreUnknownKeys = true }
        return json.decodeFromString<LevelDataResponse>(jsonString).levels ?: emptyList()
    }


}