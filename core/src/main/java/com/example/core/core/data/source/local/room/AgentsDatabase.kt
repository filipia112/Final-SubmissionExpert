package com.example.core.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core.core.data.source.local.entity.AgentsEntity

@Database(
    entities = [AgentsEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AgentsDatabase : RoomDatabase () {
    abstract fun agentsDao() : AgentsDao
}