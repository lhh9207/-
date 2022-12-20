package com.ssionii.bloodNote.ui.state.report

import android.graphics.Color
import android.graphics.DashPathEffect
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend.LegendForm
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.LimitLine.LimitLabelPosition
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.ssionii.bloodNote.R
import com.ssionii.bloodNote.base.BaseActivity
import com.ssionii.bloodNote.databinding.ActivityBloodSugarReportBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class BloodSugarReportActivity : BaseActivity<ActivityBloodSugarReportBinding, ReportViewModel>(){

    override val layoutResID: Int
        get() = R.layout.activity_blood_sugar_report

    override val viewModel: ReportViewModel by viewModel()

    var type = BloodType.Sugar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.vm = viewModel

        type = intent.getIntExtra("type", BloodType.Sugar)

        when(type){
            BloodType.Sugar -> viewDataBinding.actReportToolbar.toolbarBackTvTitle.text = "혈당값 추세"
            BloodType.Pressure -> viewDataBinding.actReportToolbar.toolbarBackTvTitle.text = "혈압값 추세"
        }


        setButton()
        setChart()
    }

    private fun setChart(){

        viewDataBinding.actReportChart.run{
            setBackgroundColor(resources.getColor(R.color.white))
            setDrawGridBackground(false)

            val description = Description()
            description.text = ""
            setDescription(description)

            val xAxis = xAxis
            xAxis.enableGridDashedLine(10f, 10f, 0f)

            val yAxis = axisLeft
            axisRight.isEnabled = false
            yAxis.enableGridDashedLine(10f, 10f, 0f)

            yAxis.axisMaximum = 300f
            yAxis.axisMinimum = 40f

            val ll1 = LimitLine(180f, "고혈당")
            ll1.lineWidth = 4f
            ll1.enableDashedLine(10f, 10f, 0f)
            ll1.labelPosition = LimitLabelPosition.RIGHT_TOP
            ll1.textSize = 13f
            ll1.typeface = resources.getFont(R.font.noto_sans_kr_medium)
            ll1.textColor = resources.getColor(R.color.mainColor)

            val ll2 = LimitLine(90f, "저혈당")
            ll2.lineWidth = 4f
            ll2.enableDashedLine(10f, 10f, 0f)
            ll2.labelPosition = LimitLabelPosition.RIGHT_BOTTOM
            ll2.textSize = 13f
            ll2.typeface = resources.getFont(R.font.noto_sans_kr_medium)
            ll2.textColor = resources.getColor(R.color.mainColor)

            yAxis.setDrawLimitLinesBehindData(true)
            xAxis.setDrawLimitLinesBehindData(true)

            // add limit lines
            yAxis.addLimitLine(ll1)
            yAxis.addLimitLine(ll2)

            animateX(1500)
            val l = legend

            setPinchZoom(false)

            l.form = LegendForm.LINE

        }

        viewModel.chartBloodSugarList.observe(this, Observer {
            setData()
        })
    }

    private fun setChartDesign(entries : ArrayList<Entry>) : LineDataSet {

        val set1 = LineDataSet(entries, "혈당")
        set1.setDrawIcons(false)

        set1.enableDashedLine(10f, 0f, 0f)

        set1.color = Color.BLACK
        set1.setCircleColor(Color.BLACK)

        set1.lineWidth = 1f
        set1.circleRadius = 3f

        // draw points as solid circles
        set1.setDrawCircleHole(false)

        // customize legend entry
        set1.formLineWidth = 1f
        set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
        set1.formSize = 15f

        // text size of values
        set1.valueTextSize = 9f

        // draw selection line as dashed
        set1.enableDashedHighlightLine(10f, 5f, 0f)

        // set the filled area
        set1.setDrawFilled(true)

        return set1
    }

    private fun setData() {

        val chart = viewDataBinding.actReportChart

        val entries = arrayListOf<Entry>()

        viewModel.chartBloodSugarList.run{
            for(i in 0 until this.value!!.size){
                entries.add(Entry(this.value!![i].date.toFloat() + this.value!![i].time,
                    this.value!![i].value.toFloat()))
            }
        }

        if (chart.data != null && chart.data.dataSetCount > 0) {
            chart.removeAllViews()
        }

        val set1 = setChartDesign(entries)

        set1.fillFormatter = IFillFormatter { dataSet, dataProvider -> chart.getAxisLeft().getAxisMinimum() }
        set1.fillColor = Color.parseColor("#FDE4E7")

        val dataSets: ArrayList<ILineDataSet> = ArrayList()
        dataSets.add(set1) // add the data sets

        val data = LineData(dataSets)

        chart.data = data
    }

    private fun setButton(){
        viewDataBinding.actReportToolbar.toolbarBackIvBack.setOnClickListener {
            finish()
        }

        viewDataBinding.actReportClWeek.setOnClickListener {
            viewModel.isWeak.value = true
            viewModel.selectWeek()

            viewDataBinding.actReportRlCharEmpty.visibility = GONE
            viewDataBinding.actReportChart.visibility = VISIBLE
        }

        viewDataBinding.actReportClMonth.setOnClickListener {
            viewModel.isWeak.value = false
            viewModel.selectMonth()

            viewDataBinding.actReportRlCharEmpty.visibility = VISIBLE
            viewDataBinding.actReportChart.visibility = GONE
        }

    }
}

object BloodType{
    const val Sugar = 0
    const val Pressure = 1
}