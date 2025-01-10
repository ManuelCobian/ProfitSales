package com.luvsoft.room.network.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FortnightsModel(
    val id: Int,
    val name: String
): Parcelable
