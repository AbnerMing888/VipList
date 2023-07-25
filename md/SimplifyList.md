# 封装列表加载

[源码位置](../app/src/main/java/com/abner/list/simplify/SimplifyListActivity.kt)

封装列表，弱化了适配器（Adapter）的存在，简化了代码，使用起来更加的简单。

利用扩展函数，实现了链式调用，DSL语法，函数选择性调用。

## 设置布局管理器

普通的布局管理器，无论是列表、网格还是瀑布流，都需要自己创建，封装之后，只需要调用固定的方法即可。

垂直列表：linear()
横向列表：linear(RecyclerView.HORIZONTAL)
网格列表：grid(2)，一列展示几个，传递数值即可
瀑布流列表：staggered(2)，一列展示几个，传递数值即可

## 设置分割线

divider()，具体的分割线参数，具体可以查看分割线的文档。

## 设置XML视图

在set方法里 addLayout(R.layout.layout_ordinary_item)

## 数据绑定

在set方法里，通过实现bindData {}

## 获取控件

1、可通过：findView<ImageView>(R.id.iv_pic)
2、【推荐】通过DataBinding: val binding = getDataBinding<LayoutOrdinaryItemBinding>()

## 举例

```kotlin
mBinding.recycler.linear().divider()
    .set<OrdinaryListBean> {
        addLayout(R.layout.layout_ordinary_item)
        bindData {
            //获取DataBinding
            val binding = getDataBinding<LayoutOrdinaryItemBinding>()
            //获取Model
            val model = getModel(adapterPosition)
            binding?.apply {
                tvTitle.text = model.title
                tvDesc.text = model.desc
                ivPic.setImageResource(model.icon!!)
            }
            setOnItemClickListener {
                //条目点击事件
                Toast.makeText(requireContext(), "当前点击条目为：$it", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }.setList(getList())//getList()为获取数据的方法
```

## 其他源码查看

[封装纵向列表](../app/src/main/java/com/abner/list/simplify/fragment/SimplifyHorizontalFragment.kt)
[封装横向列表](../app/src/main/java/com/abner/list/simplify/fragment/SimplifyHorizontalFragment.kt)
[封装网格列表](../app/src/main/java/com/abner/list/simplify/fragment/SimplifyGridFragment.kt)
[封装瀑布流列表](../app/src/main/java/com/abner/list/simplify/fragment/SimplifyStaggeredFragment.kt)

## 总结

有的老铁可能会问了，封装之后简单是简单了，但是如果遇到复杂列表，都写到一个类里，代码量实在是太多了，哎！这确实是
个问题，但是呢，又不是问题，如果bindData里的逻辑比较多，你完成可以抽取到其他地方，比如ViewModel里，在ViewModel定义
方法后，调用即可，又或者呢，使用后边的DataBinding。