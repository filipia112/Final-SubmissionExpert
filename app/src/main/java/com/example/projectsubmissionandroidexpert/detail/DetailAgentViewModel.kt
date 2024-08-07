package com.example.projectsubmissionandroidexpert.detail
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.core.core.domain.model.Agent
import com.example.core.core.domain.usecase.AgentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class DetailAgentViewModel @Inject constructor(private val agentUseCase: AgentUseCase): ViewModel() {
    private val _favoriteStatus = MutableLiveData<Boolean>()
    val favoriteStatus: LiveData<Boolean> get() = _favoriteStatus
    fun setFavoriteAgent(agentDetail: Agent, newStatus: Boolean) {
        agentUseCase.setFavoriteAgent(agentDetail, newStatus)
        _favoriteStatus.value = newStatus
    }
}