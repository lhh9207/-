package com.ssionii.bloodNote.util.dataBinding

import android.graphics.drawable.Drawable
import android.media.Image
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.ssionii.bloodNote.R
import com.ssionii.bloodNote.data.model.BloodSugarTimeId
import com.ssionii.bloodNote.ui.state.report.BloodType


@BindingAdapter("glideDrawable")
fun ImageView.setGlideImg(img: Drawable) {

    Glide.with(context)
        .load(img)
        .thumbnail(0.1f)
        .into(this)

}

@BindingAdapter("bloodSugarTimeColor", "bloodSugarTimeSelected")
fun ConstraintLayout.setBloodSugarTimeBg(color : Int , isSelected : Boolean){
    if(isSelected){
        setBackgroundColor(color)
    } else {
        setBackgroundDrawable(resources.getDrawable(R.drawable.border_4_dddddd))
    }

}


@BindingAdapter("bloodSugarTimeId", "bloodSugarTimeSelected")
fun ImageView.setBloodSugarTimeImg(id : Float , isSelected : Boolean){
    if(isSelected){
        when(id){
            BloodSugarTimeId.MORNING -> setImageDrawable(resources.getDrawable(R.drawable.ic_morning_white))
            BloodSugarTimeId.NOON -> setImageDrawable(resources.getDrawable(R.drawable.ic_noon_white))
            BloodSugarTimeId.AFTERNOON -> setImageDrawable(resources.getDrawable(R.drawable.ic_afternoon_white))
            BloodSugarTimeId.NIGHT -> setImageDrawable(resources.getDrawable(R.drawable.ic_night_white))
        }
    } else {
        when(id){
            BloodSugarTimeId.MORNING -> setImageDrawable(resources.getDrawable(R.drawable.ic_morning_color))
            BloodSugarTimeId.NOON -> setImageDrawable(resources.getDrawable(R.drawable.ic_noon_color))
            BloodSugarTimeId.AFTERNOON -> setImageDrawable(resources.getDrawable(R.drawable.ic_afternoon_color))
            BloodSugarTimeId.NIGHT -> setImageDrawable(resources.getDrawable(R.drawable.ic_night_color))
        }
    }
}

@BindingAdapter("bloodSugarTimeColor", "bloodSugarTimeSelected")
fun TextView.setBloodSugarTimeBg(color : Int , isSelected : Boolean){
    if(isSelected){
        setTextColor(resources.getColor(R.color.white))
    } else {
        setTextColor(color)
    }

}

@BindingAdapter("getMeal", "isBefore")
fun RelativeLayout.setMealBgColor(getMeal : Int?, isBefore: Boolean){
    getMeal?.let{
        if(isBefore){
            if(getMeal == 0){
                setBackgroundDrawable(resources.getDrawable(R.drawable.bg_4_maincolor))
            } else if(getMeal == 1) {
                setBackgroundDrawable(resources.getDrawable(R.drawable.border_4_dddddd))
            }
        } else {
            if(getMeal == 0){
                setBackgroundDrawable(resources.getDrawable(R.drawable.border_4_dddddd))
            } else if(getMeal == 1) {
                setBackgroundDrawable(resources.getDrawable(R.drawable.bg_4_maincolor))
            }
        }

    }


}

@BindingAdapter("getMeal", "isBefore")
fun TextView.setMealTextColor(getMeal : Int?, isBefore: Boolean){
    getMeal?.let {
        if(isBefore){
            if (getMeal == 1) {
                setTextColor(resources.getColor(R.color.black1))
            } else if(getMeal == 0){
                setTextColor(resources.getColor(R.color.white))
            }
        } else {
            if (getMeal == 0) {
                setTextColor(resources.getColor(R.color.black1))
            } else if(getMeal == 1){

                setTextColor(resources.getColor(R.color.white))
            }
        }

    }
}


@BindingAdapter("dateType", "isWeak")
fun ConstraintLayout.setDateTypeBg(dateType : Int, isWeak: Boolean) {
    when (dateType) {
        0 -> {
            if (isWeak) {
                setBackgroundDrawable(resources.getDrawable(R.drawable.bg_30_maincolor))
            } else {
                setBackgroundColor(resources.getColor(R.color.transparent))
            }
        }
        1 -> {
            if (isWeak) {
                setBackgroundColor(resources.getColor(R.color.transparent))
            } else {
                setBackgroundDrawable(resources.getDrawable(R.drawable.bg_30_maincolor))
            }
        }
    }
}

