package com.ssionii.bloodNote.data.repository

import com.google.gson.JsonObject
import com.ssionii.bloodNote.data.model.BloodSugarCompareResponse
import com.ssionii.bloodNote.data.model.BloodSugarTrendsResponse
import com.ssionii.bloodNote.data.model.NullDataResponse
import io.reactivex.Single

interface BloodSugarRepository {
    fun postBloodSugar(body : JsonObject) : Single<NullDataResponse>
    fun getBloodSugarTrends() : Single<BloodSugarTrendsResponse>
    fun getBloodSugarCompare() : Single<BloodSugarCompareResponse>
}