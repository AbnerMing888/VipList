# 拖拽排序

[源码位置](../app/src/main/java/com/abner/list/drag/DragActivity.kt)

## 初始化

调用drag()方法即可

```kotlin
mBinding.recycler.linear()
    .drag()//支持拖拽
    .set<DragBean> {
        addLayout(R.layout.layout_drag_item, BR.drag)
    }.setList(getList())
```

## 禁止某一条，不让拖动

1、定义对象，实现BaseNoDragBean，重写isDrag属性

```kotlin
class DragBean : BaseNoDragBean {
    override var isDrag = false

    var content = ""

}
```

2、设置数据时，动态改变属性isDrag属性即可，true可拖动，false不可拖动

```kotlin
private fun getList(): MutableList<DragBean> {
    return mutableListOf<DragBean>().apply {
        for (a in 0..20) {
            val bean = DragBean()
            if (a == 3 || a == 5) {
                bean.isDrag = false
                bean.content = "我是第 $a 条数据,我禁止拖拽"
                add(bean)
            } else {
                bean.isDrag = true
                bean.content = "我是第 $a 条数据"
                add(bean)
            }
        }
    }
}
```