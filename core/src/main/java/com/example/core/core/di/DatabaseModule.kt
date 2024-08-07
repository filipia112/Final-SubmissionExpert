package com.example.core.core.di

import android.content.Context
import androidx.room.Room
import com.example.core.core.data.source.local.room.AgentsDao
import com.example.core.core.data.source.local.room.AgentsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AgentsDatabase {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("admin#1234".toCharArray())
        val factory = SupportFactory(passphrase)

        return Room.databaseBuilder(
            context,
            AgentsDatabase::class.java, "agents_database.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

    @Provides
    fun provideAgentDao(database: AgentsDatabase): AgentsDao = database.agentsDao()
}
