# 单选、多选、全选、反选状态

[源码位置](../app/src/main/java/com/abner/list/choice/ChoiceActivity.kt)

## 单选

[源码位置](../app/src/main/java/com/abner/list/choice/single/SingleFragment.kt)

只需要注意两处即可。

1、开启单选刷新

mNotifySingleChoice = true

2、判断单选，使用 mCheckPosition变量

binding?.checkbox?.isChecked = adapterPosition == mCheckPosition

### 举例代码

```kotlin
 mBinding.recycler.linear().divider().set<SingleBean> {
    mNotifySingleChoice = true//开启单选刷新
    addLayout(R.layout.layout_single_choice_list_item, BR.single)
    bindData {
        val binding = this.getDataBinding<LayoutSingleChoiceListItemBinding>()
        //判断单选，直接判断  adapterPosition == mCheckPosition 即可
        binding?.checkbox?.isChecked = adapterPosition == mCheckPosition
        setOnItemViewClickListener { view, position ->
            //条目点击
            val singleBean = getList()[position]//单选 选择的对象
            mViewModel.name.set(singleBean.name)
        }
    }
}.setList(getList())
```

## 多选

[源码位置](../app/src/main/java/com/abner/list/choice/multiple/MultipleFragment.kt)

### 1、打开多选更新

```kotlin
mNotifyMultipleChoice = true
```

### 2、多选的对象实现BaseMultipleChoiceBean

```kotlin
class MultipleBean : BaseMultipleChoiceBean {

    var name = ""//测试商品的名字

    var price = 0.0f//测试商品的价格

    fun getPriceText(): String {
        return "价格：$price"
    }

    override var isChoiceItem = false//默认不选择


}
```

### 回调监听

返回的就是选中的集合数据。

```kotlin
 setOnMultipleChoiceListener {
     
}
```

举例代码：

```kotlin
mBinding.recycler.linear().divider().set<MultipleBean> {
    mNotifyMultipleChoice = true//多选更新
    addLayout(R.layout.layout_multiple_choice_list_item, BR.multiple)
    bindData {
        //多选回调监听
        setOnMultipleChoiceListener {
            var allPrice = 0.0f
            it.forEach {
                allPrice += it.price
            }
            mViewModel.commodityNumber.set("选择商品数量为：" + it.size)
            mViewModel.allPrice.set("总价格为：$allPrice")
        }
    }
}.setList(getList())
```
