package com.jjapps.tamingtemper.data.remote.model

import kotlinx.serialization.Serializable


@Serializable
data class LevelDataResponse(val levels: List<Level?>?) {
    @Serializable
    data class Level(
        val activities: List<Activity?>?,
        val description: String?,
        val level: String?,
        val state: String?,
        val title: String?)

    @Serializable
    data class Activity(
        val challengeId: String?,
        val description: String?,
        val descriptionB: String?,
        val icon: Icon?,
        val id: String?,
        val lockedIcon: LockedIcon?,
        val state: String?,
        val title: String?,
        val titleB: String?,
        val type: String?
    )

    @Serializable
    data class Icon(
        val description: String?,
        val file: File?,
        val title: String?
    )

    @Serializable
    data class File(
        val contentType: String?,
        val details: Details?,
        val fileName: String?,
        val url: String?
    )

    @Serializable
    data class LockedIcon(
        val description: String?,
        val file: File?,
        val title: String?
    )

    @Serializable
    data class Details(
        val size: Int?
    )
}