# 侧滑删除

[源码位置](../app/src/main/java/com/abner/list/slide/SlideDeleteActivity.kt)

```kotlin
 mBinding.recycler
    .linear()
    .slideDelete()//支持侧滑删除 默认是左滑删除 0是右边  1是左右两边
    .set<String> {
        addLayout(R.layout.layout_main_item, BR.str)
    }.setList(getList())
```

没看错，就是这么简单！！！