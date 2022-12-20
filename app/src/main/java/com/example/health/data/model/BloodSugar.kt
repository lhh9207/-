package com.ssionii.bloodNote.data.model


data class BloodSugarTrendsResponse(
    val status : Int,
    val message : String,
    val data : ArrayList<BloodSugar>
)

data class BloodSugarCompareResponse(
    val status : Int,
    val message : String,
    val data : ArrayList<Int>
)

data class BloodSugar(
    val month : Int,
    val date : Int,
    val time : Float, // 0f 아침, 0.25f 점심, 0.5f 저녁, 0.75f 밤
    val getMeal : Int,
    val value : Int
)