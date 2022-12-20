package com.ssionii.bloodNote.data

import com.google.gson.JsonObject
import com.ssionii.bloodNote.data.model.*
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface NetworkService {

    // 혈당 추가
    @POST("/bloodSugar")
    fun postBloodSugar(
        @Header("Content-Type") content_type: String,
        @Header("userIndex") userIndex : Int,
        @Body() body: JsonObject
    ): Single<NullDataResponse>

    // 혈당 추세 조회
    @GET("/bloodSugar/trends")
    fun getBloodSugarTrends(
        @Header("Content-Type") content_type: String,
        @Header("userIndex") userIndex : Int
    ): Single<BloodSugarTrendsResponse>

    // 혈당 추세 조회
    @GET("/bloodSugar/compare")
    fun getBloodSugarCompare(
        @Header("Content-Type") content_type: String,
        @Header("userIndex") userIndex : Int
    ): Single<BloodSugarCompareResponse>


    // 혈압 추가
    @POST("/bloodPressure")
    fun postBloodPressure(
        @Header("Content-Type") content_type: String,
        @Header("userIndex") userIndex : Int,
        @Body() body: JsonObject
    ): Single<NullDataResponse>

    // 혈압 추세 조회
    @GET("/bloodPressure/trends")
    fun getBloodPressureTrends(
        @Header("Content-Type") content_type: String,
        @Header("userIndex") userIndex : Int
    ): Single<BloodPressureTrendsResponse>

    // 분석 결과 조회
    @GET("/analysis")
    fun getAnalysis(
        @Header("Content-Type") content_type: String,
        @Header("userIndex") userIndex : Int
    ): Single<AnalysisResponse>

    // 회원가입
    @POST("/signup")
    fun signup(
        @Header("Content-Type") content_type: String,
        @Body() body: JsonObject
    ): Single<NullDataResponse>

    // 로그인
    @POST("/login")
    fun login(
        @Header("Content-Type") content_type: String,
        @Body() body: JsonObject
    ): Single<LoginResponse>


    companion object {
        fun create(): NetworkService {
            return Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NetworkService::class.java)
        }
    }

}