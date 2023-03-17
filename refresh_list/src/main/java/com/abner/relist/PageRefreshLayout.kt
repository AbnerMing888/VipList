package com.abner.relist

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.abner.refresh.kernel.SmartRefreshLayout
import com.abner.refresh.kernel.api.RefreshLayout
import com.abner.refresh.kernel.listener.OnRefreshLoadMoreListener
import com.vip.list.util.getAdapter

/**
 *AUTHOR:AbnerMing
 *DATE:2022/8/19
 *INTRODUCE:下拉刷新列表
 */
class PageRefreshLayout : LinearLayout {

    private var mRecyclerView: RecyclerView? = null
    private var mSmartRefreshLayout: SmartRefreshLayout? = null
    private var mPager = 1

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.PageRefreshLayout)
        val mEnableRefresh = ta.getBoolean(R.styleable.PageRefreshLayout_srlEnableRefresh, true)
        val mEnableLoadMore = ta.getBoolean(R.styleable.PageRefreshLayout_srlEnableLoadMore, true)
        val view = View.inflate(context, R.layout.layout_refresh_list, null)
        mSmartRefreshLayout = view.findViewById(R.id.srl_layout)
        mRecyclerView = view.findViewById(R.id.rv_view)
        setEnableRefresh(mEnableRefresh)
        setEnableLoadMore(mEnableLoadMore)
        addView(view)
    }


    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:返回RecyclerView
     */
    fun getRecycler(): RecyclerView {
        return mRecyclerView!!
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:SmartRefreshLayout
     */
    fun getSmartRefresh(): SmartRefreshLayout {
        return mSmartRefreshLayout!!
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:刷新
     */
    fun refresh(block: PageRefreshLayout.(isRefresh: Boolean, refreshLayout: RefreshLayout) -> Unit): PageRefreshLayout {
        mSmartRefreshLayout?.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onRefresh(refreshLayout: RefreshLayout) {
                mPager = 1
                block.invoke(this@PageRefreshLayout, true, refreshLayout)
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                mPager++
                block.invoke(this@PageRefreshLayout, false, refreshLayout)
            }
        })
        return this
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:获取当前页码
     */
    fun getPager(): Int {
        return mPager
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:关闭下拉刷新
     */
    fun finishRefresh() {
        mSmartRefreshLayout?.finishRefresh()
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:关闭上拉刷新
     */
    fun finishLoadMore() {
        mSmartRefreshLayout?.finishLoadMore()
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:禁止上拉
     */
    fun setEnableLoadMore(enabled: Boolean) {
        mSmartRefreshLayout?.setEnableLoadMore(enabled)
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:禁止下拉
     */
    fun setEnableRefresh(enabled: Boolean) {
        mSmartRefreshLayout?.setEnableRefresh(enabled)
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:自动刷新
     */
    fun autoRefresh() {
        mSmartRefreshLayout?.autoRefresh()
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:静默刷新
     */
    fun refresh() {
        mSmartRefreshLayout?.doDefaultRefresh()
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:添加数据
     */
    fun <T> addData(@NonNull newData: Collection<T>) {
        val adapter = mRecyclerView?.getAdapter<T>()
        if (getPager() == 1) {
            adapter?.setList(newData as MutableList<T>)
        } else {
            adapter?.addData(newData as MutableList<T>)
        }
        //关闭
        mSmartRefreshLayout?.finishRefresh()
        mSmartRefreshLayout?.finishLoadMore()

    }

}