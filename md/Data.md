# 追加和删除数据

[源码位置](../app/src/main/java/com/abner/list/data/DataActivity.kt)

## 初始化添加数据

```kotlin
mAdapter = mBinding.recycler.linear()
    .divider()
    .set {
        addLayout(R.layout.layout_item, BR.str)
        bindData {
            //数据操作
        }
    }

//初始添加数据
mAdapter?.setList(getList())
```

## 单数据添加

泛型为不确定类型

```kotlin
 mAdapter?.addData("我是追加的单条数据")
```

## 集合数据添加

```kotlin
 val list = mutableListOf<String>()
list.add("我是集合追加数据0")
list.add("我是集合追加数据1")
list.add("我是集合追加数据2")
mAdapter?.addData(list)
```

## 删除数据

```kotlin
mAdapter?.removeData(0)
```