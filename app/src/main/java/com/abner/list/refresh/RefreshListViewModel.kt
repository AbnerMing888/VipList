package com.abner.list.refresh

import com.vip.base.viewmodel.BaseViewModel

/**
 *AUTHOR:AbnerMing
 *DATE:2022/12/22
 *INTRODUCE:
 */
class RefreshListViewModel : BaseViewModel() {

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:模拟网络请求
     */
    fun doHttp(block: (list: List<String>) -> Unit) {
        val list = getList()
        block.invoke(list)
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:模拟数据
     */
    private fun getList(): MutableList<String> {
        return mutableListOf<String>().apply {
            for (a in 0..20) {
                add("我是第 $a 条数据")
            }
        }
    }
}