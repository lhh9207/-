package com.ssionii.bloodNote.data.model

data class BloodPressureTrendsResponse(
    val status : Int,
    val message : String,
    val data : ArrayList<BloodPressure>
)


data class BloodPressure(
    val month : Int,
    val date : Int,
    val max : Int,
    val min : Int,
    val pulse : Int
)