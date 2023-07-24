package com.vip.list.base

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.vip.list.data.BaseMultipleItem

/**
 *AUTHOR:AbnerMing
 *DATE:2022/11/2
 *INTRODUCE:多条目
 */
abstract class BaseMultipleItemAdapter : BaseAdapter<BaseMultipleItem>() {

    var mVariableIdMap = HashMap<String, Int>()

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

    override fun dataOperation(holder: BaseViewHolder, t: BaseMultipleItem?, position: Int) {
        val bind = DataBindingUtil.bind<ViewDataBinding>(holder.itemView)
        if (mVariableIdMap.isNotEmpty()) {
            val mVariableId = mVariableIdMap[t?.javaClass?.name]!!
            bindVariable(bind, mVariableId, t)
        }
        bindOperation(holder, t, position)
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:绑定
     */
    private fun bindVariable(bind: ViewDataBinding?, variableName: Int, t: BaseMultipleItem?) {
        bind?.setVariable(variableName, t)
        bind?.executePendingBindings()
    }

    abstract fun bindOperation(holder: BaseViewHolder, t: BaseMultipleItem?, position: Int)

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:添加布局
     */

    inline fun <reified T> addLayout(layoutId: Int, variableName: Int = -1) {
        try {
            val t = T::class.java.newInstance()
            val baseMultipleItemBean = t as BaseMultipleItem
            mLayouts.put(baseMultipleItemBean.itemViewType, layoutId)
            if (variableName != -1) {
                mVariableIdMap[t.javaClass.name] = variableName
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun addLayoutBindData(t: Any, layoutId: Int, variableName: Int = -1) {
        val baseMultipleItemBean = t as BaseMultipleItem
        mLayouts.put(baseMultipleItemBean.itemViewType, layoutId)
        if (variableName != -1) {
            mVariableIdMap[t.javaClass.name] = variableName
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