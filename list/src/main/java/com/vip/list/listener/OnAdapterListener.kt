package com.vip.list.listener

import com.vip.list.adapter.BAdapter


/**
 *AUTHOR:AbnerMing
 *DATE:2022/11/7
 *INTRODUCE:
 */
interface OnAdapterListener<T> {
    fun adapter(adapter: BAdapter<T>)
}