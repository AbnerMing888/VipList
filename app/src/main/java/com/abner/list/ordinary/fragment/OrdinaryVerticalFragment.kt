package com.abner.list.ordinary.fragment

import android.graphics.Color
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abner.list.R
import com.abner.list.databinding.LayoutListBinding
import com.abner.list.ordinary.adapter.OrdinaryListAdapter
import com.abner.list.ordinary.OrdinaryListBean
import com.vip.base.fragment.BaseFragment
import com.vip.list.divider.ItemDivider

/**
 *AUTHOR:AbnerMing
 *DATE:2022/12/19
 *INTRODUCE:普通的纵向列表
 */
class OrdinaryVerticalFragment : BaseFragment<LayoutListBinding>(R.layout.layout_list) {

    override fun initData() {
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

        //设置数据
        adapter.setList(getList())

        adapter.setOnItemClickListener {
            //条目点击事件
            Toast.makeText(requireContext(), "当前点击条目为：$it", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:模拟数据
     */
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
}