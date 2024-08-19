package com.jjapps.tamingtemper.domain.model


data class Level(
    val description: String,
    val level: String,
    val state: String,
    val title: String,
    val activities: List<Activity>
)

