# 设置头和尾

[源码位置](../app/src/main/java/com/abner/list/head_footer/HeadFooterActivity.kt)

## 初始化添加头尾

必须先添加数据后，再添加头和尾，这一点大家须知！头和尾支持layout和View两种形式。

```kotlin
 mAdapter = mBinding.recycler.linear()
    .divider()
    .set {
        addLayout(R.layout.layout_item, BR.str)
        bindData {
            //数据操作
        }
        bindHeadOrFoot {
            //头和尾操作
        }

    }

//初始添加数据
mAdapter?.setList(getList())

mAdapter?.addHead(R.layout.layout_head)//初始 添加头  不用刷新
mAdapter?.addFoot(R.layout.layout_foot)//初始 添加尾  不用刷新
```

## 动态添加头

支持layout和View两种形式

```kotlin

//添加头 layout形式
mAdapter?.addHead(R.layout.layout_head, true)//追加头，需要刷新

val view =
    View.inflate(this@HeadFooterActivity, R.layout.layout_head, null)
// 可以是任意的view
mAdapter?.addHead(view, true)//追加头，需要刷新
```

## 动态添加尾

支持layout和View两种形式

```kotlin
//添加尾 layout形式
mAdapter?.addFoot(R.layout.layout_foot, true)//追加尾，需要刷新

val view =
    View.inflate(this@HeadFooterActivity, R.layout.layout_foot, null)
// 可以是任意的view
mAdapter?.addFoot(view, true)//追加尾，需要刷新
```

## 删除头（支持索引删除）

```kotlin
//删除头
mAdapter?.removeHead()
```

## 删除尾（支持索引删除）

```kotlin
 //删除尾
mAdapter?.removeFooter()
```