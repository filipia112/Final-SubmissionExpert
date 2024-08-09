package com.example.core.core.di

import com.example.core.core.data.source.AgentsRepository
import com.example.core.core.data.source.local.LocalDataSource
import com.example.core.core.data.source.remote.RemoteDataSource
import com.example.core.core.domain.repository.IAgentRepository
import com.example.core.core.utils.AppExecutors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideAgentRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource,
        appExecutors: AppExecutors
    ): IAgentRepository {
        return AgentsRepository(remoteDataSource, localDataSource, appExecutors)
    }
}