# 下拉刷新和上拉加载

[源码位置](../app/src/main/java/com/abner/list/refresh/RefreshListActivity.kt)

## 普通使用

直接使用SmartRefreshLayout，和官网的使用方式保持一致。

1、xml引入

```xml
    <com.abner.refresh.kernel.SmartRefreshLayout
        android:id="@+id/srl_layout"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <androidx.recyclerview.widget.RecyclerView
            android:id="@+id/rv_view"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />
    </com.abner.refresh.kernel.SmartRefreshLayout>
```

2、实现下拉和上拉

逻辑需要自己处理，和以往使用SmartRefreshLayout方式保持一致。

```kotlin
mSmartRefreshLayout?.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onRefresh(refreshLayout: RefreshLayout) {
               
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
             
            }
        })
```

## 封装后使用

1、xml里引入

```xml

<com.abner.relist.PageRefreshLayout android:id="@+id/refresh" android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

2、代码使用

autoRefresh:自动刷新操作
refresh：静默刷新
addData：添加数据，分页会自动追加数据，下拉和上拉会自动关闭
setEnableRefresh：是否禁止下拉
setEnableLoadMore：是否禁止上拉
finishRefresh：关闭下拉刷新
finishLoadMore：关闭上拉刷新
getPager：获取当前页码
refresh：刷新和加载方法
getSmartRefresh：获取SmartRefreshLayout
getRecycler：获取RecyclerView
addEmptyView：添加空的布局，支持layout和View
addErrorView：添加错误的布局，支持layout和View
showEmptyView：显示空布局
showErrorView：显示错误布局
hintEmptyView：隐藏空布局
hintErrorView：隐藏错误布局
setHeightWrapContent()：设置整体的列表由充满改为包裹内容。

```kotlin
mBinding.refresh.getRecycler().linear()
    .divider()
    .set<String> {
        addLayout(R.layout.layout_item, BR.str)
    }
//刷新和加载
mBinding.refresh.refresh { isRefresh, refreshLayout ->
    //isRefresh true为下拉 false为上拉
    mViewModel.doHttp {
        addData(it)
    }
}.autoRefresh()
```