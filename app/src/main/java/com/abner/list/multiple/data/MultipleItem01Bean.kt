package com.abner.list.multiple.data

import android.graphics.drawable.Drawable
import com.vip.list.data.BaseMultipleItemBean

/**
 *AUTHOR:AbnerMing
 *DATE:2022/12/23
 *INTRODUCE:模拟数据
 */
class MultipleItem01Bean : BaseMultipleItemBean {

    override val itemViewType: Int = 1

    var icon: Drawable? = null//模拟的测试参数

    var title: String? = null

    var desc: String? = null

}