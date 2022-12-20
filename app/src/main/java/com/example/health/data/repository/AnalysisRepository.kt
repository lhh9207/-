package com.ssionii.bloodNote.data.repository

import com.ssionii.bloodNote.data.model.AnalysisResponse
import io.reactivex.Single

interface AnalysisRepository {

    fun getAnalysis() : Single<AnalysisResponse>
}