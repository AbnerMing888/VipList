package com.abner.list.choice.multiple

import com.vip.list.data.BaseMultipleChoiceBean

/**
 *AUTHOR:AbnerMing
 *DATE:2022/12/12
 *INTRODUCE:多选的Bean,用于测试
 */
class MultipleBean : BaseMultipleChoiceBean {

    var name = ""//测试商品的名字

    var price = 0.0f//测试商品的价格

    fun getPriceText(): String {
        return "价格：$price"
    }

    override var isChoiceItem = false//默认不选择


}