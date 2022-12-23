package com.vip.list.adapter

import com.vip.list.base.BaseMultipleItemAdapter
import com.vip.list.base.BaseViewHolder
import com.vip.list.data.BaseMultipleItemBean
import com.vip.list.listener.OnHeadOrFootConvertListener
import com.vip.list.listener.OnMultipleConvertListener

/**
 *AUTHOR:AbnerMing
 *DATE:2022/8/19
 *INTRODUCE:多布局
 */
open class MAdapter : BaseMultipleItemAdapter() {

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:数据操作
     */
    override fun bindOperation(holder: BaseViewHolder, t: BaseMultipleItemBean?, position: Int) {
        if (mOnMultipleConvertListener != null) {
            mOnMultipleConvertListener?.bind(holder, t!!)
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
    fun bindData(block: BaseViewHolder.(t: BaseMultipleItemBean) -> Unit) {
        setConvert(object : OnMultipleConvertListener {
            override fun bind(holder: BaseViewHolder, t: BaseMultipleItemBean) {
                block(holder, t)
            }
        })
    }


    private var mOnMultipleConvertListener: OnMultipleConvertListener? = null
    private fun setConvert(mOnMultipleConvertListener: OnMultipleConvertListener) {
        this.mOnMultipleConvertListener = mOnMultipleConvertListener
    }


    private var mOnHeadOrFootConvertListener: OnHeadOrFootConvertListener? = null
    private fun setHeadOrFootConvert(mOnHeadOrFootConvertListener: OnHeadOrFootConvertListener) {
        this.mOnHeadOrFootConvertListener = mOnHeadOrFootConvertListener
    }

}