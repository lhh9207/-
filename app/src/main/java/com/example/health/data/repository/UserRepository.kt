package com.ssionii.bloodNote.data.repository

import com.google.gson.JsonObject
import com.ssionii.bloodNote.data.model.LoginResponse
import com.ssionii.bloodNote.data.model.NullDataResponse
import io.reactivex.Single

interface UserRepository {
    fun signup(body : JsonObject) : Single<NullDataResponse>
    fun login(body : JsonObject) : Single<LoginResponse>
}