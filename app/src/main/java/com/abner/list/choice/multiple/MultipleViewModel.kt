package com.abner.list.choice.multiple

import androidx.databinding.ObservableField
import com.vip.base.viewmodel.BaseViewModel

/**
 *AUTHOR:AbnerMing
 *DATE:2022/12/12
 *INTRODUCE:多选 viewModel
 */
class MultipleViewModel : BaseViewModel() {

    var commodityNumber = ObservableField<String>()//商品的数量

    var allPrice = ObservableField<String>()//商品的总价格

}