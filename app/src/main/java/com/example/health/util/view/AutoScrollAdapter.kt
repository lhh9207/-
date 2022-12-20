package com.ssionii.bloodNote.util.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import com.ssionii.bloodNote.R
import com.ssionii.bloodNote.data.model.MainBanner
import com.ssionii.bloodNote.databinding.ItemMainBannerBinding


class AutoScrollAdapter(
    private val context : Context
) : PagerAdapter(){

    private var mOnItemClickListener: OnItemClickListener? = null
    private val itemList = ArrayList<MainBanner>()

    fun replaceAll(items: ArrayList<MainBanner>){
        itemList.clear()
        itemList.addAll(items)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val viewDataBinding = DataBindingUtil.inflate<ItemMainBannerBinding>(
            LayoutInflater.from(context),
            R.layout.item_main_banner, container, false
        )

        viewDataBinding.banner = itemList[position]

        viewDataBinding.itemMainBannerIv.setOnClickListener {
            mOnItemClickListener?.onItemClick(itemList[position].url, itemList[position].title)
        }


        viewDataBinding.root.tag = position
        container.addView(viewDataBinding.root)
        container.clipToPadding= false

        return viewDataBinding.root

    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return (view == (o as View))
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        mOnItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(url: String?, title: String)
    }

    override fun getCount(): Int {
        return itemList.size
    }

}