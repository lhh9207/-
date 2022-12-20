package com.ssionii.bloodNote.ui.state

import android.content.Intent
import android.os.Bundle
import com.ssionii.bloodNote.R
import com.ssionii.bloodNote.base.BaseActivity
import com.ssionii.bloodNote.databinding.ActivityStateBinding
import com.ssionii.bloodNote.ui.main.EmptyViewModel
import com.ssionii.bloodNote.ui.state.analysis.AnalysisActivity
import com.ssionii.bloodNote.ui.state.report.BloodPressureReportActivity
import com.ssionii.bloodNote.ui.state.report.BloodType
import com.ssionii.bloodNote.ui.state.report.BloodSugarReportActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class StateActivity : BaseActivity<ActivityStateBinding, EmptyViewModel>(){

    override val layoutResID: Int
        get() = R.layout.activity_state

    override val viewModel: EmptyViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.actMyStateToolbar.toolbarBackTvTitle.text = "현재 내 상태 보기"

        setButton()
    }

    private fun setButton(){
        viewDataBinding.actStareCvBloodSugarReport.setOnClickListener {
            val intent = Intent(this, BloodSugarReportActivity::class.java)
            intent.putExtra("type", BloodType.Sugar)

            startActivity(intent)
        }

        viewDataBinding.actStareCvBloodPressureReport.setOnClickListener {
            val intent = Intent(this, BloodPressureReportActivity::class.java)
            intent.putExtra("type", BloodType.Pressure)

            startActivity(intent)
        }

        viewDataBinding.actMyStateToolbar.toolbarBackIvBack.setOnClickListener {
            finish()
        }

        viewDataBinding.actStareCvAnalysis.setOnClickListener {
            val intent = Intent(this, AnalysisActivity::class.java)
            startActivity(intent)
        }
    }
}