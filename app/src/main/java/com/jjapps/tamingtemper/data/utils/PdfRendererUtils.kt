package com.jjapps.tamingtemper.data.utils

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import java.io.File

object PdfRendererUtils {
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