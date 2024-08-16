package com.jjapps.tamingtemper.domain

import com.jjapps.tamingtemper.data.Repository
import com.jjapps.tamingtemper.data.local.model.LevelData
import javax.inject.Inject

class GetLevelList @Inject constructor(private val repository: Repository){

    suspend fun execute() : List<LevelData.Level?> {
        return repository.getLevelList()
    }

}