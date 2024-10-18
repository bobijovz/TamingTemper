package com.jjapps.tamingtemper.data.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import java.io.File
import java.io.FileOutputStream

object BitmapUtils {

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

    fun renderPdfToBitmap(pdfFile: File): Bitmap? {
        var pdfRenderer: PdfRenderer? = null
        var fileDescriptor: ParcelFileDescriptor? = null
        try {
            fileDescriptor = ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY)
            pdfRenderer = PdfRenderer(fileDescriptor)
            val page = pdfRenderer.openPage(0) // Render the first page of the PDF

            val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            page.close()

            return bitmap
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            pdfRenderer?.close()
            fileDescriptor?.close()
        }
        return null
    }
}