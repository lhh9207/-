package com.ssionii.bloodNote.data.repository

import com.google.gson.JsonObject
import com.ssionii.bloodNote.data.NetworkService
import com.ssionii.bloodNote.data.model.BloodSugarCompareResponse
import com.ssionii.bloodNote.data.model.BloodSugarTrendsResponse
import com.ssionii.bloodNote.data.model.NullDataResponse
import io.reactivex.Single

class BloodSugarRepositoryImpl(val api: NetworkService) : BloodSugarRepository {
    override fun postBloodSugar(body: JsonObject): Single<NullDataResponse> =
        api.postBloodSugar("application/json", 1, body)
            .map { it }

    override fun getBloodSugarTrends(): Single<BloodSugarTrendsResponse> =
        api.getBloodSugarTrends("application/json", 1)
            .map { it }

    override fun getBloodSugarCompare(): Single<BloodSugarCompareResponse> =
        api.getBloodSugarCompare("application/json", 1)
            .map { it }
}