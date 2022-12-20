package com.ssionii.bloodNote.ui.write

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssionii.bloodNote.R
import com.ssionii.bloodNote.base.BaseRecyclerViewAdapter
import com.ssionii.bloodNote.data.model.BloodSugarTime
import com.ssionii.bloodNote.data.model.BloodSugarTimeId
import com.ssionii.bloodNote.databinding.ItemBloodSugarTimeBinding

class BloodSugarTimeRecyclerViewAdapter(context : Context) : RecyclerView.Adapter<BloodSugarTimeRecyclerViewAdapter.ViewHolder>() {


    private var listener : OnBloodSugarTimeClickListener? = null
    fun setOnBloodSugarTimeClickListener(listener : OnBloodSugarTimeClickListener){
        this.listener = listener
    }

    private val itemList = arrayListOf(
        BloodSugarTime(BloodSugarTimeId.MORNING, "아침", context.getColor(R.color.morning), false),
        BloodSugarTime(BloodSugarTimeId.NOON, "점심", context.getColor(R.color.noon), false),
        BloodSugarTime(BloodSugarTimeId.AFTERNOON, "저녁", context.getColor(R.color.afternoon),false),
        BloodSugarTime(BloodSugarTimeId.NIGHT , "밤", context.getColor(R.color.night), false)
    )

    private fun select(position : Int){

        for(item in itemList){
            item.isSelected = false
        }

        itemList[position].isSelected = true

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = itemList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewDataBinding = DataBindingUtil.inflate<ItemBloodSugarTimeBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_blood_sugar_time, parent, false
        )

        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dataBinding.bloodSugarTime = itemList[position]

        holder.dataBinding.root.setOnClickListener {
            select(position)
            listener?.onClick(itemList[position].id)
        }
    }

    inner class ViewHolder(val dataBinding: ItemBloodSugarTimeBinding ) : RecyclerView.ViewHolder(dataBinding.root)

    interface OnBloodSugarTimeClickListener{
        fun onClick(id : Float)
    }
}

