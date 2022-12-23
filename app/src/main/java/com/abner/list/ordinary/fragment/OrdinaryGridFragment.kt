package com.abner.list.ordinary.fragment

import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.abner.list.R
import com.abner.list.databinding.LayoutListBinding
import com.abner.list.ordinary.OrdinaryListBean
import com.abner.list.ordinary.adapter.OrdinaryHorizontalListAdapter
import com.vip.base.fragment.BaseFragment

/**
 *AUTHOR:AbnerMing
 *DATE:2022/12/19
 *INTRODUCE:普通的网格列表
 */
class OrdinaryGridFragment : BaseFragment<LayoutListBinding>(R.layout.layout_list) {

    override fun initData() {
        //网格
        val manger = GridLayoutManager(requireContext(), 2)
        //设置布局管理器
        mBinding.recycler.layoutManager = manger

        val adapter = OrdinaryHorizontalListAdapter()
        //设置适配器
        mBinding.recycler.adapter = adapter


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
                bean.icon = R.mipmap.ic_launcher
                add(bean)
            }
        }
    }

}