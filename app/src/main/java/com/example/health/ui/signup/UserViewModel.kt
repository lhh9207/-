package com.ssionii.bloodNote.ui.signup

import android.util.Log
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.ssionii.bloodNote.base.BaseViewModel
import com.ssionii.bloodNote.data.repository.UserRepository
import com.ssionii.bloodNote.util.scheduler.SchedulerProvider

class UserViewModel(
    private val repository : UserRepository,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel(){

    fun signup(jsonObject : JsonObject){

        val body = JsonParser().parse(jsonObject.toString()) as JsonObject

        addDisposable(repository.signup(body)
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
}