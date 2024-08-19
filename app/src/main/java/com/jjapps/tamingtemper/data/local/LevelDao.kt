package com.jjapps.tamingtemper.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.jjapps.tamingtemper.data.local.model.ActivityEntity
import com.jjapps.tamingtemper.data.local.model.LevelEntity
import com.jjapps.tamingtemper.data.local.model.LevelWithActivitiesEntity

@Dao
interface LevelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLevel(level: LevelEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivities(activities: List<ActivityEntity>)

    // Transaction to ensure atomicity
    @Transaction
    suspend fun upsertLevelWithActivities(level: LevelEntity, activities: List<ActivityEntity>) {
        insertLevel(level)
        insertActivities(activities)
    }

    @Transaction
    @Query("SELECT * FROM Levels WHERE level = :level")
    suspend fun getLevelWithActivities(level: String): LevelWithActivitiesEntity

    @Transaction
    @Query("SELECT * FROM Levels")
    suspend fun getAllLevelsWithActivities(): List<LevelWithActivitiesEntity>


}