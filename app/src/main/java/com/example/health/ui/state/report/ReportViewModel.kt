package com.ssionii.bloodNote.ui.state.report

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ssionii.bloodNote.base.BaseViewModel
import com.ssionii.bloodNote.data.model.BloodPressure
import com.ssionii.bloodNote.data.model.BloodSugar
import com.ssionii.bloodNote.data.repository.BloodPressureRepository
import com.ssionii.bloodNote.data.repository.BloodSugarRepository
import com.ssionii.bloodNote.util.scheduler.SchedulerProvider

class ReportViewModel(
    private val bloodSugarRepository: BloodSugarRepository,
    private val bloodPressureRepository: BloodPressureRepository,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel(){

    var isWeak = MutableLiveData<Boolean>()

    var averageBloodSugarList = MutableLiveData<ArrayList<Int>>()
    var chartBloodSugarList = MutableLiveData<ArrayList<BloodSugar>>()
    var chartBloodPressureList = MutableLiveData<ArrayList<BloodPressure>>()

    var weekAverageBloodSugarList = arrayListOf<Int>()
    var weekChartBloodSugarList = arrayListOf<BloodSugar>()
    var weekChartBloodPressureList = arrayListOf<BloodPressure>()

    val bloodSugarDummy1 = arrayListOf<BloodSugar>()
    val bloodSugarDummy2 = arrayListOf<Int>()



    init {
        isWeak.value = true
        getBloodSugarCompare()
        getBloodSugarTrends()
        getBloodPressureTrends()
    }

    fun selectWeek(){
        averageBloodSugarList.value = weekAverageBloodSugarList
        chartBloodSugarList.value = weekChartBloodSugarList
    }

    fun selectMonth(){

        chartBloodSugarList.value = bloodSugarDummy1
    }

    fun getBloodSugarTrends(){
        addDisposable(bloodSugarRepository.getBloodSugarTrends()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.mainThread())
            .doOnError {
                it.printStackTrace()
            }
            .subscribe({
                Log.e("혈당 추세 조회 status", it.status.toString())
                chartBloodSugarList.value = it.data
                weekChartBloodSugarList = it.data

            }, {
                it.printStackTrace()
            })
        )
    }

    fun getBloodPressureTrends(){
        addDisposable(bloodPressureRepository.getBloodPressureTrends()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.mainThread())
            .doOnError {
                it.printStackTrace()
            }
            .subscribe({
                Log.e("혈압 추세 조회 status", it.status.toString())
                chartBloodPressureList.value = it.data
                weekChartBloodPressureList = it.data

            }, {
                it.printStackTrace()
            })
        )
    }

    fun getBloodSugarCompare(){
        addDisposable(bloodSugarRepository.getBloodSugarCompare()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.mainThread())
            .doOnError {
                it.printStackTrace()
            }
            .subscribe({
                Log.e("혈당 비교 조회 status", it.status.toString())
                averageBloodSugarList.value = it.data
                weekAverageBloodSugarList = it.data

            }, {
                it.printStackTrace()
            })
        )
    }


}