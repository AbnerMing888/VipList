package com.abner.list.simplify.fragment

import android.widget.Toast
import com.abner.list.R
import com.abner.list.databinding.LayoutListBinding
import com.abner.list.databinding.LayoutOrdinaryHorizontalItemBinding
import com.abner.list.databinding.LayoutOrdinaryItemBinding
import com.abner.list.ordinary.OrdinaryListBean
import com.vip.base.fragment.BaseFragment
import com.vip.list.util.divider
import com.vip.list.util.set
import com.vip.list.util.staggered

/**
 *AUTHOR:AbnerMing
 *DATE:2022/12/19
 *INTRODUCE:封装之后的交错列表
 */
class SimplifyStaggeredFragment : BaseFragment<LayoutListBinding>(R.layout.layout_list) {

    override fun initData() {

        mBinding.recycler.staggered(2).divider()
            .set<OrdinaryListBean> {
                addLayout(R.layout.layout_ordinary_horizontal_item)
                bindData {
                    //获取DataBinding
                    val binding = getDataBinding<LayoutOrdinaryHorizontalItemBinding>()
                    //获取Model
                    val model = getModel(adapterPosition)
                    binding?.apply {
                        tvTitle.text = model.title
                        tvDesc.text = model.desc
                        ivPic.setImageResource(model.icon!!)
                    }
                }
                setOnItemClickListener { view, position ->
                    //条目点击事件
                    Toast.makeText(requireContext(), "当前点击条目为：$position", Toast.LENGTH_SHORT).show()
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
                bean.icon = R.mipmap.vip_list_logo
                add(bean)
            }
        }
    }

}