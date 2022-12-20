package com.ssionii.bloodNote.ui.state.report

import android.graphics.Color
import android.graphics.DashPathEffect
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.ssionii.bloodNote.R
import com.ssionii.bloodNote.base.BaseActivity
import com.ssionii.bloodNote.databinding.ActivityBloodPressureReportBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class BloodPressureReportActivity : BaseActivity<ActivityBloodPressureReportBinding, ReportViewModel>(){

    override val layoutResID: Int
        get() = R.layout.activity_blood_pressure_report

    override val viewModel: ReportViewModel by viewModel()

    var type = BloodType.Sugar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.vm = viewModel

        type = intent.getIntExtra("type", BloodType.Sugar)

        when(type){
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
            xAxis.setDrawGridLines(false)
//            xAxis.enableGridDashedLine(10f, 10f, 0f)

            val yAxis = axisLeft
            axisRight.isEnabled = false
            yAxis.setDrawGridLines(false)
//            yAxis.enableGridDashedLine(10f, 10f, 0f)

            yAxis.axisMaximum = 160f
            yAxis.axisMinimum = 40f

            val ll1 = LimitLine(120f, "고혈압")
            ll1.lineWidth = 4f
            ll1.enableDashedLine(10f, 10f, 0f)
            ll1.labelPosition = LimitLine.LimitLabelPosition.RIGHT_TOP
            ll1.textSize = 13f
            ll1.typeface = resources.getFont(R.font.noto_sans_kr_medium)
            ll1.textColor = resources.getColor(R.color.mainColor)
            ll1.lineColor = Color.parseColor("#fde4e7")

            val ll2 = LimitLine(80f, "저혈압")
            ll2.lineWidth = 4f
            ll2.enableDashedLine(10f, 10f, 0f)
            ll2.labelPosition = LimitLine.LimitLabelPosition.RIGHT_BOTTOM
            ll2.textSize = 13f
            ll2.typeface = resources.getFont(R.font.noto_sans_kr_medium)
            ll2.textColor = resources.getColor(R.color.afternoon)
            ll2.lineColor = Color.parseColor("#dfecff")

            yAxis.setDrawLimitLinesBehindData(false)
            xAxis.setDrawLimitLinesBehindData(false)

            // add limit lines
            yAxis.addLimitLine(ll1)
            yAxis.addLimitLine(ll2)

            animateX(1500)
            val l = legend

            setPinchZoom(false)

            l.form = Legend.LegendForm.LINE

        }

        viewModel.chartBloodPressureList.observe(this, Observer {
            setData()
        })
    }

    private fun setData() {

        val chart = viewDataBinding.actReportChart

        val maxEntries = arrayListOf<Entry>()
        val minEntries = arrayListOf<Entry>()
        val pulseEntries = arrayListOf<Entry>()

        val currentTime = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("dd", Locale.getDefault())

        val today = dateFormat.format(currentTime).toInt()
        val beforeOneWeek = today - 6


        viewModel.chartBloodPressureList.run{

            for(day in beforeOneWeek until today+1) {
                for(i in 0 until this.value!!.size)
                    if(day == this.value!![i].date ){
                        maxEntries.add(Entry(this.value!![i].date.toFloat(),
                            this.value!![i].max.toFloat()))
                        minEntries.add(Entry(this.value!![i].date.toFloat(),
                            this.value!![i].min.toFloat()))
                        pulseEntries.add(Entry(this.value!![i].date.toFloat(),
                            this.value!![i].pulse.toFloat()))
                    } else {
                        maxEntries.add(Entry(day.toFloat(), -1f))
                        minEntries.add(Entry(day.toFloat(), -1f))
                        pulseEntries.add(Entry(day.toFloat(), -1f))
                    }
            }

        }

        if (chart.data != null && chart.data.dataSetCount > 0) {
            chart.removeAllViews()
        }

        val set1 = setMaxDesign(maxEntries)
        val set2 = setMinDesign(minEntries)
        val set3 = setPulseDesign(pulseEntries)

        set1.fillFormatter = IFillFormatter { dataSet, dataProvider -> chart.getAxisLeft().getAxisMinimum() }
        set1.fillColor =  resources.getColor(R.color.mainColor)
        set2.fillFormatter = IFillFormatter { dataSet, dataProvider -> chart.getAxisLeft().getAxisMinimum() }
        set2.fillColor = resources.getColor(R.color.afternoon)
        set3.fillFormatter = IFillFormatter { dataSet, dataProvider -> chart.getAxisLeft().getAxisMinimum() }
        set3.fillColor = resources.getColor(R.color.noon)

        val dataSets: ArrayList<ILineDataSet> = ArrayList()
        dataSets.add(set1) // add the data sets
        dataSets.add(set2) // add the data sets
        dataSets.add(set3) // add the data sets


        val data = LineData(dataSets)

        chart.data = data
    }

    private fun setMaxDesign(entries : ArrayList<Entry>) : LineDataSet {

        val set1 = LineDataSet(entries, "")
        set1.setDrawIcons(false)

        set1.enableDashedLine(10f, 0f, 0f)

        set1.color = resources.getColor(R.color.white)
        set1.setCircleColor(resources.getColor(R.color.mainColor))

        set1.lineWidth = 0f
        set1.circleRadius = 3f

        // draw points as solid circles
        set1.setDrawCircleHole(false)

        // customize legend entry
        set1.formLineWidth = 0f
//        set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
        set1.formSize = 0f

        // text size of values
        set1.valueTextSize = 0f

        // draw selection line as dashed
        set1.enableDashedHighlightLine(10f, 5f, 0f)

        // set the filled area
        set1.setDrawFilled(false)

        return set1
    }

    private fun setMinDesign(entries : ArrayList<Entry>) : LineDataSet {

        val set1 = LineDataSet(entries, "")
        set1.setDrawIcons(false)

        set1.enableDashedLine(10f, 0f, 0f)

        set1.color = resources.getColor(R.color.white)
        set1.setCircleColor(resources.getColor(R.color.afternoon))

        set1.lineWidth = 1f
        set1.circleRadius = 3f

        // draw points as solid circles
        set1.setDrawCircleHole(false)

        // customize legend entry
        set1.formLineWidth = 1f
        set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
        set1.formSize = 15f

        // text size of values
        set1.valueTextSize = 0f

        // draw selection line as dashed
        set1.enableDashedHighlightLine(10f, 5f, 0f)

        // set the filled area
        set1.setDrawFilled(false)

        return set1
    }

    private fun setPulseDesign(entries : ArrayList<Entry>) : LineDataSet {

        val set1 = LineDataSet(entries, "")
        set1.setDrawIcons(false)

        set1.enableDashedLine(10f, 0f, 0f)

        set1.color = resources.getColor(R.color.white)
        set1.setCircleColor(resources.getColor(R.color.noon))

        set1.lineWidth = 1f
        set1.circleRadius = 3f

        // draw points as solid circles
        set1.setDrawCircleHole(false)

        // customize legend entry
        set1.formLineWidth = 1f
        set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
        set1.formSize = 15f

        // text size of values
        set1.valueTextSize = 0f

        // draw selection line as dashed
        set1.enableDashedHighlightLine(10f, 5f, 0f)

        // set the filled area
        set1.setDrawFilled(false)

        return set1
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
