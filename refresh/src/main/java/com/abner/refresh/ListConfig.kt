package com.abner.refresh

import android.content.Context
import com.abner.refresh.kernel.SmartRefreshLayout
import com.abner.refresh.kernel.api.RefreshFooter
import com.abner.refresh.kernel.api.RefreshHeader

/**
 *AUTHOR:AbnerMing
 *DATE:2022/8/18
 *INTRODUCE:list相关配置
 */
object ListConfig {

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:添加刷新头
     */
    fun addRefreshHeader(header: (context: Context) -> RefreshHeader) {
        //初始化下拉头
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, _ ->
            header(context)
        }
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:添加刷新尾
     */
    fun addRefreshFooter(footer: (context: Context) -> RefreshFooter) {
        //初始化下拉头
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ ->
            footer(context)
        }
    }
}