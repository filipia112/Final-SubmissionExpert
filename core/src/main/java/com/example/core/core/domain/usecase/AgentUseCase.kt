package com.example.core.core.domain.usecase
import com.example.core.core.data.source.Resource
import com.example.core.core.domain.model.Agent
import kotlinx.coroutines.flow.Flow

interface AgentUseCase {
    fun getAllAgents(): Flow<Resource<List<Agent>>>
    fun getFavoriteAgent(): Flow<List<Agent>>
    fun setFavoriteAgent(agents: Agent, state: Boolean)
}