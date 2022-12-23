package com.abner.list.slide

import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.abner.list.BR
import com.abner.list.R
import com.abner.list.databinding.LayoutItemBinding
import com.abner.list.databinding.LayoutListBinding
import com.vip.base.activity.BaseActivity
import com.vip.list.util.divider
import com.vip.list.util.linear
import com.vip.list.util.setSlide

/**
 *AUTHOR:AbnerMing
 *DATE:2022/11/30
 *INTRODUCE:侧滑显示按钮
 */
class SlideMenuDeleteActivity : BaseActivity<LayoutListBinding>(R.layout.layout_list) {

    override fun initData() {
        setBarTitle("侧滑显示按钮")
        mBinding.recycler
            .linear()
            .divider()
            .setSlide<String> {//如果要显示按钮 使用 setSlide
                addLayout(R.layout.layout_item)
                bindData {
                    val model = getModel(adapterPosition)
                    setText(R.id.tv_content, model)
                }
            }.setList(getList())
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:模拟数据
     */
    private fun getList(): MutableList<String> {
        return mutableListOf<String>().apply {
            for (a in 0..20) {
                add("我是第 $a 条数据")
            }
        }
    }
}