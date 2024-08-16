package com.jjapps.tamingtemper.data.local.model

import android.app.Application
import android.content.Context
import android.util.Log
import com.jjapps.tamingtemper.TamingTemperApp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.InputStream
import javax.inject.Inject

class LevelDataSource @Inject constructor(){

    //todo remove context reference here
    suspend fun getLevelList() : List<LevelData.Level?>{
        return withContext(Dispatchers.IO) {
            val jsonString = readJsonFromAsset(context = TamingTemperApp.context,"level.json")
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

    private fun parseJson(jsonString: String): List<LevelData.Level?> {
        val json = Json { ignoreUnknownKeys = true }
        return json.decodeFromString<LevelData>(jsonString).levels ?: emptyList()
    }


}