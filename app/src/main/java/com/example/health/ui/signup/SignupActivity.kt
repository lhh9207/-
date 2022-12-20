package com.ssionii.bloodNote.ui.signup

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.ssionii.bloodNote.R
import com.ssionii.bloodNote.base.BaseActivity
import com.ssionii.bloodNote.databinding.ActivitySignupBinding
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignupActivity : BaseActivity<ActivitySignupBinding, UserViewModel>(){

    override val layoutResID: Int
        get() = R.layout.activity_signup

    override val viewModel: UserViewModel by viewModel()


    var sex = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setButton()
    }

    fun setButton(){

        viewDataBinding.actSignupClDone.setOnClickListener {
            if(sex == -1){
                Toast.makeText(this, "성별을 선택해주세요.", Toast.LENGTH_SHORT).show()
            } else if(viewDataBinding.actSingupPw.text != viewDataBinding.actSingupPwCheck.text){
                Toast.makeText(this, "비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show()
            } else {
                val jsonObject = JSONObject()

                jsonObject.put("id", viewDataBinding.actSingupId.text)
                jsonObject.put("password", viewDataBinding.actSingupPw.text)
                jsonObject.put("age", viewDataBinding.actSingupAge.text)
                jsonObject.put("sex",sex)

                var gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

//                viewModel.signup(gsonObject)

                finish()

            }
        }

        viewDataBinding.actSingupMan.setOnClickListener {
            sex = 1
            viewDataBinding.actSingupMan.setBackgroundDrawable(resources.getDrawable(R.drawable.bg_4_fde4ef_border_dddddd))
            viewDataBinding.actSignupWoman.setBackgroundDrawable(resources.getDrawable(R.drawable.border_4_dddddd))

            viewDataBinding.actSignupClDone.setBackgroundColor(resources.getColor(R.color.mainColor))
        }

        viewDataBinding.actSignupWoman.setOnClickListener {
            sex = 2
            viewDataBinding.actSignupWoman.setBackgroundDrawable(resources.getDrawable(R.drawable.bg_4_fde4ef_border_dddddd))
            viewDataBinding.actSingupMan.setBackgroundDrawable(resources.getDrawable(R.drawable.border_4_dddddd))

            viewDataBinding.actSignupClDone.setBackgroundColor(resources.getColor(R.color.mainColor))
        }
    }


}