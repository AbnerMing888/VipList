package com.vip.list.listener

import androidx.databinding.ViewDataBinding
import com.vip.list.base.BaseViewHolder

/**
 *AUTHOR:AbnerMing
 *DATE:2023/3/22
 *INTRODUCE:dataBinding转换接口
 */
interface OnViewBindDataConvertListener<T> {

    fun bind(vb: ViewDataBinding, holder: BaseViewHolder, item: T, position: Int)
}