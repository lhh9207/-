package com.ssionii.bloodNote.data.model

data class AnalysisResponse(
    val status : Int,
    val message : String,
    val data : ArrayList<Int>
)
