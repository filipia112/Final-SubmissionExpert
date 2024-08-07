package com.example.core.core.domain.model
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize
data class Agent(
    val uuid: String,
    val display: String,
    val displayName: String,
    val description: String,
    val developerName: String,
    val fullPortrait: String,
    val killfeedPortrait: String,
    val background: String,
    var isFavorite: Boolean = false,
    val backgroundGradientColors: String?,
) : Parcelable