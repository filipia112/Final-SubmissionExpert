package com.example.projectsubmissionandroidexpert.di
import com.example.core.core.domain.usecase.AgentInteractor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun provideAgentUsecase(agentInteractor: AgentInteractor): AgentInteractor

}