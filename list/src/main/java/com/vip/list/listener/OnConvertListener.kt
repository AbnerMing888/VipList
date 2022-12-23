package com.vip.list.listener

import com.vip.list.base.BaseViewHolder

/**
 *AUTHOR:AbnerMing
 *DATE:2022/11/5
 *INTRODUCE:转换接口
 */
interface OnConvertListener<T> {
    fun bind(holder: BaseViewHolder, item: T)
}