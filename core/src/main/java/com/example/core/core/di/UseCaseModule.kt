package com.example.core.core.di

import com.example.core.core.domain.repository.IAgentRepository
import com.example.core.core.domain.usecase.AgentInteractor
import com.example.core.core.domain.usecase.AgentUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideAgentUseCase(repository: IAgentRepository): AgentUseCase {
        return AgentInteractor(repository)
    }
}
