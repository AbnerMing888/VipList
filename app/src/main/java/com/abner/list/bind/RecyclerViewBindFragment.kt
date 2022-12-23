package com.abner.list.bind

import com.abner.list.BR
import com.abner.list.R
import com.abner.list.databinding.LayoutBindListBinding
import com.abner.list.databinding.LayoutListBinding
import com.vip.base.fragment.BaseFragment
import com.vip.base.fragment.BaseVMFragment

/**
 *AUTHOR:AbnerMing
 *DATE:2022/12/12
 *INTRODUCE:RecyclerView绑定
 */
class RecyclerViewBindFragment :
    BaseVMFragment<LayoutBindListBinding, RecyclerViewBindViewModel>(R.layout.layout_bind_list) {


    override fun initVMData() {

    }


    override fun getVariableId(): Int {
        return BR.model
    }
}