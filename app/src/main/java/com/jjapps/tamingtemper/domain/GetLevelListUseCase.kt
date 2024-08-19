package com.jjapps.tamingtemper.domain

import com.jjapps.tamingtemper.domain.abstraction.Repository
import com.jjapps.tamingtemper.domain.model.Level
import javax.inject.Inject

class GetLevelListUseCase @Inject constructor(private val repository: Repository){

    suspend fun execute() : List<Level> {
        return repository.getAllLevelsWithActivities()
    }

}