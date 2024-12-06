package com.abner.list.edit

import com.abner.list.BR
import com.abner.list.R
import com.abner.list.databinding.ActivityEditItemBinding
import com.abner.list.drag.DragBean
import com.vip.base.activity.BaseActivity
import com.vip.list.adapter.BAdapter
import com.vip.list.util.drag
import com.vip.list.util.grid
import com.vip.list.util.set

/**
 *AUTHOR:AbnerMing
 *DATE:2023/12/11
 *INTRODUCE:编辑条目
 */
class EditItemActivity : BaseActivity<ActivityEditItemBinding>(R.layout.activity_edit_item) {

    private val selectedAdapter: BAdapter<EditItemBean> by lazy {
        mBinding.recycler.grid(3)
            .drag()//支持拖拽
            .set {
                addLayout(R.layout.layout_edit_item, BR.edit)
            }
    }

    override fun initData() {
        setBarTitle("编辑条目")


        selectedAdapter.setList(getMeList())

        mBinding.btnDrag.setOnClickListener {
            //拖拽

            selectedAdapter.getList().forEach {
                it.isDrag = true
            }
        }

        mBinding.btnNoDrag.setOnClickListener {
            //禁止拖拽
            selectedAdapter.getList().forEach {
                it.isDrag = false
            }
        }

    }

    private fun getMeList(): MutableList<EditItemBean> {
        return mutableListOf<EditItemBean>().apply {
            for (a in 0..2) {
                val bean = EditItemBean()
                bean.isDrag = false
                bean.content = "$a 条数据"
                add(bean)
            }
        }
    }

    private fun getList(): MutableList<EditItemBean> {
        return mutableListOf<EditItemBean>().apply {
            for (a in 0..5) {
                val bean = EditItemBean()
                bean.isDrag = false
                bean.content = "$a 条数据"
                add(bean)
            }
        }
    }

}