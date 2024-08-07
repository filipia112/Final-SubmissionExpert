package com.example.core.core.utils
import com.example.core.core.data.source.local.entity.AgentsEntity
import com.example.core.core.data.source.remote.response.GetAgentResponse
import com.example.core.core.domain.model.Agent

object DataMapper {
    fun mapApiResponseToEntity(apiResponse: GetAgentResponse): List<AgentsEntity> {
        val dataList = apiResponse.data
        return dataList.map { data ->
            AgentsEntity(
                uuid = data.uuid.orEmpty(),
                displayName = data.displayName.orEmpty(),
                description = data.description.orEmpty(),
                developerName = data.developerName.orEmpty(),
                fullPortrait = data.fullPortrait.orEmpty(),
                killfeedPortrait = data.killfeedPortrait.orEmpty(),
                background = data.background.orEmpty(),
                isFavorite = false,
                backgroundGradientColors = data.backgroundGradientColors?.joinToString(",") ?: "",
            )
        }
    }

    fun mapEntityToDomain(entity: AgentsEntity): Agent {
        return Agent(
            uuid = entity.uuid,
            display = entity.displayName,
            displayName = entity.displayName,
            description = entity.description,
            developerName = entity.developerName,
            fullPortrait = entity.fullPortrait,
            killfeedPortrait = entity.killfeedPortrait,
            background = entity.background,
            isFavorite = entity.isFavorite,
            backgroundGradientColors = entity.backgroundGradientColors,
        )
    }
    fun mapDomainToEntity(domain: Agent): AgentsEntity {
        return AgentsEntity(
            uuid = domain.uuid,
            displayName = domain.displayName,
            description = domain.description,
            developerName = domain.developerName,
            fullPortrait = domain.fullPortrait,
            killfeedPortrait = domain.killfeedPortrait,
            background = domain.background,
            isFavorite = domain.isFavorite,
            backgroundGradientColors = domain.backgroundGradientColors ?: "",
        )
    }
}