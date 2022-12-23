package com.vip.list.listener

import com.vip.list.base.BaseViewHolder
import com.vip.list.data.BaseMultipleItemBean

/**
 *AUTHOR:AbnerMing
 *DATE:2022/11/5
 *INTRODUCE:转换接口
 */
interface OnMultipleConvertListener {
    fun bind(holder: BaseViewHolder, t: BaseMultipleItemBean)
}