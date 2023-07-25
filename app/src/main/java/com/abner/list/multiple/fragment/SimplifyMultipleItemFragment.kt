package com.abner.list.multiple.fragment

import androidx.core.content.ContextCompat
import com.abner.list.BR
import com.abner.list.R
import com.abner.list.databinding.LayoutListBinding
import com.abner.list.multiple.data.MultipleItem01Bean
import com.abner.list.multiple.data.MultipleItem02Bean
import com.abner.list.multiple.data.MultipleItem03Bean
import com.vip.base.fragment.BaseFragment
import com.vip.list.data.BaseMultipleItem
import com.vip.list.util.divider
import com.vip.list.util.linear
import com.vip.list.util.setMore

/**
 *AUTHOR:AbnerMing
 *DATE:2022/12/23
 *INTRODUCE:封装多条目
 */
class SimplifyMultipleItemFragment : BaseFragment<LayoutListBinding>(R.layout.layout_list) {

    override fun initData() {
        mBinding.recycler.linear()
            .divider()
            .setMore {
                addLayout<MultipleItem01Bean>(R.layout.layout_multiple_01, BR.multiple1)
                addLayout<MultipleItem02Bean>(R.layout.layout_multiple_02, BR.multiple2)
                addLayout<MultipleItem03Bean>(R.layout.layout_multiple_03, BR.multiple3)
            }.setList(getMoreList())
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:模拟数据,都是测试数据
     */
    private fun getMoreList(): MutableList<BaseMultipleItem> {
        return mutableListOf<BaseMultipleItem>().apply {

            for (a in 0..50) {
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
                    bean01.icon =
                        ContextCompat.getDrawable(requireContext(), R.mipmap.vip_list_logo)
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