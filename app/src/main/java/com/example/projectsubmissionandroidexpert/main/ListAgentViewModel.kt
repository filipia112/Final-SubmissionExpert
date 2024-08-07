package com.example.projectsubmissionandroidexpert

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.core.domain.usecase.AgentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListAgentViewModel @Inject constructor(agentsUseCase: AgentUseCase): ViewModel() {
    val agent = agentsUseCase.getAllAgents().asLiveData()
}