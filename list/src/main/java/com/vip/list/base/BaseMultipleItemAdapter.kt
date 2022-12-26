package com.vip.list.base

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.vip.list.data.BaseMultipleItemBean

/**
 *AUTHOR:AbnerMing
 *DATE:2022/11/2
 *INTRODUCE:多条目
 */
abstract class BaseMultipleItemAdapter : BaseAdapter<BaseMultipleItemBean>() {


    override fun isMultiple(): Boolean {
        return true
    }


    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:以布局做为多条目
     */
    override fun getMultipleItemViewType(position: Int): Int {
        val bean = getList()[position]
        return bean.itemViewType
    }

    override fun dataOperation(holder: BaseViewHolder, t: BaseMultipleItemBean?, position: Int) {
        try {
            val bind = DataBindingUtil.bind<ViewDataBinding>(holder.itemView)
            mVariableIds.forEach {
                bind?.setVariable(it, t)
                bind?.executePendingBindings()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            bindOperation(holder, t, position)
        }

    }

    abstract fun bindOperation(holder: BaseViewHolder, t: BaseMultipleItemBean?, position: Int)

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:添加布局
     */
    var mVariableIds = ArrayList<Int>()

    inline fun <reified T> addLayout(layoutId: Int, variableName: Int = -1) {
        val t = T::class.java.newInstance()
        val baseMultipleItemBean = t as BaseMultipleItemBean
        mLayouts.put(baseMultipleItemBean.itemViewType, layoutId)
        if (variableName != -1) {
            mVariableIds.add(variableName)
        }
    }

    fun addLayoutBindData(t: Any, layoutId: Int, variableName: Int = -1) {
        val baseMultipleItemBean = t as BaseMultipleItemBean
        mLayouts.put(baseMultipleItemBean.itemViewType, layoutId)
        if (variableName != -1) {
            mVariableIds.add(variableName)
        }
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:获取条目对象
     */
    fun <T> getItemBean(position: Int): T {
        return getModel(position) as T
    }
}