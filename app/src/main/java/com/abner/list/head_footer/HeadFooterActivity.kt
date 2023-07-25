package com.abner.list.head_footer

import android.graphics.Color
import android.view.View
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
 *DATE:2022/12/6
 *INTRODUCE:添加和删除头和尾
 */
class HeadFooterActivity : BaseActivity<LayoutListBinding>(R.layout.layout_list) {
    private var mAdapter: BAdapter<String>? = null

    override fun initData() {
        setBarTitle("添加头和尾")
        createRight()

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
                        0 -> {//添加头 layout形式
                            mAdapter?.addHead(R.layout.layout_head, true)//追加头，需要刷新
                        }
                        1 -> {//添加头 View形式
                            val view =
                                View.inflate(this@HeadFooterActivity, R.layout.layout_head, null)
                            // 可以是任意的view
                            mAdapter?.addHead(view, true)//追加头，需要刷新
                        }
                        2 -> {//添加尾 layout形式
                            mAdapter?.addFoot(R.layout.layout_foot, true)//追加尾，需要刷新
                        }
                        3 -> {//添加尾 View形式
                            val view =
                                View.inflate(this@HeadFooterActivity, R.layout.layout_foot, null)
                            // 可以是任意的view
                            mAdapter?.addFoot(view, true)//追加尾，需要刷新
                        }
                        4 -> {
                            //删除头
                            mAdapter?.removeHead()
                        }
                        5 -> {
                            //删除尾
                            mAdapter?.removeFooter()
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
            add("添加头,layout形式")
            add("添加头,View形式")
            add("添加尾,layout形式")
            add("添加尾,View形式")
            add("删除头")
            add("删除尾")
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
