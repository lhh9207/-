package com.ssionii.bloodNote.data.repository

import com.google.gson.JsonObject
import com.ssionii.bloodNote.data.NetworkService
import com.ssionii.bloodNote.data.model.BloodPressureTrendsResponse
import com.ssionii.bloodNote.data.model.BloodSugarTrendsResponse
import com.ssionii.bloodNote.data.model.NullDataResponse
import io.reactivex.Single

class BloodPressureRepositoryImpl(val api: NetworkService) : BloodPressureRepository {

    override fun postBloodPressure(body: JsonObject): Single<NullDataResponse> =
        api.postBloodPressure("application/json", 1, body)
            .map { it }

    override fun getBloodPressureTrends(): Single<BloodPressureTrendsResponse> =
        api.getBloodPressureTrends("application/json", 1)
            .map { it }


}