# DataBinding列表形式

[源码位置](../app/src/main/java/com/abner/list/bind/DataBindActivity.kt)

DataBinding目前分为了列表绑定和RecyclerView绑定。

## 一、列表绑定

[源码位置](../app/src/main/java/com/abner/list/bind/ItemBindFragment.kt)

就是给一个列表的item布局进行绑定，三步走。

### 1、设置数据

**创建数据对象**

```kotlin

class OrdinaryListBean {

    var title: String? = null
    var desc: String? = null
    var icon: Int? = null //测试的图片，以本地图片来代替
}

```

**封装数据**

```kotlin
 private fun getList(): MutableList<OrdinaryListBean> {
    return mutableListOf<OrdinaryListBean>().apply {
        for (a in 0..20) {
            val bean = OrdinaryListBean()
            bean.title = "我是标题$a"
            bean.desc = "我是描述信息$a"
            bean.icon = R.mipmap.vip_list_logo
            add(bean)
        }
    }
}
```

### 2、绘制视图（xml）

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <data>

        <variable name="ordinary" type="com.abner.list.ordinary.OrdinaryListBean" />
    </data>

    <androidx.constraintlayout.widget.ConstraintLayout android:layout_width="match_parent"
        android:layout_height="@dimen/dp_80" android:paddingLeft="@dimen/dp_20"
        android:paddingRight="@dimen/dp_20">

        <ImageView android:id="@+id/iv_pic" android:layout_width="@dimen/dp_60"
            android:layout_height="@dimen/dp_60" android:src="@mipmap/vip_list_logo"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintLeft_toLeftOf="parent" app:layout_constraintTop_toTopOf="parent" />

        <LinearLayout android:layout_width="wrap_content" android:layout_height="wrap_content"
            android:layout_marginStart="@dimen/dp_10" android:orientation="vertical"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintLeft_toRightOf="@id/iv_pic"
            app:layout_constraintTop_toTopOf="parent">

            <TextView android:id="@+id/tv_title" android:layout_width="wrap_content"
                android:layout_height="wrap_content" android:text="@{ordinary.title}"
                android:textColor="#222222" android:textSize="@dimen/sp_16" />

            <TextView android:id="@+id/tv_desc" android:layout_width="wrap_content"
                android:layout_height="wrap_content" android:layout_marginTop="@dimen/dp_10"
                android:text="@{ordinary.desc}" android:textColor="#666666"
                android:textSize="@dimen/sp_14" />

        </LinearLayout>

    </androidx.constraintlayout.widget.ConstraintLayout>
</layout>
```

### 3、绑定数据

```kotlin
 mBinding.recycler.linear().divider()
    .set<OrdinaryListBean> {
        addLayout(R.layout.layout_ordinary_bind_item, BR.ordinary)
        setOnItemViewClickListener { view, position ->
            //条目点击事件
            Toast.makeText(requireContext(), "当前点击条目为：$position", Toast.LENGTH_SHORT).show()
        }
    }.setList(getList())
```

## 二、RecyclerView绑定

[源码位置](../app/src/main/java/com/abner/list/bind/RecyclerViewBindFragment.kt)

RecyclerView绑定是直接在xml视图中给RecyclerView直接绑定数据，省去了在代码中设置的步骤，也是三步走。

### 1、设置绑定BR

在Activity里获取Fragment里重写getVariableId方法，设置和xml中对应的name

```kotlin
 override fun getVariableId(): Int {
    return BR.model
}
```

### 2、ViewModel定义Xml需要的数据

```kotlin
class RecyclerViewBindViewModel : BaseViewModel() {

    val layoutId = R.layout.layout_ordinary_bind_item//item列表

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:获取视图绑定的name
     */
    fun getListVariableName(): Int {
        return BR.ordinary
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:模拟数据
     */
    fun getList(): MutableList<OrdinaryListBean> {

        return mutableListOf<OrdinaryListBean>().apply {
            for (a in 0..20) {
                val bean = OrdinaryListBean()
                bean.title = "我是标题$a"
                bean.desc = "我是描述信息$a"
                bean.icon = R.mipmap.vip_list_logo
                add(bean)
            }
        }
    }
}
```

### 3、xml中

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">

    <data>

        <variable name="model" type="com.abner.list.bind.RecyclerViewBindViewModel" />
    </data>

    <androidx.constraintlayout.widget.ConstraintLayout android:layout_width="match_parent"
        android:layout_height="match_parent">

        <androidx.recyclerview.widget.RecyclerView android:id="@+id/recycler"
            listData="@{model.list}" listLayout="@{model.layoutId}" listManager="@{0}"
            listVariableName="@{model.listVariableName}" android:layout_width="match_parent"
            android:layout_height="match_parent" />

    </androidx.constraintlayout.widget.ConstraintLayout>
</layout>
```

### 4、属性一览

| 属性                     | 类型                             | 概述                                      |
| ---------------------- | ------------------------------ | --------------------------------------- |
| listManager            | Int                            | 布局管理器：默认为纵向的普通列表。0：普通列表1：网格2：瀑布流        |
| listOrientation        | Int                            | 设置列表方向，默认纵向。横向：RecyclerView.HORIZONTAL |
| listSpanCount          | Int                            | 展示几列，适用于网格和瀑布流                          |
| isDivider              | Boolean                        | 是否展示分割线                                 |
| dividerDrawable        | Int                            | 分割线样式                                   |
| listLayout             | Int                            | Item布局                                  |
| listData               | MutableList\<T\>                | 数据                                      |
| listVariableName       | Int                            | 绑定的BR                                   |
| listAdapter            | OnAdapterListener\<T\>          | 返回适配器，可以通过这里实现，适配器中的逻辑处理                |
| isMultiple             | Boolean                        | 是否是多条目                                  |
| multipleAdapter        | OnAdapterMultipleListener      | 返回多条目适配器                                |
| multipleData           | MutableList\<BaseMultipleItem\> | 多条目数据                                   |
| multipleLayout         | MutableList\<Int\>              | 多条目布局                                   |
| multipleLayoutBindData | MutableList<Class<*>>        | layout绑定的数据对象                           |
| multipleVariableName   | MutableList\<Int\>              | xml绑定的对应的VariableName                   |




