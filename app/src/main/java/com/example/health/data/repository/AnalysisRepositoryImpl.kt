package com.ssionii.bloodNote.data.repository

import com.google.gson.JsonObject
import com.ssionii.bloodNote.data.NetworkService
import com.ssionii.bloodNote.data.model.AnalysisResponse
import com.ssionii.bloodNote.data.model.BloodPressureTrendsResponse
import com.ssionii.bloodNote.data.model.NullDataResponse
import io.reactivex.Single

class AnalysisRepositoryImpl(val api: NetworkService) : AnalysisRepository {

    override fun getAnalysis(): Single<AnalysisResponse> =
        api.getAnalysis("application/json", 1)
            .map { it }
}