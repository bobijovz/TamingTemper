package com.jjapps.tamingtemper.data.remote

import android.content.Context
import android.graphics.Bitmap
import com.jjapps.tamingtemper.data.utils.BitmapUtils
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

class PdfService @Inject constructor(private val context: Context){
    private val client = OkHttpClient()

    fun getPdfThumbnail(pdfUrl: String): Bitmap? {
        return BitmapUtils.getCachedBitmap(context, pdfUrl) ?: generateThumbnailFromPdf(pdfUrl)
    }

    private fun generateThumbnailFromPdf(pdfUrl: String): Bitmap? {
        val pdfFile = downloadPdf(pdfUrl)
        return pdfFile?.let {
            val bitmap = BitmapUtils.renderPdfToBitmap(it)
            BitmapUtils.saveBitmapToCache(context, pdfUrl, bitmap)
            bitmap
        }
    }

    private fun downloadPdf(pdfUrl: String): File? {
        val request = Request.Builder().url(pdfUrl).build()

        return try {
            val response = client.newCall(request).execute()
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            response.body?.let { body ->
                val pdfFile = File(context.cacheDir, pdfUrl.hashCode().toString() + ".pdf")
                FileOutputStream(pdfFile).use { it.write(body.bytes()) }
                pdfFile
            }
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}