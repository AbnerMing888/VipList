# 设置缺省页面

[源码位置](../app/src/main/java/com/abner/list/default/DefaultActivity.kt)

## 初始化缺省页面

支持传递layout和View。

addEmptyView(R.layout.layout_empty)//初始化 空页面
addErrorView(R.layout.layout_error)//初始化 错误页面

```kotlin

mAdapter = mBinding.recycler.linear()
    .divider()
    .set {
        addEmptyView(R.layout.layout_empty)//初始化 空页面
        addErrorView(R.layout.layout_error)//初始化  错误页面
        addLayout(R.layout.layout_item, BR.str)
    }

```

## 显示空

mAdapter?.showEmptyView()

## 隐藏空

mAdapter?.hintEmptyView()

## 显示错误

mAdapter?.showErrorView()

## 隐藏错误

mAdapter?.hintErrorView()