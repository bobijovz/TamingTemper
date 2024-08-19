package com.jjapps.tamingtemper.data.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File
import java.io.FileOutputStream

object CachingUtils {

    private const val CACHE_DIR = "pdf_thumbnails"

    fun saveBitmapToCache(context: Context, url: String, bitmap: Bitmap?) {
        if (bitmap == null) return

        val cacheDir = File(context.cacheDir, CACHE_DIR)
        if (!cacheDir.exists()) cacheDir.mkdir()

        val cacheFile = File(cacheDir, url.hashCode().toString())
        FileOutputStream(cacheFile).use { out ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        }
    }

    fun getCachedBitmap(context: Context, url: String): Bitmap? {
        val cacheDir = File(context.cacheDir, CACHE_DIR)
        val cacheFile = File(cacheDir, url.hashCode().toString())
        return if (cacheFile.exists()) {
            BitmapFactory.decodeFile(cacheFile.path)
        } else {
            null
        }
    }
}