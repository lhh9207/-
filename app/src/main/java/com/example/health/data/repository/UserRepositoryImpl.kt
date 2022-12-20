package com.ssionii.bloodNote.data.repository

import com.google.gson.JsonObject
import com.ssionii.bloodNote.data.NetworkService
import com.ssionii.bloodNote.data.model.BloodSugarCompareResponse
import com.ssionii.bloodNote.data.model.BloodSugarTrendsResponse
import com.ssionii.bloodNote.data.model.LoginResponse
import com.ssionii.bloodNote.data.model.NullDataResponse
import io.reactivex.Single

class UserRepositoryImpl(val api: NetworkService) : UserRepository {
    override fun signup(body: JsonObject): Single<NullDataResponse> =
        api.signup("application/json",  body)
            .map { it }

    override fun login(body: JsonObject): Single<LoginResponse> =
        api.login("application/json",  body)
            .map { it }
}