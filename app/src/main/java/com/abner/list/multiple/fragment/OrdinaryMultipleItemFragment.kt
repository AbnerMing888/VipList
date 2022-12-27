package com.abner.list.multiple.fragment

import androidx.core.content.ContextCompat
import com.abner.list.R
import com.abner.list.databinding.LayoutListBinding
import com.abner.list.multiple.adapter.OrdinaryMultipleItemAdapter
import com.abner.list.multiple.data.MultipleItem01Bean
import com.abner.list.multiple.data.MultipleItem02Bean
import com.abner.list.multiple.data.MultipleItem03Bean
import com.vip.base.fragment.BaseFragment
import com.vip.list.data.BaseMultipleItemBean
import com.vip.list.util.divider
import com.vip.list.util.linear

/**
 *AUTHOR:AbnerMing
 *DATE:2022/12/23
 *INTRODUCE:普通的多条目加载
 */
class OrdinaryMultipleItemFragment : BaseFragment<LayoutListBinding>(R.layout.layout_list) {

    override fun initData() {
        val adapter = OrdinaryMultipleItemAdapter()
        mBinding.recycler.linear()
            .divider().adapter = adapter
        adapter.setList(getMoreList())
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:模拟数据,都是测试数据
     */
    private fun getMoreList(): MutableList<BaseMultipleItemBean> {
        return mutableListOf<BaseMultipleItemBean>().apply {

            for (a in 0..20) {
                if (a % 3 == 0) {
                    val bean02 = MultipleItem02Bean()
                    bean02.icon01 =
                        ContextCompat.getDrawable(requireContext(), R.mipmap.vip_list_logo)
                    bean02.icon02 =
                        ContextCompat.getDrawable(requireContext(), R.mipmap.vip_list_logo)
                    bean02.icon03 =
                        ContextCompat.getDrawable(requireContext(), R.mipmap.vip_list_logo)
                    add(bean02)
                }

                if (a % 2 == 0) {
                    val bean01 = MultipleItem01Bean()
                    bean01.icon = ContextCompat.getDrawable(requireContext(), R.mipmap.vip_list_logo)
                    bean01.title = "我是类型一测试标题"
                    bean01.desc = "我是类型一测试描述"
                    add(bean01)
                }

                if (a % 5 == 0) {
                    val bean03 = MultipleItem03Bean()
                    bean03.content = "我是类型三测试数据"
                    add(bean03)
                }

            }

        }
    }
}