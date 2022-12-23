package com.abner.list.default

import android.graphics.Color
import com.abner.list.BR
import com.abner.list.R
import com.abner.list.databinding.LayoutListBinding
import com.abner.list.utils.showDialog
import com.vip.base.activity.BaseActivity
import com.vip.list.adapter.BAdapter
import com.vip.list.util.divider
import com.vip.list.util.linear
import com.vip.list.util.set

/**
 *AUTHOR:AbnerMing
 *DATE:2022/12/7
 *INTRODUCE:缺省页面，空或者错误页面
 */
class DefaultActivity : BaseActivity<LayoutListBinding>(R.layout.layout_list) {
    private var mAdapter: BAdapter<String>? = null
    override fun initData() {
        setBarTitle("缺省页面")

        createRight()

        mAdapter = mBinding.recycler.linear()
            .divider()
            .set {
                addEmptyView(R.layout.layout_empty)//初始化 空页面
                addErrorView(R.layout.layout_error)//初始化  错误页面
                addLayout(R.layout.layout_item, BR.str)
            }

        //初始添加数据
        mAdapter?.setList(getList())
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:初始化右侧按钮
     */
    private fun createRight() {
        getActionBarView().apply {
            val rightMenu = getRightMenu("选择")
            rightMenu.setTextColor(Color.WHITE)
            setOnRightClickListener {
                //右侧按钮的点击
                showDialog(getDialogList()) {
                    when (it) {
                        0 -> {//显示空
                            mAdapter?.showEmptyView()
                        }
                        1 -> {//隐藏空
                            mAdapter?.hintEmptyView()
                        }
                        2 -> {//显示错误
                            mAdapter?.showErrorView()
                        }
                        3 -> {//隐藏错误
                            mAdapter?.hintErrorView()
                        }
                    }
                }
            }
        }
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:Dialog 列表
     */
    private fun getDialogList(): MutableList<String> {
        return mutableListOf<String>().apply {
            add("显示空页面")
            add("隐藏空页面")
            add("显示错误页面")
            add("隐藏错误页面")
        }
    }


    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:模拟数据
     */
    private fun getList(): MutableList<String> {
        return mutableListOf<String>().apply {
            for (a in 0..20) {
                add("我是第 $a 条数据")
            }
        }
    }
}