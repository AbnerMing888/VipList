package com.vip.list.adapter

import android.widget.LinearLayout
import androidx.databinding.ViewDataBinding
import com.vip.list.R
import com.vip.list.base.BaseViewHolder


/**
 *AUTHOR:AbnerMing
 *DATE:2022/11/10
 *INTRODUCE:滑动删除  Drag
 */
abstract class SlideDeleteAdapter<T> : BindingAdapter<T, ViewDataBinding>() {

    //条目点击
    private var mSlideItemClick: ((Int) -> Unit?)? = null


    override fun getLayoutId(): Int {
        return getViewLayout()
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:
     */
    abstract fun getViewLayout(): Int

    override fun bindOperation(holder: BaseViewHolder, item: T?, position: Int) {
        holder.findView<LinearLayout>(R.id.ll_slide).setOnClickListener {
            mSlideItemClick?.invoke(position)
        }
        slideOperation(holder, item, position)
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:数据绑定
     */
    abstract fun slideOperation(holder: BaseViewHolder, item: T?, position: Int)

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:侧滑条目点击
     */
    fun setOnSlideClickListener(slideItemClick: (position: Int) -> Unit) {
        mSlideItemClick = slideItemClick
    }

    override fun getIsSlideDelete(): Boolean {
        return true
    }
}