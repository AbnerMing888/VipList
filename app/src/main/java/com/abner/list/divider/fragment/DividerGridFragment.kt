package com.abner.list.divider.fragment

import com.abner.list.BR
import com.abner.list.R
import com.abner.list.databinding.LayoutListBinding
import com.abner.list.ordinary.OrdinaryListBean
import com.vip.base.fragment.BaseFragment
import com.vip.list.util.divider
import com.vip.list.util.grid
import com.vip.list.util.linear
import com.vip.list.util.set

/**
 *AUTHOR:AbnerMing
 *DATE:2022/12/21
 *INTRODUCE:网格分割线
 */
class DividerGridFragment : BaseFragment<LayoutListBinding>(R.layout.layout_list) {

    override fun initData() {
        mBinding.recycler.grid(3)
            .divider(itemType = 1)
            .set<String> {
                addLayout(R.layout.layout_item, BR.str)
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