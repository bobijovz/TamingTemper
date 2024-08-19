package com.jjapps.tamingtemper.data.remote

import android.content.Context
import android.graphics.Bitmap
import com.jjapps.tamingtemper.data.utils.CachingUtils
import com.jjapps.tamingtemper.data.utils.PdfRendererUtils
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
        val cachedBitmap = CachingUtils.getCachedBitmap(context, pdfUrl)
        return cachedBitmap ?: run {
            val pdfFile = downloadPdf(pdfUrl)
            pdfFile?.let {
                val bitmap = PdfRendererUtils.renderPdfToBitmap(it)
                CachingUtils.saveBitmapToCache(context, pdfUrl, bitmap)
                bitmap
            }
        }
    }

    private fun downloadPdf(pdfUrl: String): File? {
        val request = Request.Builder()
            .url(pdfUrl)
            .build()

        try {
            val response = client.newCall(request).execute()
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val body: ResponseBody? = response.body
            if (body != null) {
                val pdfFile = File(context.cacheDir, pdfUrl.hashCode().toString() + ".pdf")
                val outputStream = FileOutputStream(pdfFile)
                outputStream.use {
                    it.write(body.bytes())
                }
                return pdfFile
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }
}