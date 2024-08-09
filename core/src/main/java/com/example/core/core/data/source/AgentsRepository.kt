package com.example.core.core.data.source
import com.example.core.core.data.source.local.LocalDataSource
import com.example.core.core.data.source.remote.RemoteDataSource
import com.example.core.core.data.source.remote.network.Apiresponse
import com.example.core.core.domain.model.Agent
import com.example.core.core.domain.repository.IAgentRepository
import com.example.core.core.utils.AppExecutors
import com.example.core.core.utils.DataMapper
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AgentsRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IAgentRepository {

    override fun getAllAgents(): Flow<Resource<List<Agent>>> = flow {
        emit(Resource.Loading())
        try {
            val localAgents = withContext(appExecutors.diskIO.asCoroutineDispatcher()) {
                localDataSource.getAllAgents().map { entities ->
                    entities.map { DataMapper.mapEntityToDomain(it) }
                }.firstOrNull()
            }

            if (localAgents.isNullOrEmpty()) {
                val response = withContext(appExecutors.networkIO.asCoroutineDispatcher()) {
                    remoteDataSource.getAllAgents().first()
                }
                when (response) {
                    is Apiresponse.Success -> {
                        val agentsEntities = DataMapper.mapApiResponseToEntity(response.data)
                        withContext(appExecutors.diskIO.asCoroutineDispatcher()) {
                            localDataSource.insertAgents(agentsEntities)
                        }
                        emit(Resource.Success(agentsEntities.map { DataMapper.mapEntityToDomain(it) }))
                    }
                    is Apiresponse.Error -> emit(Resource.Error(response.errorMessage))
                    is Apiresponse.Empty -> emit(Resource.Success(emptyList()))
                }
            } else {
                emit(Resource.Success(localAgents))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }.flowOn(appExecutors.mainThread.asCoroutineDispatcher())

    override fun getFavoriteAgent(): Flow<List<Agent>> = localDataSource.getFavoriteAgents()
        .map { entities ->
            entities.map { DataMapper.mapEntityToDomain(it) }
        }
        .flowOn(appExecutors.mainThread.asCoroutineDispatcher())
    override fun setFavoriteAgent(agents: Agent, state: Boolean) {
        val agentEntity = DataMapper.mapDomainToEntity(agents)
        agentEntity.isFavorite = state
        appExecutors.diskIO.execute {
            localDataSource.setFavoriteAgent(agentEntity.uuid, state)
        }
    }
}


