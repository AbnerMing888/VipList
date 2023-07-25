# 条目吸顶效果

[源码位置](../app/src/main/java/com/abner/list/stick/StickHeaderActivity.kt)

## 代码实现

调用stick方法即可

可用参数如下：

height：吸顶的条目高度
bgColor：吸顶的条目背景颜色
textColor：吸顶的条目的文字颜色
textSize：吸顶的条目的文字大小
textPaddingLeft：吸顶的条目的文字左边内边距
isTextCenter：吸顶的条目的文字是否居中
lineColor：分割线的颜色
isShowLine：是否显示分割线

```kotlin
 mBinding.recycler.linear()
    .stick()
    .set<StickHeaderBean> {
        addLayout(R.layout.layout_stick_item, BR.stick)
    }.setList(getList())
```

### 注意事项

所传递对象，必须实现BaseStickHeaderBean，并重写stickGroup，也就是需要吸顶的分组标识。

```kotlin
class StickHeaderBean : BaseStickHeaderBean {

    override var stickGroup: String = ""//分组标识

    var name = ""//普通内容
}
```