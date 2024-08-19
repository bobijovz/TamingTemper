package com.jjapps.tamingtemper.domain

import android.graphics.Bitmap
import com.jjapps.tamingtemper.data.remote.PdfService
import com.jjapps.tamingtemper.domain.abstraction.Repository
import com.jjapps.tamingtemper.domain.model.Level
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GenerateThumbnailUseCase @Inject constructor(private val repository: Repository){

    suspend fun execute(pdfUrl: String): Bitmap? {
        return withContext(Dispatchers.IO) {
            repository.generatePdfThumbnailUseCase(pdfUrl)
        }
    }
}