package com.vip.list.adapter

import androidx.databinding.ViewDataBinding
import com.vip.list.base.BaseViewHolder
import com.vip.list.data.BaseStickHeaderBean
import com.vip.list.listener.OnConvertListener
import com.vip.list.listener.OnHeadOrFootConvertListener

/**
 *AUTHOR:AbnerMing
 *DATE:2022/11/1
 *INTRODUCE:拓展函数适配器
 */
class BAdapter<T> : BindingAdapter<T, ViewDataBinding>() {

    private var mLayoutId: Int? = null

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:单选更新
     */
    var mNotifySingleChoice = false

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:多选更新
     */
    var mNotifyMultipleChoice = false

    override fun getLayoutId(): Int {
        if (mLayoutId == null) {
            throw NullPointerException("没设置layout，我就问你是不是故意的！！！")
        }
        return mLayoutId!!
    }

    override fun isNotifySingleChoice(): Boolean {
        return mNotifySingleChoice
    }

    override fun isNotifyMultipleChoice(): Boolean {
        return mNotifyMultipleChoice
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:添加布局
     */
    fun addLayout(layoutId: Int, variableName: Int = -1) {
        mLayoutId = layoutId
        if (variableName != -1) {
            setModelId(variableName)
        }

    }

    override fun bindOperation(holder: BaseViewHolder, item: T?, position: Int) {
        if (mOnConvertListener != null) {
            mOnConvertListener?.bind(holder, item!!)
        }
    }

    override fun dataOperationHead(holder: BaseViewHolder, position: Int) {
        super.dataOperationHead(holder, position)
        convertListener(holder, position)
    }

    override fun dataOperationFoot(holder: BaseViewHolder, position: Int) {
        super.dataOperationFoot(holder, position)
        convertListener(holder, position)
    }

    private fun convertListener(holder: BaseViewHolder, position: Int) {
        if (mOnHeadOrFootConvertListener != null) {
            mOnHeadOrFootConvertListener?.bind(holder, position)
        }
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:绑定头部或者尾部
     */
    fun bindHeadOrFoot(block: BaseViewHolder.(position: Int) -> Unit) {
        setHeadOrFootConvert(object : OnHeadOrFootConvertListener {
            override fun bind(holder: BaseViewHolder, position: Int) {
                block(holder, position)
            }
        })
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:绑定数据
     */
    fun bindData(block: BaseViewHolder.(t: T) -> Unit) {
        setConvert(object : OnConvertListener<T> {
            override fun bind(holder: BaseViewHolder, item: T) {
                block(holder, item)
            }
        })
    }


    private var mOnConvertListener: OnConvertListener<T>? = null
    private fun setConvert(mOnConvertListener: OnConvertListener<T>) {
        this.mOnConvertListener = mOnConvertListener
    }

    private var mOnHeadOrFootConvertListener: OnHeadOrFootConvertListener? = null
    private fun setHeadOrFootConvert(mOnHeadOrFootConvertListener: OnHeadOrFootConvertListener) {
        this.mOnHeadOrFootConvertListener = mOnHeadOrFootConvertListener
    }


    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:是否要吸顶
     */
    fun isItemStick(position: Int): Boolean {
        if (position == 0) {
            return true
        }
        val last = getList()[position - 1]
        val t = getList()[position]
        if (last is BaseStickHeaderBean && t is BaseStickHeaderBean) {
            return last.stickGroup != t.stickGroup
        }
        return false
    }

    fun getGroupName(position: Int): String {
        val t = getList()[position]
        return (t as BaseStickHeaderBean).stickGroup
    }
}