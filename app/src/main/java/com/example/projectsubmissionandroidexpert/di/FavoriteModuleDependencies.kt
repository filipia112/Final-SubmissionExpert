package com.example.projectsubmissionandroidexpert.di

import com.example.core.core.domain.usecase.AgentUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDependencies {
    fun agentUsecase(): AgentUseCase
}