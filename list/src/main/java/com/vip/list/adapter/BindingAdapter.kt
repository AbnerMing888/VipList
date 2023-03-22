package com.vip.list.adapter

import androidx.databinding.ViewDataBinding
import com.vip.list.base.BaseAdapter
import com.vip.list.base.BaseViewHolder

/**
 *AUTHOR:AbnerMing
 *DATE:2022/11/1
 *INTRODUCE:
 */
abstract class BindingAdapter<T, VB : ViewDataBinding> :
    BaseAdapter<T>() {

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:设置需要绑定的xml布局
     */
    private var mVariableName = -1
    fun setModelId(variableId: Int) {
        mVariableName = variableId
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:数据操作
     */
    override fun dataOperation(holder: BaseViewHolder, t: T?, position: Int) {
        if (mVariableName != -1) {
            val dataBinding = holder.getDataBinding<VB>()
            dataBinding?.setVariable(mVariableName, t)
            dataBinding?.executePendingBindings()
        }

        bindOperation(holder, t, position)

    }


    abstract fun bindOperation(holder: BaseViewHolder, item: T?, position: Int)

}