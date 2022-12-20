package com.ssionii.bloodNote.ui.login

import android.content.Intent
import android.os.Bundle
import com.ssionii.bloodNote.R
import com.ssionii.bloodNote.base.BaseActivity
import com.ssionii.bloodNote.databinding.ActivityLoginBinding
import com.ssionii.bloodNote.ui.main.EmptyViewModel
import com.ssionii.bloodNote.ui.main.MainActivity
import com.ssionii.bloodNote.ui.signup.SignupActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity<ActivityLoginBinding, EmptyViewModel>(){

    override val layoutResID: Int
        get() = R.layout.activity_login

    override val viewModel: EmptyViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setButton()
    }

    fun setButton(){

        viewDataBinding.actSignupClDone.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            finish()
            startActivity(intent)

        }

        viewDataBinding.actLoginTvSignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

}