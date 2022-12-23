package com.abner.list.data

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
 *DATE:2022/12/6
 *INTRODUCE:追加和删除数据
 */
class DataActivity : BaseActivity<LayoutListBinding>(R.layout.layout_list) {

    private var mAdapter: BAdapter<String>? = null

    override fun initData() {
        setBarTitle("数据追加和删除")
        createRight()
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
                        0 -> {//单数据添加
                            mAdapter?.addData("我是追加的单条数据")
                        }
                        1 -> {//集合数据添加
                            val list = mutableListOf<String>()
                            list.add("我是集合追加数据0")
                            list.add("我是集合追加数据1")
                            list.add("我是集合追加数据2")
                            mAdapter?.addData(list)
                        }
                        2 -> {//删除数据
                            //删除指定索引下的数据
                            mAdapter?.removeData(0)
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
            add("单条数据添加")
            add("集合数据添加")
            add("删除一个数据")
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