package com.ssionii.bloodNote.ui.state.analysis

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ssionii.bloodNote.base.BaseViewModel
import com.ssionii.bloodNote.data.repository.AnalysisRepository
import com.ssionii.bloodNote.util.scheduler.SchedulerProvider

class AnalysisViewModel(
    private val analysisRepository: AnalysisRepository,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel(){


    var analysisList = MutableLiveData<ArrayList<Int>>()

    init {
        getAnalysis()
    }

    fun getAnalysis(){
        addDisposable(analysisRepository.getAnalysis()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.mainThread())
            .doOnError {
                it.printStackTrace()
            }
            .subscribe({
                Log.e("분석 조회 status", it.status.toString())
                analysisList.value = it.data

            }, {
                it.printStackTrace()
            })
        )
    }


}