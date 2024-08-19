package com.jjapps.tamingtemper.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jjapps.tamingtemper.data.local.model.ActivityEntity
import com.jjapps.tamingtemper.data.local.model.LevelEntity

@Database(
    entities = [LevelEntity::class, ActivityEntity::class],
    version = 1,
    exportSchema = false
)
abstract class LevelDatabase: RoomDatabase() {

    abstract val levelDao: LevelDao

    companion object {
        const val DATABASE_NAME = "levels_db"
    }
}