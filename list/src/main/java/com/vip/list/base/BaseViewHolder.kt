package com.vip.list.base

import android.content.Context
import android.util.SparseArray
import android.view.View
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 *AUTHOR:AbnerMing
 *DATE:2022/10/27
 *INTRODUCE:
 */
open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var mItemView: View? = null
    private var mSparse = SparseArray<View>()


    init {
        mItemView = itemView
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:设置TextView内容
     */
    fun setText(id: Int, content: String) {
        val textView = findView<TextView>(id)
        textView.text = content
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:获取View
     */
    fun <V> findView(id: Int): V {
        var view = mSparse.get(id)
        if (view == null) {
            view = mItemView?.findViewById(id)
            mSparse.put(id, view)
        }
        return view as V
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:获取DataBinding
     */
    fun <DB : ViewDataBinding> getDataBinding(): DB? {
        return DataBindingUtil.bind(itemView)
    }

    fun <DB : ViewDataBinding> getDataBinding(view: View): DB? {
        return DataBindingUtil.bind(view)
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:获取上下文
     */
    fun getContext(): Context {
        return mItemView?.context!!
    }

}