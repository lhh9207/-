package com.ssionii.bloodNote.ui.main

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.ssionii.bloodNote.BloodNoteApplication
import com.ssionii.bloodNote.R
import com.ssionii.bloodNote.base.BaseViewModel
import com.ssionii.bloodNote.data.model.MainBanner

class MainViewModel() : BaseViewModel(){

    private val context = BloodNoteApplication.getGlobalApplicationContext()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    val mainBannerList = arrayListOf(
        MainBanner(1, ContextCompat.getDrawable(context, R.drawable.img_main_1), "https://1boon.kakao.com/dailylife/180523_2", "당뇨에 좋은 음식으로 당뇨를 이겨내자!"),
        MainBanner(2, ContextCompat.getDrawable(context, R.drawable.img_main_2), "https://m.blog.naver.com/PostView.nhn?blogId=kfdazzang&logNo=221539206190&proxyReferer=https:%2F%2Fwww.google.com%2F", "고혈압 예방을 위한 생활 습관 6가지!")

    )

}