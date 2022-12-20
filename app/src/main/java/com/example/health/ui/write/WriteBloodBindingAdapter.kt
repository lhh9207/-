package com.ssionii.bloodNote.ui.write

import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.ssionii.bloodNote.R
import com.ssionii.bloodNote.data.model.BloodSugarTimeId

@BindingAdapter("result")
fun ImageView.setResult(result : Int){
    when(result){
        0 -> setImageDrawable(resources.getDrawable(R.drawable.img_good))
        1 -> setImageDrawable(resources.getDrawable(R.drawable.img_caution))
        2 -> setImageDrawable(resources.getDrawable(R.drawable.img_danger))
    }
}

@BindingAdapter("result")
fun TextView.setResult(result : Int){
    when(result){
        0 -> text = "안전"
        1 -> text = "주의"
        2 -> text = "위험"
    }
}

@BindingAdapter("time", "getMeal")
fun TextView.setTimeMeal(time : Float, getMeal : Int){

    var resultText = ""

    when(time){
        BloodSugarTimeId.MORNING -> resultText = "아침"
        BloodSugarTimeId.NOON -> resultText = "점심"
        BloodSugarTimeId.AFTERNOON -> resultText = "저녁"
        BloodSugarTimeId.NIGHT -> resultText = "밤"
    }

    when(getMeal){
        0 -> resultText += " 식전"
        1 -> resultText += " 식후"
    }

    text = resultText


}