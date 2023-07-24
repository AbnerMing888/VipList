package com.abner.list.choice.single

import com.abner.list.BR
import com.abner.list.R
import com.abner.list.databinding.LayoutSingleChoiceListBinding
import com.abner.list.databinding.LayoutSingleChoiceListItemBinding
import com.vip.base.fragment.BaseVMFragment
import com.vip.list.util.divider
import com.vip.list.util.linear
import com.vip.list.util.set

/**
 *AUTHOR:AbnerMing
 *DATE:2022/12/9
 *INTRODUCE:单选状态
 */
class SingleFragment :
    BaseVMFragment<LayoutSingleChoiceListBinding,
            SingleViewModel>(R.layout.layout_single_choice_list) {

    override fun initVMData() {
        mViewModel.name.set("暂无选择商品")//默认暂无选择
        mBinding.recycler.linear().divider().set<SingleBean> {
            mNotifySingleChoice = true//开启单选刷新
            addLayout(R.layout.layout_single_choice_list_item, BR.single)
            bindData {
                val binding = this.getDataBinding<LayoutSingleChoiceListItemBinding>()
                //判断单选，直接判断  adapterPosition == mCheckPosition 即可
                binding?.checkbox?.isChecked = adapterPosition == mCheckPosition
                setOnItemViewClickListener { view, position ->
                    //条目点击
                    val singleBean = getList()[position]//单选 选择的对象
                    mViewModel.name.set(singleBean.name)
                }
            }
        }.setList(getList())
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:绑定视图
     */
    override fun getVariableId(): Int {
        return BR.choice
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:模拟数据
     */
    private fun getList(): MutableList<SingleBean> {
        return mutableListOf<SingleBean>().apply {
            for (a in 0..20) {
                val bean = SingleBean()
                bean.name = "商品 $a "
                add(bean)
            }
        }
    }

}