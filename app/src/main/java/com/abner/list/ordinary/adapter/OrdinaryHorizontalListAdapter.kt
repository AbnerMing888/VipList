package com.abner.list.ordinary.adapter

import android.widget.ImageView
import com.abner.list.R
import com.abner.list.ordinary.OrdinaryListBean
import com.vip.list.base.BaseAdapter
import com.vip.list.base.BaseViewHolder

/**
 *AUTHOR:AbnerMing
 *DATE:2022/11/30
 *INTRODUCE:普通的使用 横向，需要创建Adapter
 */
class OrdinaryHorizontalListAdapter :
    BaseAdapter<OrdinaryListBean>(R.layout.layout_ordinary_horizontal_item) {

    override fun dataOperation(holder: BaseViewHolder, t: OrdinaryListBean?, position: Int) {

        t?.title?.let {
            holder.setText(R.id.tv_title, it)
        }

        t?.desc?.let {
            holder.setText(R.id.tv_desc, it)
        }

        val ivPic = holder.findView<ImageView>(R.id.iv_pic)

        t?.icon?.let {
            ivPic.setImageResource(it)
        }


    }
}