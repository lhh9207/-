package com.ssionii.bloodNote.ui.state.analysis

import android.os.Bundle
import androidx.lifecycle.Observer
import com.ssionii.bloodNote.R
import com.ssionii.bloodNote.base.BaseActivity
import com.ssionii.bloodNote.databinding.ActivityAnalysisBinding
import com.ssionii.bloodNote.ui.main.EmptyViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AnalysisActivity : BaseActivity<ActivityAnalysisBinding, AnalysisViewModel>(){

    override val layoutResID: Int
        get() = R.layout.activity_analysis

    override val viewModel: AnalysisViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        viewDataBinding.vm = viewModel

        viewDataBinding.actAnalysisToolbar.toolbarBackTvTitle.text = "현재 내 상태 보기"

        setButton()
        setResultText()
    }

    fun setResultText(){

        var tempText = ""

        viewModel.analysisList.observe(this, Observer {
            if(it[0] < 50){
                tempText = "현재 혈당 관리를 잘하고 있어요!"

                if(it[2] < 50){
                    tempText += " 앞으로도 쭉 혈당 관리를 잘한다면 당뇨에 걸릴 확률은 적을거에요."
                } else {
                    tempText += " 하지만 앞으로 5년 뒤에는 당뇨에 걸릴 확률이 꽤 높으니 철저한 관리가 필요해요."
                }
            } else {
                tempText = "현재 당뇨일 확률이 꽤 높아요. 철저한 관리가 꼭 필요해요!"
            }

            tempText + "\n"

            if(it[1] < 50){
                tempText += " 현재 혈압 관리를 잘하고 있어요!"

                if(it[3] < 50){
                    tempText += " 앞으로도 쭉 혈압 관리를 잘한다면 고혈압에 걸릴 확률은 적을거에요."
                } else {
                    tempText += " 하지만 앞으로 5년 뒤에는 고혈압에 걸릴 확률이 꽤 높으니 철저한 관리가 필요해요."
                }
            } else {
                tempText += " 현재 고혈압일 확률이 꽤 높아요. 철저한 관리가 꼭 필요해요!"
            }

            viewDataBinding.actAnalysisTvResult.text = tempText

        })



    }

    fun setButton(){

        viewDataBinding.actAnalysisToolbar.toolbarBackIvBack.setOnClickListener {
            finish()
        }




    }


}