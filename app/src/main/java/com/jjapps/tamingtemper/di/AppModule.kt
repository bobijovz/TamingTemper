package com.jjapps.tamingtemper.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.jjapps.tamingtemper.data.RepositoryImpl
import com.jjapps.tamingtemper.data.local.LevelDataSource
import com.jjapps.tamingtemper.data.local.LevelDatabase
import com.jjapps.tamingtemper.data.remote.PdfService
import com.jjapps.tamingtemper.domain.GetLevelListUseCase
import com.jjapps.tamingtemper.domain.abstraction.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideLevelDatabase(app: Application): LevelDatabase {
        return Room.databaseBuilder(
            app,
            LevelDatabase::class.java,
            LevelDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideLevelRepository(datasource: LevelDataSource, db: LevelDatabase, service: PdfService): Repository {
        return RepositoryImpl(datasource, db.levelDao, service)
    }


    @Provides
    @Singleton
    fun provideUseCases(repository: Repository): GetLevelListUseCase {
        return GetLevelListUseCase(repository)
    }

}