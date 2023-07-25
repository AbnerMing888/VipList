# 设置分割线

[源码位置](../app/src/main/java/com/abner/list/divider/DividerActivity.kt)

## 使用方式

直接调用divider()方法即可

divider方法可以传递参数有：

color：分割线颜色，默认颜色为：#cccccc
orientation：分割线方向，默认是RecyclerView.VERTICAL
lineWidth：分割线的宽度
hideLast：最后一个分割线是否隐藏
margin：分割线边距，距离左右或者距离上下
itemType：分割线类型，控制是否绘制垂直的线，用于网格列表

```kotlin
 mBinding.recycler.linear()
    .divider()
    .set<OrdinaryListBean> {
        addLayout(R.layout.layout_ordinary_bind_item, BR.ordinary)
    }.setList(getList())
```

### 纵向列表分割线

divider()，默认就是纵向列表

### 横向列表分割线

divider(orientation = RecyclerView.HORIZONTAL)

### 网格分割线

divider(itemType = 1)

