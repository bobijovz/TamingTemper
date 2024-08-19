package com.jjapps.tamingtemper.data.local.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(
    tableName = "Levels",
    indices = [Index(value = ["level"], unique = true)] // Unique index on level column
)
data class LevelEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val description: String,
    val level: String, // This is the level name or identifier
    val state: String,
    val title: String
)

data class LevelWithActivitiesEntity(
    @Embedded val level: LevelEntity,
    @Relation(
        parentColumn = "level",
        entityColumn = "level"
    )
    val activities: List<ActivityEntity>
)