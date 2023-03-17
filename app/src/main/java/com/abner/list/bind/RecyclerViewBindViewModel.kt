package com.abner.list.bind

import com.abner.list.BR
import com.abner.list.R
import com.abner.list.databinding.LayoutOrdinaryItemBinding
import com.abner.list.ordinary.OrdinaryListBean
import com.vip.base.viewmodel.BaseViewModel

/**
 *AUTHOR:AbnerMing
 *DATE:2022/12/19
 *INTRODUCE:
 */
class RecyclerViewBindViewModel : BaseViewModel() {

    val layoutId = R.layout.layout_ordinary_bind_item//item列表

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:获取视图绑定的name
     */
    fun getListVariableName(): Int {
        return BR.ordinary
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:模拟数据
     */
    fun getList(): MutableList<OrdinaryListBean> {
        return mutableListOf<OrdinaryListBean>().apply {
            for (a in 0..20) {
                val bean = OrdinaryListBean()
                bean.title = "我是标题$a"
                bean.desc = "我是描述信息$a"
                bean.icon = R.mipmap.vip_list_logo
                add(bean)
            }
        }
    }
}