package com.example.core.core.data.source
import com.example.core.core.data.source.local.LocalDataSource
import com.example.core.core.data.source.remote.RemoteDataSource
import com.example.core.core.data.source.remote.network.Apiresponse
import com.example.core.core.data.source.remote.response.GetAgentResponse
import com.example.core.core.domain.model.Agent
import com.example.core.core.domain.repository.IAgentRepository
import com.example.core.core.utils.AppExecutors
import com.example.core.core.utils.DataMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AgentsRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IAgentRepository {
    override fun getAllAgents(): Flow<Resource<List<Agent>>> =
        object : NetworkBoundResource<List<Agent>, GetAgentResponse>(appExecutors) {
            override fun loadFromDB(): Flow<List<Agent>> {
                return localDataSource.getAllAgents().map { entities ->
                    entities.map { DataMapper.mapEntityToDomain(it) }
                }
            }

            override fun shouldFetch(data: List<Agent>?): Boolean =
                data.isNullOrEmpty()

            override suspend fun createCall(): Flow<Apiresponse<GetAgentResponse>> {
                return remoteDataSource.getAllAgents()
            }

            override suspend fun saveCallResult(data: GetAgentResponse) {
                val agentsEntities = DataMapper.mapApiResponseToEntity(data)
                localDataSource.insertAgents(agentsEntities)
            }
        }.result

    override fun getFavoriteAgent(): Flow<List<Agent>> {
        return localDataSource.getFavoriteAgents().map { entities ->
            entities.map { DataMapper.mapEntityToDomain(it) }
        }
    }

    override fun setFavoriteAgent(agent: Agent, state: Boolean) {
        val agentEntity = DataMapper.mapDomainToEntity(agent)
        agentEntity.isFavorite = state
        CoroutineScope(Dispatchers.IO).launch {
            localDataSource.setFavoriteAgent(agentEntity.uuid, state)
        }
    }

}