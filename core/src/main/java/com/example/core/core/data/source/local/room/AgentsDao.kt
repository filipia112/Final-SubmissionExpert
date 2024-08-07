package com.example.core.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.core.core.data.source.local.entity.AgentsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AgentsDao {
    @Query("SELECT * FROM agents")
    fun getAllAgents(): Flow<List<AgentsEntity>>

    @Query("SELECT * FROM agents WHERE uuid != :excludedUuid")
    fun getAllAgentsExcluding(excludedUuid: String): Flow<List<AgentsEntity>>

    @Query("SELECT * FROM agents WHERE isFavorite = 1")
    fun getFavoriteAgents(): Flow<List<AgentsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAgents(agents: List<AgentsEntity>)

    @Update
    fun updateFavoriteAgents(agents: List<AgentsEntity>)

    @Query("UPDATE agents SET isFavorite = :state WHERE uuid = :uuid")
    suspend fun updateFavoriteStatus(uuid: String, state: Boolean)
}
