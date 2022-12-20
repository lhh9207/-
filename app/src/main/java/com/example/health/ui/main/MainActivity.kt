package com.ssionii.bloodNote.ui.main

import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.Display
import androidx.viewpager.widget.ViewPager
import com.ssionii.bloodNote.R
import com.ssionii.bloodNote.base.BaseActivity
import com.ssionii.bloodNote.databinding.ActivityMainBinding
import com.ssionii.bloodNote.ui.WebActivity
import com.ssionii.bloodNote.ui.login.LoginActivity
import com.ssionii.bloodNote.ui.state.StateActivity
import com.ssionii.bloodNote.ui.write.WriteBloodPressureActivity
import com.ssionii.bloodNote.ui.write.WriteBloodSugarActivity
import com.ssionii.bloodNote.util.view.AutoScrollAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(){

    override val layoutResID: Int
        get() = R.layout.activity_main

    override val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setVp()
        setDate()
        setButton()

    }

    private fun setVp(){
        val display: Display = this.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)

        val width = size.x

        val vpAdapter = AutoScrollAdapter(this)

        vpAdapter.run{
            setOnItemClickListener(onBannerItemClickListener)
            replaceAll(viewModel.mainBannerList)
            notifyDataSetChanged()
        }

        viewDataBinding.actMainAsvp.run{
            adapter = vpAdapter
            setInterval(3000)
            setCycle(true)

            layoutParams.height = (width * 0.75).toInt()

            addOnPageChangeListener(onBannerChangeListener)
            startAutoScroll()
        }

        viewDataBinding.actMainCai.createDotPanel(viewModel.mainBannerList.size, R.drawable.dot_unselected, R.drawable.dot_selected)

    }

    private val onBannerItemClickListener
            = object : AutoScrollAdapter.OnItemClickListener{
        override fun onItemClick(url: String?, title: String) {

            val intent = Intent(this@MainActivity, WebActivity::class.java)
            intent.putExtra("url",url)
            intent.putExtra("title", title)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

            startActivity(intent)
        }
    }

    private val onBannerChangeListener
            = object : ViewPager.OnPageChangeListener{
        override fun onPageScrollStateChanged(state: Int) { }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

        override fun onPageSelected(position: Int) {
            viewDataBinding.actMainCai.selectDot(position)
        }
    }

    private fun setDate(){
        val currentTime: Date = Calendar.getInstance().getTime()

        var dateString = SimpleDateFormat("MM월 d일 ", Locale.getDefault()).format(currentTime)

        val week = SimpleDateFormat("EE", Locale.getDefault()).format(currentTime)
        when(week){
            "Mon" -> dateString += "월요일"
            "Tue" -> dateString += "화요일"
            "Wed" -> dateString += "수요일"
            "Tur" -> dateString += "목요일"
            "Fri" -> dateString += "금요일"
            "Sat" -> dateString += "토요일"
            "Sun" -> dateString += "일요일"
        }


        viewDataBinding.actMainTvDate.text = dateString

    }


    private fun setButton(){
        viewDataBinding.actMainIvBloodSugarInput.setOnClickListener {
            val intent = Intent(this, WriteBloodSugarActivity::class.java)
            startActivity(intent)
        }

        viewDataBinding.actMainIvBloodPressureInput.setOnClickListener {
            val intent = Intent(this, WriteBloodPressureActivity::class.java)
            startActivity(intent)
        }

        viewDataBinding.actMainIvMyState.setOnClickListener {
            val intent = Intent(this, StateActivity::class.java)
            startActivity(intent)
        }

        viewDataBinding.actMainLogout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            finish()
            startActivity(intent)
        }
    }
}