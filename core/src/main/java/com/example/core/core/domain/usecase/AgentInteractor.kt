package com.example.core.core.domain.usecase

import com.example.core.core.domain.model.Agent
import com.example.core.core.domain.repository.IAgentRepository
import javax.inject.Inject

class AgentInteractor @Inject constructor(private val agentsRepository: IAgentRepository):AgentUseCase {
    override fun getAllAgents() = agentsRepository.getAllAgents()

    override fun getFavoriteAgent() = agentsRepository.getFavoriteAgent()
    override fun setFavoriteAgent(agents: Agent, state: Boolean) =
        agentsRepository.setFavoriteAgent(agents, state)
}