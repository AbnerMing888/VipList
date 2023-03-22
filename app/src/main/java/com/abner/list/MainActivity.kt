package com.abner.list

import android.content.Intent
import androidx.core.content.ContextCompat
import com.abner.list.bind.DataBindActivity
import com.abner.list.choice.ChoiceActivity
import com.abner.list.data.DataActivity
import com.abner.list.databinding.LayoutListBinding
import com.abner.list.databinding.LayoutMainItemBinding
import com.abner.list.default.DefaultActivity
import com.abner.list.divider.DividerActivity
import com.abner.list.drag.DragActivity
import com.abner.list.head_footer.HeadFooterActivity
import com.abner.list.multiple.MultipleItemActivity
import com.abner.list.ordinary.OrdinaryListActivity
import com.abner.list.refresh.RefreshListActivity
import com.abner.list.simplify.SimplifyListActivity
import com.abner.list.slide.SlideDeleteActivity
import com.abner.list.slide.SlideMenuDeleteActivity
import com.abner.list.stick.StickHeaderActivity
import com.vip.base.activity.BaseActivity
import com.vip.list.util.linear
import com.vip.list.util.set


/**
 *AUTHOR:AbnerMing
 *DATE:2022/11/18
 *INTRODUCE:BaseActivity为我的另一个特别简单的MVVM库，地址是：https://github.com/AbnerMing888/VipBase
 */
class MainActivity : BaseActivity<LayoutListBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.layout_list
    }

    override fun initData() {
        setBarTitle("列表加载")
        hintLeftMenu()
        //加载列表
        mBinding.recycler.linear().set<String> {
            mNotifySingleChoice = true//开启单选刷新
            addLayout(R.layout.layout_main_item, BR.str)
            bindData {
                val dataBinding = getDataBinding<LayoutMainItemBinding>()

                //单选
                if (adapterPosition == mCheckPosition) {
                    dataBinding?.tvContent?.setTextColor(
                        ContextCompat.getColor(this@MainActivity, R.color.color_ff0000)
                    )
                } else {
                    dataBinding?.tvContent?.setTextColor(
                        ContextCompat.getColor(this@MainActivity, R.color.base_color_272F3E)
                    )
                }

                //条目点击事件
                setOnItemClickListener { view, position ->
                    itemClick(position)
                }

            }

        }.setList(getList())

    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:条目点击
     */
    private fun itemClick(it: Int) {
        when (it) {
            0 -> {//普通列表加载
                start<OrdinaryListActivity>()
            }
            1 -> {//封装列表
                start<SimplifyListActivity>()
            }
            2 -> {//多条目
                start<MultipleItemActivity>()
            }
            3 -> {//DataBinding列表
                start<DataBindActivity>()
            }
            4 -> {//分割线
                start<DividerActivity>()
            }
            5 -> {//头和尾追加和删除
                start<HeadFooterActivity>()
            }
            6 -> {//数据追加和删除
                start<DataActivity>()
            }
            7 -> {//缺省页面
                start<DefaultActivity>()
            }
            8 -> {//拖拽排序
                start<DragActivity>()
            }
            9 -> {//侧滑删除
                start<SlideDeleteActivity>()
            }
            10 -> {//侧滑显示按钮
                start<SlideMenuDeleteActivity>()
            }
            11 -> {//条目吸顶操作
                start<StickHeaderActivity>()
            }
            12 -> {//单选、多选、全选、反选
                start<ChoiceActivity>()
            }
            13 -> {//上拉刷新和上拉
                start<RefreshListActivity>()
            }
        }
    }

    private inline fun <reified A> start() {
        startActivity(Intent(this, A::class.java))
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:获取左侧的功能列表
     */
    private fun getList(): MutableList<String> {
        val itemName = resources.getStringArray(R.array.list_array)
        val apply = mutableListOf<String>().apply {
            for (i in itemName.indices) {
                add(itemName[i])
            }
        }
        return apply
    }


}