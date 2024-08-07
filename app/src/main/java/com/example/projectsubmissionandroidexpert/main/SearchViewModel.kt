package com.example.projectsubmissionandroidexpert

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.core.core.data.source.Resource
import com.example.core.core.domain.model.Agent
import com.example.core.core.domain.usecase.AgentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class SearchViewModel @Inject constructor(private val agentsUseCase: AgentUseCase) : ViewModel() {

    private val _agents = MutableStateFlow<List<Agent>?>(null)

    private val _query = MutableStateFlow("")
    private val _filteredAgents = MutableStateFlow<List<Agent>>(emptyList())
    val filteredAgentsLiveData: LiveData<List<Agent>> get() = _filteredAgents.asLiveData()
    init {
        viewModelScope.launch {
            agentsUseCase.getAllAgents()
                .collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            _agents.value = resource.data
                        }
                        is Resource.Error -> {
                            _agents.value = emptyList()
                        }
                        is Resource.Loading -> {
                            // Optionally handle loading state
                        }
                    }
                }
        }

        viewModelScope.launch {
            combine(_agents, _query) { agents, query ->
                filterAgents(agents.orEmpty(), query)
            }.collect { filteredAgents ->
                _filteredAgents.value = filteredAgents
            }
        }
    }

    private fun filterAgents(agents: List<Agent>, query: String): List<Agent> {
        return if (query.isEmpty()) {
            agents
        } else {
            agents.filter { it.displayName.contains(query, ignoreCase = true) }
            agents.filter { it.developerName.contains(query,ignoreCase = true)}
        }
    }

    fun search(query: String) {
        _query.value = query
    }
}