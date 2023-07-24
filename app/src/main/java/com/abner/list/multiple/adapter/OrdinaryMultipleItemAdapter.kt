package com.abner.list.multiple.adapter

import android.widget.ImageView
import com.abner.list.R
import com.abner.list.multiple.data.MultipleItem01Bean
import com.abner.list.multiple.data.MultipleItem02Bean
import com.abner.list.multiple.data.MultipleItem03Bean
import com.vip.list.base.BaseMultipleItemAdapter
import com.vip.list.base.BaseViewHolder
import com.vip.list.data.BaseMultipleItem

/**
 *AUTHOR:AbnerMing
 *DATE:2022/12/23
 *INTRODUCE:
 */
class OrdinaryMultipleItemAdapter : BaseMultipleItemAdapter {

    constructor() {
        //添加多条目类型以及绑定的数据对象
        addLayout<MultipleItem01Bean>(R.layout.layout_ordinary_multiple_01)
        addLayout<MultipleItem02Bean>(R.layout.layout_ordinary_multiple_02)
        addLayout<MultipleItem03Bean>(R.layout.layout_ordinary_multiple_03)
    }

    override fun bindOperation(holder: BaseViewHolder, t: BaseMultipleItem?, position: Int) {

        when (holder.itemViewType) {
            1 -> {
                val bean = t as MultipleItem01Bean
                holder.setText(R.id.tv_title, bean.title!!)
                holder.setText(R.id.tv_desc, bean.desc!!)
                val ivPic = holder.findView<ImageView>(R.id.iv_pic)
                ivPic.setImageDrawable(bean.icon)
            }
            2 -> {
                val bean = t as MultipleItem02Bean
                val ivPic01 = holder.findView<ImageView>(R.id.iv_01)
                val ivPic02 = holder.findView<ImageView>(R.id.iv_02)
                val ivPic03 = holder.findView<ImageView>(R.id.iv_03)

                ivPic01.setImageDrawable(bean.icon01)
                ivPic02.setImageDrawable(bean.icon02)
                ivPic03.setImageDrawable(bean.icon03)
            }
            3 -> {
                val bean = t as MultipleItem03Bean
                holder.setText(R.id.tv_content, bean.content!!)
            }
        }
    }

}