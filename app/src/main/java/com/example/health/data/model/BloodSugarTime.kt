package com.ssionii.bloodNote.data.model

import android.graphics.Color
import android.graphics.drawable.Drawable

data class BloodSugarTime(
    val id : Float,
    val text: String,
    val color : Int,
    var isSelected : Boolean
)

object BloodSugarTimeId {
    const val MORNING = 0f
    const val NOON =  0.25f
    const val AFTERNOON = 0.5f
    const val NIGHT = 0.75f
}