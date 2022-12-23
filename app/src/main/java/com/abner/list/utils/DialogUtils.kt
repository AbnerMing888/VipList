package com.abner.list.utils

import androidx.appcompat.app.AppCompatActivity
import com.abner.list.BR
import com.abner.list.R
import com.abner.list.databinding.LayoutDialogBinding
import com.vip.dialog.showVipDialog
import com.vip.list.util.divider
import com.vip.list.util.linear
import com.vip.list.util.set

/**
 *AUTHOR:AbnerMing
 *DATE:2022/12/2
 *INTRODUCE:简单的弹出框
 */
fun AppCompatActivity.showDialog(list: MutableList<String>, itemClick: (position: Int) -> Unit) {
    showVipDialog {
        addLayout(R.layout.layout_dialog)//dialog视图添加
        bind<LayoutDialogBinding> {
            //封装模式进行列表加载
            it.recycler.linear().divider().set<String> {
                addLayout(R.layout.layout_dialog_item, BR.str)
                setOnItemClickListener {
                    itemClick.invoke(it)
                    dismiss()
                }
            }.setList(list)
        }
    }
}