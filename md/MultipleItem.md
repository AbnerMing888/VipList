# 多条目列表加载

[源码位置](../app/src/main/java/com/abner/list/multiple/MultipleItemActivity.kt)

针对多条目，为了兼顾到不同喜好的老铁，也分为了普通用法和封装用法，大家可以选择性使用。

## 普通的多条目加载

[源码位置](../app/src/main/java/com/abner/list/multiple/fragment/OrdinaryMultipleItemFragment.kt)

### 1、创建适配器

毕竟适配器已经抽取了父类，即便是普通使用，这里也是建议继承父类！直接继承RecyclerView.Adapter的方式，
实在冗余，这一点，大家切记！

#### 设置数据和对应的xml布局

在构造方法里直接调用addLayout即可，有几个多条目类型就创建几个。
addLayout<MultipleItem01Bean>(R.layout.layout_ordinary_multiple_01)

#### 如何区分多条目类型

通过holder.itemViewType，判断是否和你设置的多条目类型是否一致。和你在数据对象里设置的itemViewType一一对应。

```kotlin
class OrdinaryMultipleItemAdapter : BaseMultipleItemAdapter {

    constructor() {
        //添加多条目类型以及绑定的数据对象
        addLayout<MultipleItem01Bean>(R.layout.layout_ordinary_multiple_01)
        addLayout<MultipleItem02Bean>(R.layout.layout_ordinary_multiple_02)
        addLayout<MultipleItem03Bean>(R.layout.layout_ordinary_multiple_03)
    }

    override fun bindOperation(holder: BaseViewHolder, t: BaseMultipleItem?, position: Int) {

        when (holder.itemViewType) {
            1 -> {
                val bean = t as MultipleItem01Bean
                holder.setText(R.id.tv_title, bean.title!!)
                holder.setText(R.id.tv_desc, bean.desc!!)
                val ivPic = holder.findView<ImageView>(R.id.iv_pic)
                ivPic.setImageDrawable(bean.icon)
            }
            2 -> {
                val bean = t as MultipleItem02Bean
                val ivPic01 = holder.findView<ImageView>(R.id.iv_01)
                val ivPic02 = holder.findView<ImageView>(R.id.iv_02)
                val ivPic03 = holder.findView<ImageView>(R.id.iv_03)

                ivPic01.setImageDrawable(bean.icon01)
                ivPic02.setImageDrawable(bean.icon02)
                ivPic03.setImageDrawable(bean.icon03)
            }
            3 -> {
                val bean = t as MultipleItem03Bean
                holder.setText(R.id.tv_content, bean.content!!)
            }
        }
    }

}

```

### 2、创建数据对象

多条目加载需要区分每条Item的类型，一定要实现BaseMultipleItem接口，实现itemViewType属性，具体的值，可以
自定义，或者根据接口返回的参数，或者直接根据item的layout布局也行。

```kotlin

class MultipleItem01Bean : BaseMultipleItem {

    override val itemViewType: Int = 1

    var icon: Drawable? = null//模拟的测试参数

    var title: String? = null

    var desc: String? = null

}

class MultipleItem02Bean : BaseMultipleItem {

    override val itemViewType: Int = 2

    var icon01: Drawable? = null
    var icon02: Drawable? = null
    var icon03: Drawable? = null

}

class MultipleItem03Bean : BaseMultipleItem {

    override val itemViewType: Int = 3

    var content: String? = null

}
```

### 3、绑定数据

既然都封装了扩展函数，就必要按照原生一步一步的操作了，直接使用扩展函数即可。

```kotlin

val adapter = OrdinaryMultipleItemAdapter()
mBinding.recycler.linear()
    .divider().adapter = adapter
adapter.setList(getMoreList())//getMoreList()为多条目数据源

```

## 封装多条目

[源码位置](../app/src/main/java/com/abner/list/multiple/fragment/SimplifyMultipleItemFragment.kt)

多条目的封装和单条目是类似的，都是告别了adapter。

### 具体使用如下

```kotlin
 mBinding.recycler.linear()
    .divider()
    .setMore {
        addLayout<MultipleItem01Bean>(R.layout.layout_multiple_01, BR.multiple1)
        addLayout<MultipleItem02Bean>(R.layout.layout_multiple_02, BR.multiple2)
        addLayout<MultipleItem03Bean>(R.layout.layout_multiple_03, BR.multiple3)
    }.setList(getMoreList())
```

看后是不是很惊讶，什么？就这几行就实现了一个多条目？没错，就这几行，那数据怎么绑定的啊，这里使用了DataBinding，
直接在Xml里进行绑定了。

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <data>

        <variable name="multiple1" type="com.abner.list.multiple.data.MultipleItem01Bean" />
    </data>

    <androidx.constraintlayout.widget.ConstraintLayout android:layout_width="match_parent"
        android:layout_height="@dimen/dp_80">

        <ImageView android:id="@+id/iv_pic" android:layout_width="@dimen/dp_60"
            android:layout_height="@dimen/gwm_dp_60" android:src="@{multiple1.icon}"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintLeft_toLeftOf="parent" app:layout_constraintTop_toTopOf="parent" />

        <LinearLayout android:layout_width="wrap_content" android:layout_height="wrap_content"
            android:layout_marginStart="@dimen/gwm_dp_10" android:orientation="vertical"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintLeft_toRightOf="@id/iv_pic"
            app:layout_constraintTop_toTopOf="parent">

            <TextView android:id="@+id/tv_title" android:layout_width="wrap_content"
                android:layout_height="wrap_content" android:text="@{multiple1.title}"
                android:textColor="#222222" android:textSize="@dimen/sp_16" />

            <TextView android:id="@+id/tv_desc" android:layout_width="wrap_content"
                android:layout_height="wrap_content" android:layout_marginTop="@dimen/dp_5"
                android:text="@{multiple1.desc}" android:textColor="#666666"
                android:textSize="@dimen/sp_14" />

        </LinearLayout>

    </androidx.constraintlayout.widget.ConstraintLayout>
</layout>
```

当然了，如果你不想在Xml进行绑定数据，你可以在setMore里实现 bindData { }方法。


