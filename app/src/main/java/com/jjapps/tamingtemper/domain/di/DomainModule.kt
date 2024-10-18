package com.jjapps.tamingtemper.domain.di

import com.jjapps.tamingtemper.domain.GetLevelListUseCase
import com.jjapps.tamingtemper.domain.abstraction.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideGetLevelListUseCase(repository: Repository): GetLevelListUseCase {
        return GetLevelListUseCase(repository)
    }

}