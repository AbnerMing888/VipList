package com.vip.list.listener

import com.vip.list.base.BaseViewHolder

/**
 *AUTHOR:AbnerMing
 *DATE:2022/11/5
 *INTRODUCE:
 */
interface OnHeadOrFootConvertListener {
    fun bind(holder: BaseViewHolder, position: Int)
}