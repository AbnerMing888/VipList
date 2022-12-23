package com.abner.list.choice.multiple

import android.view.View
import com.abner.list.BR
import com.abner.list.R
import com.abner.list.databinding.LayoutMultipleChoiceListBinding
import com.vip.base.fragment.BaseVMFragment
import com.vip.list.adapter.BAdapter
import com.vip.list.util.divider
import com.vip.list.util.get
import com.vip.list.util.linear
import com.vip.list.util.set

/**
 *AUTHOR:AbnerMing
 *DATE:2022/12/9
 *INTRODUCE:多选状态
 */
class MultipleFragment : BaseVMFragment<LayoutMultipleChoiceListBinding,
        MultipleViewModel>(R.layout.layout_multiple_choice_list) {


    override fun initVMData() {
        mViewModel.commodityNumber.set("选择商品数量为：0")//默认为0
        mViewModel.allPrice.set("总价格为：0.0")//默认价格为0.0
        mBinding.recycler.linear().divider().set<MultipleBean> {
            mNotifyMultipleChoice = true//多选更新
            addLayout(R.layout.layout_multiple_choice_list_item, BR.multiple)
            bindData {
                //多选回调监听
                setOnMultipleChoiceListener {
                    var allPrice = 0.0f
                    it.forEach {
                        allPrice += it.price
                    }
                    mViewModel.commodityNumber.set("选择商品数量为：" + it.size)
                    mViewModel.allPrice.set("总价格为：$allPrice")
                }
            }
        }.setList(getList())

        //可以使用这种方式，直接拿到Adapter
        mAdapter = mBinding.recycler.get()

        mBinding.tvSelectAll.setOnClickListener(clickListener)
        mBinding.tvInvertSelection.setOnClickListener(clickListener)
    }

    private var mAdapter: BAdapter<MultipleBean>? = null

    private var isSelectAll = true
    private val clickListener = View.OnClickListener {
        when (it.id) {
            R.id.tv_select_all -> {
                //全选
                val selectAllList = mAdapter?.getList()
                var allPrice = 0.0f
                var allNumber = 0
                if (isSelectAll) {
                    mBinding.tvSelectAll.text = "取消全选"
                    selectAllList?.forEach {
                        it.isChoiceItem = true
                        allPrice += it.price
                    }
                    allNumber = selectAllList?.size!!
                    isSelectAll = false
                } else {
                    mBinding.tvSelectAll.text = "全选"
                    selectAllList?.forEach {
                        it.isChoiceItem = false
                    }
                    isSelectAll = true
                }

                mAdapter?.notifyDataSetChanged()
                mViewModel.commodityNumber.set("选择商品数量为：$allNumber")
                mViewModel.allPrice.set("总价格为：$allPrice")
            }
            R.id.tv_invert_selection -> {
                //反选

                val selectAllList = mAdapter?.getList()
                var allPrice = 0.0f
                var allNumber = 0
                selectAllList?.forEach {
                    it.isChoiceItem = !it.isChoiceItem
                    if (it.isChoiceItem) {
                        allPrice += it.price
                        allNumber++
                    }
                }
                mAdapter?.notifyDataSetChanged()
                mViewModel.commodityNumber.set("选择商品数量为：$allNumber")
                mViewModel.allPrice.set("总价格为：$allPrice")

            }
        }
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
    private fun getList(): MutableList<MultipleBean> {
        return mutableListOf<MultipleBean>().apply {
            for (a in 0..20) {
                val bean = MultipleBean()
                bean.name = "商品 $a"
                bean.price = a * 2.5f//简单给个测试价格，实际请以业务为主
                bean.isChoiceItem = false
                add(bean)
            }
        }
    }
}