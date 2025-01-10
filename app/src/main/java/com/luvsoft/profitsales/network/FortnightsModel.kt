package com.luvsoft.profitsales.network

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FortnightsModel(
    val id: Int,
    val name: String
): Parcelable
