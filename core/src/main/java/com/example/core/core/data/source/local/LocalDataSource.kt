package com.example.core.core.data.source.local

import com.example.core.core.data.source.local.entity.AgentsEntity
import com.example.core.core.data.source.local.room.AgentsDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton
@Singleton
class LocalDataSource @Inject constructor(
    private val agentsDao: AgentsDao
) {
    private val excludedUuid = "ded3520f-4264-bfed-162d-b080e2abccf9"

    fun getAllAgents(): Flow<List<AgentsEntity>> {
        return agentsDao.getAllAgentsExcluding(excludedUuid)
    }
    fun getFavoriteAgents(): Flow<List<AgentsEntity>> = agentsDao.getFavoriteAgents()
    fun insertAgents(agents: List<AgentsEntity>) = agentsDao.insertAgents(agents)
    fun setFavoriteAgent(uuid: String, newState: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            agentsDao.updateFavoriteStatus(uuid, newState)
        }
    }
}
