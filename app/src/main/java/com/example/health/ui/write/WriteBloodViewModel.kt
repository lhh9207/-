package com.ssionii.bloodNote.ui.write

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.ssionii.bloodNote.base.BaseViewModel
import com.ssionii.bloodNote.data.repository.BloodPressureRepository
import com.ssionii.bloodNote.data.repository.BloodSugarRepository
import com.ssionii.bloodNote.util.scheduler.SchedulerProvider

class WriteBloodViewModel(
    private val bloodSugarRepository: BloodSugarRepository,
    private val bloodPressureRepository: BloodPressureRepository,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel(){

    var getMeal = MutableLiveData<Int>()

    init {
        getMeal.value = -1
    }

    fun postBloodSugarData(jsonObject : JsonObject){

        val body = JsonParser().parse(jsonObject.toString()) as JsonObject

        addDisposable(bloodSugarRepository.postBloodSugar(body)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.mainThread())
            .doOnError {
                it.printStackTrace()
            }
            .subscribe({
                Log.e("혈당 등록 status", it.status.toString())

            }, {
                it.printStackTrace()
            })
        )
    }

    fun postBloodPressureData(jsonObject: JsonObject){

        val body = JsonParser().parse(jsonObject.toString()) as JsonObject

        addDisposable(bloodPressureRepository.postBloodPressure(body)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.mainThread())
            .doOnError {
                it.printStackTrace()
            }
            .subscribe({
                Log.e("혈압 등록 status", it.status.toString())

            }, {
                it.printStackTrace()
            })
        )
    }

}