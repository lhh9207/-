package com.ssionii.bloodNote.ui.write

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.ssionii.bloodNote.R
import com.ssionii.bloodNote.base.BaseActivity
import com.ssionii.bloodNote.databinding.ActivityWriteBloodPressureBinding
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class WriteBloodPressureActivity : BaseActivity<ActivityWriteBloodPressureBinding, WriteBloodViewModel>(){
    override val layoutResID: Int
        get() = R.layout.activity_write_blood_pressure

    override val viewModel: WriteBloodViewModel by viewModel()

    private var max = ""
    private var min = ""
    private var pulse = ""

    private var isDone = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.actWriteBloodPressureToolbar.toolbarBackTvTitle.text = "혈압 입력하기"

        setButton()
        setEditChange()

    }

    private fun onDataCheck(){

        getEditValue()

        if(max != "" && min != "" && pulse != ""){
            viewDataBinding.actWriteBloodPressureClDone.run{
                setBackgroundColor(resources.getColor(R.color.mainColor))
                isDone = true
            }
        } else {
            viewDataBinding.actWriteBloodPressureClDone.run{
                setBackgroundColor(resources.getColor(R.color.gray2))

                isDone = false
            }
        }
    }

    private fun EditText.onChange(cb: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                cb(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun getEditValue(){
        max = viewDataBinding.actWriteBloodPressureEtMax.text.toString()
        min = viewDataBinding.actWriteBloodPressureEtMin.text.toString()
        pulse = viewDataBinding.actWriteBloodPressureEtPulse.text.toString()
}

    private fun setEditChange(){
        viewDataBinding.actWriteBloodPressureEtMax.onChange { onDataCheck() }
        viewDataBinding.actWriteBloodPressureEtMin.onChange { onDataCheck() }
        viewDataBinding.actWriteBloodPressureEtPulse.onChange { onDataCheck() }
    }


    private fun post(){
        val currentTime = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("dd", Locale.getDefault())
        val monthFormat = SimpleDateFormat("MM", Locale.getDefault())

        val month: String = monthFormat.format(currentTime)
        val date: String = dateFormat.format(currentTime)

        val jsonObject = JSONObject()

        jsonObject.put("month", month)
        jsonObject.put("date", date)
        jsonObject.put("max", max.toInt())
        jsonObject.put("min", min.toInt())
        jsonObject.put("pulse", pulse.toInt())
        var gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

        viewModel.postBloodPressureData(gsonObject)

    }

    private fun setButton(){
        viewDataBinding.actWriteBloodPressureToolbar.toolbarBackIvBack.setOnClickListener {
            finish()
        }

        viewDataBinding.actWriteBloodPressureClDone.setOnClickListener {
            if(isDone){
                post()
                Toast.makeText(this, "혈당 기록이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "입력되지 않은 값이 있습니다.", Toast.LENGTH_SHORT).show()
            }

        }

    }
}