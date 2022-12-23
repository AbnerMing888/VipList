package com.abner.list.slide

import com.abner.list.BR
import com.abner.list.R
import com.abner.list.databinding.LayoutListBinding
import com.vip.base.activity.BaseActivity
import com.vip.list.util.linear
import com.vip.list.util.set
import com.vip.list.util.slideDelete

/**
 *AUTHOR:AbnerMing
 *DATE:2022/11/30
 *INTRODUCE:侧滑删除
 */
class SlideDeleteActivity : BaseActivity<LayoutListBinding>(R.layout.layout_list) {

    override fun initData() {
        setBarTitle("侧滑删除")
        mBinding.recycler
            .linear()
            .slideDelete()//支持侧滑删除 默认是左滑删除 0是右边  1是左右两边
            .set<String> {
                addLayout(R.layout.layout_main_item, BR.str)
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