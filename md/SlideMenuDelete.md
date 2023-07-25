# 侧滑显示按钮

[源码位置](../app/src/main/java/com/abner/list/slide/SlideMenuDeleteActivity.kt)

## 使用方式

调用setSlide方法即可

如果想追加侧滑按钮视图，调用addSlideLayout即可。

正常在bindData里给按钮写逻辑即可。

```kotlin
 mBinding.recycler
    .linear()
    .divider()
    .setSlide<String> {//如果要显示按钮 使用 setSlide
        addLayout(R.layout.layout_item)
        //addSlideLayout(),追加显示按钮
        bindData {
            val model = getModel(adapterPosition)
            setText(R.id.tv_content, model)
        }
    }.setList(getList())
```