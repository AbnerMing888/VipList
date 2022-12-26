package com.abner.list.multiple.data

import android.graphics.drawable.Drawable
import com.vip.list.data.BaseMultipleItemBean

/**
 *AUTHOR:AbnerMing
 *DATE:2022/12/23
 *INTRODUCE:模拟测试数据
 */
class MultipleItem02Bean : BaseMultipleItemBean {

    override val itemViewType: Int = 2

    var icon01: Drawable? = null
    var icon02: Drawable? = null
    var icon03: Drawable? = null

}