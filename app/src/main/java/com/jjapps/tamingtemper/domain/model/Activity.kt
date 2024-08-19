package com.jjapps.tamingtemper.domain.model

import android.graphics.Bitmap


data class Activity(
    val challengeId: String,
    val description: String,
    val descriptionB: String,
    val icon: String, // PDF URL
    var iconThumb: Bitmap? = null,
    val lockedIcon: String, // PDF URL
    var lockedIconThumb: Bitmap? = null,
    val state: String,
    val title: String,
    val titleB: String,
    val type: String,
    val level: String
)