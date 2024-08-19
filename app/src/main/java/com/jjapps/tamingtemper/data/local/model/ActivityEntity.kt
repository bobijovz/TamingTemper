package com.jjapps.tamingtemper.data.local.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Activities",
    foreignKeys = [ForeignKey(
        entity = LevelEntity::class,
        parentColumns = ["level"], // Column in Level entity
        childColumns = ["level"], // Corresponding column in Activity entity
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["level"])]
)
data class ActivityEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val activityId: String,
    val challengeId: String,
    val description: String,
    val descriptionB: String,
    val icon: String, // PDF URL
    val lockedIcon: String, // PDF URL
    val state: String,
    val title: String,
    val titleB: String,
    val type: String,
    val level: String // Foreign key referencing Level's level
)