@BindingAdapter("dateType", "isWeak")
fun TextView.setDateTypeTextColor(dateType : Int, isWeak: Boolean) {
    when (dateType) {
        0 -> {
            if (isWeak) {
                setTextColor(resources.getColor(R.color.white))
            } else {
                setTextColor(resources.getColor(R.color.mainColor))
            }
        }
        1 -> {
            if (isWeak) {
                setTextColor(resources.getColor(R.color.mainColor))
            } else {
                setTextColor(resources.getColor(R.color.white))
            }
        }
    }
}

@BindingAdapter("isWeak", "type")
fun TextView.setReportText(isWeak: Boolean, type : Int) {
    when(type){
        0 ->{
            if(isWeak) text = "저번주에 비해 얼마나 좋아졌나요?"
             else text = "저번달에 비해 얼마나 좋아졌나요?"
        }
        1 ->{
            if(isWeak) text = "저번주"
            else text = "저번달"
        }
        2 ->{
            if(isWeak) text = "이번주"
            else text = "이번달"
        }
    }
}

@BindingAdapter("bloodSugarValue")
fun TextView.setReportText(bloodSugarValue: Int) {
   if(bloodSugarValue == 0){
       text = "-"
   } else {
       text = bloodSugarValue.toString()
   }
}

@BindingAdapter("beforeValue", "afterValue")
fun TextView.setReportResult(beforeValue : Int, afterValue : Int) {
    if(beforeValue == afterValue){
        visibility = GONE
    } else {
        visibility = VISIBLE
        text = Math.abs(beforeValue - afterValue).toString()
        if(beforeValue - afterValue > 0){
            setTextColor(resources.getColor(R.color.afternoon))
        } else {
            setTextColor(resources.getColor(R.color.mainColor))
        }
    }
}

@BindingAdapter("beforeValue", "afterValue")
fun LinearLayout.setReportResult(beforeValue : Int, afterValue : Int) {
    if(beforeValue ==  afterValue){
        setBackgroundDrawable(resources.getDrawable(R.drawable.bg_20_eeeeee))
    } else if(beforeValue - afterValue > 0){
        setBackgroundDrawable(resources.getDrawable(R.drawable.bg_20_dfecff))
    } else {
        setBackgroundDrawable(resources.getDrawable(R.drawable.bg_20_fde4e7))
    }
}

@BindingAdapter("beforeValue", "afterValue")
fun ImageView.setReportResult(beforeValue : Int, afterValue : Int) {
    if(beforeValue == afterValue){
        setBackgroundDrawable(resources.getDrawable(R.drawable.ic_report_line))
    } else if(beforeValue - afterValue > 0){
        setBackgroundDrawable(resources.getDrawable(R.drawable.ic_report_arrow_dwon))
    } else {
        setBackgroundDrawable(resources.getDrawable(R.drawable.ic_report_arrow_up))
    }
}

@BindingAdapter("analysisResult")
fun RelativeLayout.setAnalysisResultBg(analysisResult : Int) {
   if(analysisResult < 40){
       setBackgroundDrawable(resources.getDrawable(R.drawable.bg_20_dfecff))
   } else if (analysisResult < 80){
       setBackgroundDrawable(resources.getDrawable(R.drawable.bg_20_ffe8c3))
   } else {
       setBackgroundDrawable(resources.getDrawable(R.drawable.bg_20_fde4e7))
   }
}

@BindingAdapter("analysisResult")
fun TextView.setAnalysisResultTextColor(analysisResult : Int) {
    if(analysisResult < 40){
        setTextColor(resources.getColor(R.color.afternoon))
    } else if (analysisResult < 80){
        setTextColor(resources.getColor(R.color.noon))
    } else {
        setTextColor(resources.getColor(R.color.mainColor))
    }
}

@BindingAdapter("analysisResultPercent")
fun TextView.setAnalysisResultPercent(analysisResult : String) {
    text = analysisResult + "%"
}
