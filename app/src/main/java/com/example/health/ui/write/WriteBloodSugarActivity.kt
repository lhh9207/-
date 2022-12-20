package com.ssionii.bloodNote.ui.write

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.ssionii.bloodNote.R
import com.ssionii.bloodNote.base.BaseActivity
import com.ssionii.bloodNote.databinding.ActivityWriteBloodSugarBinding
import com.ssionii.bloodNote.ui.write.dialog.BloodSugarDialogFragment
import com.ssionii.bloodNote.util.view.NonScrollGridLayoutManager
import com.ssionii.bloodNote.util.view.SpaceDecoration
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class WriteBloodSugarActivity : BaseActivity<ActivityWriteBloodSugarBinding, WriteBloodViewModel>(){
    override val layoutResID: Int
        get() = R.layout.activity_write_blood_sugar

    override val viewModel: WriteBloodViewModel by viewModel()

    private var time = 1f

    private var isDone = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.vm = viewModel

        viewDataBinding.actWriteBloodSugarToolbar.toolbarBackTvTitle.text = "혈당 입력하기"

        setTimeRv()
        setButton()
        setEditChange()
    }

    private fun setTimeRv(){

        val rvAdapter = BloodSugarTimeRecyclerViewAdapter(this)
        rvAdapter.setOnBloodSugarTimeClickListener(object : BloodSugarTimeRecyclerViewAdapter.OnBloodSugarTimeClickListener{
            override fun onClick(id: Float) {
                time = id
                onDataCheck()
            }
        })

        val marginDp = 6
        val d = resources.displayMetrics.density
        val size = (marginDp * d).toInt()

        val deco = SpaceDecoration(size)

        viewDataBinding.actWriteBloodSugarRvTime.run{
            adapter = rvAdapter
            layoutManager = NonScrollGridLayoutManager(this@WriteBloodSugarActivity, 4)
            addItemDecoration(deco)

        }
    }

    private fun onDataCheck(){
        if(time != 1f && viewModel.getMeal.value != -1 && viewDataBinding.actWriteBloodSugarEt.text.toString() != ""){
            viewDataBinding.actWriteBloodSugarClDone.run{
                setBackgroundColor(resources.getColor(R.color.mainColor))
                isDone = true
            }
        } else {
            viewDataBinding.actWriteBloodSugarClDone.run{
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

    private fun setEditChange(){
        viewDataBinding.actWriteBloodSugarEt.onChange { onDataCheck() }
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
        jsonObject.put("time", time)
        jsonObject.put("getMeal", viewModel.getMeal.value)
        jsonObject.put("value", viewDataBinding.actWriteBloodSugarEt.text.toString().toInt())
        var gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

        viewModel.postBloodSugarData(gsonObject)

    }

    private fun setButton(){
        viewDataBinding.actWriteBloodSugarToolbar.toolbarBackIvBack.setOnClickListener {
            finish()
        }

        viewDataBinding.actWriteBloodSugarClDone.setOnClickListener {
            if(isDone){
                post()

                hideKeyboard(viewDataBinding.actWriteBloodSugarEt)

                val dialogFragment = BloodSugarDialogFragment()
                val args = Bundle()
                args.putFloat("time", time)
                args.putInt("value", viewDataBinding.actWriteBloodSugarEt.text.toString().toInt())
                args.putInt("getMeal", viewModel.getMeal.value!!)

                dialogFragment.arguments = args
                dialogFragment.setOnDialogDismissedListener(dialogDismissedListener)
                dialogFragment.show(supportFragmentManager, "frag_dialog_blood_sugar")
            } else {
                Toast.makeText(this, "입력되지 않은 값이 있습니다.", Toast.LENGTH_SHORT).show()
            }

        }

        viewDataBinding.actWriteBloodSugarRlBeforeMeal.setOnClickListener {
            viewModel.getMeal.value = 0
            onDataCheck()
        }

        viewDataBinding.actWriteBloodSugarRlAfterMeal.setOnClickListener {
            viewModel.getMeal.value = 1
            onDataCheck()
        }
    }

    private val dialogDismissedListener = object : BloodSugarDialogFragment.OnDialogDismissedListener{
        override fun onDialogDismissed() {
            finish()
        }
    }

    fun hideKeyboard(et: EditText){

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0)

    }

}