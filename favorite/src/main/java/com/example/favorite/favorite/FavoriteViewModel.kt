package com.example.favorite.favorite

import androidx.lifecycle.ViewModel
import com.example.core.core.domain.model.Agent
import com.example.core.core.domain.usecase.AgentUseCase
import kotlinx.coroutines.flow.Flow
class FavoriteViewModel (agentUseCase: AgentUseCase) : ViewModel() {
    val getFavorite: Flow<List<Agent>> = agentUseCase.getFavoriteAgent()
}
