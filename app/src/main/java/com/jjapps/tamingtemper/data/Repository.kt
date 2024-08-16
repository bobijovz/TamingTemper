package com.jjapps.tamingtemper.data

import com.jjapps.tamingtemper.data.local.model.LevelData
import com.jjapps.tamingtemper.data.local.model.LevelDataSource
import javax.inject.Inject


class Repository @Inject constructor(private val levelDataSource: LevelDataSource){

    suspend fun getLevelList() : List<LevelData.Level?> {
        return levelDataSource.getLevelList()
    }
}