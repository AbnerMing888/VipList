package com.abner.list.drag

import com.abner.list.BR
import com.abner.list.R
import com.abner.list.databinding.LayoutListBinding
import com.vip.base.activity.BaseActivity
import com.vip.list.util.*

/**
 *AUTHOR:AbnerMing
 *DATE:2022/12/7
 *INTRODUCE:拖拽排序
 */
class DragActivity : BaseActivity<LayoutListBinding>(R.layout.layout_list) {

    override fun initData() {
        setBarTitle("拖拽排序")

        mBinding.recycler.linear()
            .drag()//支持拖拽
            .set<DragBean> {
                addLayout(R.layout.layout_drag_item, BR.drag)
            }.setList(getList())
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:模拟数据
     */
    private fun getList(): MutableList<DragBean> {
        return mutableListOf<DragBean>().apply {
            for (a in 0..20) {
                val bean = DragBean()
                if (a == 3 || a == 5) {
                    bean.isDrag = false
                    bean.content = "我是第 $a 条数据,我禁止拖拽"
                    add(bean)
                } else {
                    bean.isDrag = true
                    bean.content = "我是第 $a 条数据"
                    add(bean)
                }
            }
        }
    }
}