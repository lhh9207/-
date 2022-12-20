package com.ssionii.bloodNote.data.repository

import com.google.gson.JsonObject
import com.ssionii.bloodNote.data.model.BloodPressureTrendsResponse
import com.ssionii.bloodNote.data.model.BloodSugarTrendsResponse
import com.ssionii.bloodNote.data.model.NullDataResponse
import io.reactivex.Single

interface BloodPressureRepository {
    fun postBloodPressure(body : JsonObject) : Single<NullDataResponse>
    fun getBloodPressureTrends() : Single<BloodPressureTrendsResponse>
}