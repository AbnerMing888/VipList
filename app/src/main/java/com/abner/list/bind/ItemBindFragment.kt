package com.abner.list.bind

import android.widget.Toast
import com.abner.list.BR
import com.abner.list.R
import com.abner.list.databinding.LayoutListBinding
import com.abner.list.databinding.LayoutOrdinaryItemBinding
import com.abner.list.ordinary.OrdinaryListBean
import com.vip.base.fragment.BaseFragment
import com.vip.list.util.divider
import com.vip.list.util.linear
import com.vip.list.util.set

/**
 *AUTHOR:AbnerMing
 *DATE:2022/12/12
 *INTRODUCE:条目绑定
 */
class ItemBindFragment : BaseFragment<LayoutListBinding>(R.layout.layout_list) {

    override fun initData() {
        mBinding.recycler.linear().divider()
            .set<OrdinaryListBean> {
                addLayout(R.layout.layout_ordinary_bind_item, BR.ordinary)
                setOnItemClickListener {
                    //条目点击事件
                    Toast.makeText(requireContext(), "当前点击条目为：$it", Toast.LENGTH_SHORT).show()
                }
            }.setList(getList())
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