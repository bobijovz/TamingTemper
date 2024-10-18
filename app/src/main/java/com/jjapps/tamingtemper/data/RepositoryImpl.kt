package com.jjapps.tamingtemper.data

import android.graphics.Bitmap
import com.jjapps.tamingtemper.data.remote.LevelDataSource
import com.jjapps.tamingtemper.data.local.LevelDao
import com.jjapps.tamingtemper.data.local.model.ActivityEntity
import com.jjapps.tamingtemper.data.local.model.LevelEntity
import com.jjapps.tamingtemper.data.remote.PdfService
import com.jjapps.tamingtemper.domain.abstraction.Repository
import com.jjapps.tamingtemper.domain.model.Activity
import com.jjapps.tamingtemper.domain.model.Level

import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val levelDataSource: LevelDataSource,
    private val levelDao: LevelDao,
    private val pdfService: PdfService
) : Repository {

    /*override suspend fun getLevelWithActivities(level: String): Level {
        val temp = levelDao.getLevelWithActivities(level)
        return Level(
            description = temp.level.description,
            level = temp.level.level,
            state = temp.level.state,
            title = temp.level.title,
            activities = temp.activities.map { a ->
                Activity(
                    challengeId = a.challengeId,
                    description = a.description,
                    descriptionB = a.descriptionB,
                    icon = a.icon,
                    lockedIcon = a.lockedIcon,
                    state = a.state,
                    title = a.title,
                    titleB = a.titleB,
                    type = a.type,
                    level = a.level
                )
            }
        )
    }*/

    override suspend fun getAllLevelsWithActivities(): List<Level> {

        //Updating level and activity data
        levelDataSource.getLevelList().forEach { level ->

            //Saving level
            val temp = LevelEntity (
                description = level?.description ?: "",
                level = level?.level ?: "",
                state = level?.state ?: "",
                title = level?.title ?: ""
            )
            levelDao.insertLevel(temp)

            //Saving activities
            val activities = level?.activities?.map { activity ->
                ActivityEntity(
                    challengeId = activity?.challengeId ?: "",
                    description = activity?.description ?: "" ,
                    descriptionB = activity?.descriptionB ?: "",
                    icon = activity?.icon?.file?.url ?: "",
                    lockedIcon = activity?.lockedIcon?.file?.url ?: "",
                    state = activity?.state ?: "",
                    title = activity?.title ?: "",
                    titleB = activity?.titleB ?: "",
                    type = activity?.type ?: "",
                    activityId = activity?.id ?: "",
                    level = level.level ?: ""
                    )
            }?.toList()

            if (!activities.isNullOrEmpty()) {
                levelDao.insertActivities(activities)
            }
        }

        val result = levelDao.getAllLevelsWithActivities().map { level ->
            Level(
                description = level.level.description,
                level = level.level.level,
                state = level.level.state,
                title = level.level.title,
                activities = level.activities.map { a ->
                    Activity(
                        challengeId = a.challengeId,
                        description = a.description,
                        descriptionB = a.descriptionB,
                        icon = a.icon,
                        lockedIcon = a.lockedIcon,
                        state = a.state,
                        title = a.title,
                        titleB = a.titleB,
                        type = a.type,
                        level = a.level
                    )
                }
            )

        }

        return result
    }

    override suspend fun generatePdfThumbnailUseCase(pdfUrl:String): Bitmap? {
        return pdfService.getPdfThumbnail(pdfUrl)
    }
}