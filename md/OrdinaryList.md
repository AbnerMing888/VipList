# 普通列表加载

[源码位置](../app/src/main/java/com/abner/list/ordinary/OrdinaryListActivity.kt)

普通的列表加载，和之前的使用方式一样，创建适配器，然后设置给RecyclerView。这种方式，经过抽取封装
之后，已不建议使用，毕竟还得要每次创建一个适配器，冗余代码过多，不过呢，有的老铁想用的话，也可以的，毕竟
有的人还是喜欢用这种方式的。

## 纵向列表

[源码位置](../app/src/main/java/com/abner/list/ordinary/fragment/OrdinaryVerticalFragment.kt)

### 1、创建适配器

毕竟适配器已经抽取了父类，即便是普通使用，这里也是建议继承父类！直接继承RecyclerView.Adapter的方式，
实在冗余，这一点，大家切记！

继承父类BaseAdapter之后，使用起来也是无比的方便，需要注意的地方，只有三点

1、泛型是需要渲染的数据对象。
2、要加载的xml布局，可以使用构造传递或者通过实现getLayoutId方法传递。
3、dataOperation方法用于视图和数据进行绑定，也就是在这里进行数据设置。

**简单举例如下**

```kotlin
/**
 *AUTHOR:AbnerMing
 *DATE:2022/11/30
 *INTRODUCE:普通的使用，需要创建Adapter
 */
class OrdinaryListAdapter : BaseAdapter<OrdinaryListBean>(R.layout.layout_ordinary_item) {

    override fun dataOperation(holder: BaseViewHolder, t: OrdinaryListBean?, position: Int) {

        t?.title?.let {
            holder.setText(R.id.tv_title, it)
        }

        t?.desc?.let {
            holder.setText(R.id.tv_desc, it)
        }

        //获取控件
        val ivPic = holder.findView<ImageView>(R.id.iv_pic)

        t?.icon?.let {
            ivPic.setImageResource(it)
        }


    }
}
```

### 2、设置适配器

```kotlin

val manger = LinearLayoutManager(requireContext())
manger.orientation = LinearLayoutManager.VERTICAL
//设置布局管理器
mBinding.recycler.layoutManager = manger

val adapter = OrdinaryListAdapter()
//设置适配器
mBinding.recycler.adapter = adapter

//设置分割线
mBinding.recycler.addItemDecoration(
    ItemDivider(
        Color.parseColor("#cccccc"),
        RecyclerView.VERTICAL, 0
    )
)

//设置数据 getList()为获取数据的方法，具体可见源码：OrdinaryVerticalFragment
adapter.setList(getList())

adapter.setOnItemClickListener {
    //条目点击事件
    Toast.makeText(requireContext(), "当前点击条目为：$it", Toast.LENGTH_SHORT).show()
}
```

### 3、总结

普通的列表没什么好说的，和原生使用步骤一样，创建适配器，然后给RecyclerView设置即可。

## 补充说明

横向、网格、瀑布流列表加载，除了布局管理器不一样，其他的使用和上述的纵向列表一致，大家直接看源码即可。

[横向列表](../app/src/main/java/com/abner/list/ordinary/fragment/OrdinaryHorizontalFragment.kt)
[网格列表](../app/src/main/java/com/abner/list/ordinary/fragment/OrdinaryGridFragment.kt)
[瀑布流列表](../app/src/main/java/com/abner/list/ordinary/fragment/OrdinaryStaggeredFragment.kt)



