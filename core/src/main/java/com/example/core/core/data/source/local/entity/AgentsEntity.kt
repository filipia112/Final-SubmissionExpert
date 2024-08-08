package com.example.core.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "agents")
data class AgentsEntity(
    @PrimaryKey
    @ColumnInfo(name = "uuid")
    var uuid: String,

    @ColumnInfo(name = "displayName")
    var displayName: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "developerName")
    var developerName: String,

    @ColumnInfo(name = "fullPortrait")
    var fullPortrait: String,

    @ColumnInfo(name = "killfeedPortrait")
    var killfeedPortrait: String,

    @ColumnInfo(name = "background")
    var background: String,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false,

    @ColumnInfo(name = "backgroundGradientColors")
    val backgroundGradientColors: String?

)
