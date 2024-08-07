package com.example.core.core.domain.repository

import com.example.core.core.data.source.Resource
import com.example.core.core.domain.model.Agent
import kotlinx.coroutines.flow.Flow

interface IAgentRepository {
    fun getAllAgents(): Flow<Resource<List<Agent>>>
    fun getFavoriteAgent(): Flow<List<Agent>>
    fun setFavoriteAgent(agents: Agent, state: Boolean)

}