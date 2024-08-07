package com.example.core.core.di

import com.example.core.core.data.source.AgentsRepository
import com.example.core.core.domain.repository.IAgentRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(agentsRepository: AgentsRepository): IAgentRepository

}