package com.jjapps.tamingtemper.domain.abstraction


import android.graphics.Bitmap
import com.jjapps.tamingtemper.domain.model.Level


interface Repository {

    suspend fun getLevelWithActivities(level: String): Level

    suspend fun getAllLevelsWithActivities(): List<Level>

    suspend fun generatePdfThumbnailUseCase(pdfUrl:String): Bitmap?
}