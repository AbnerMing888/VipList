package com.abner.list

import android.app.Application
import com.abner.refresh.ListConfig
import com.abner.refresh.footer.ClassicsFooter
import com.abner.refresh.header.ClassicsHeader
import com.vip.base.config.BaseConfig

/**
 *AUTHOR:AbnerMing
 *DATE:2022/11/19
 *INTRODUCE:
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        //标题栏初始化
        BaseConfig.apply {
            actionBarBg = R.color.color_ff0000
            statusBarColor = R.color.color_ff0000
            statusBarDarkMode = false
            titleColor = R.color.base_color_ffffff
            leftIcon = R.drawable.ic_back
        }

        //上拉加载和下拉刷新，初始化头和尾
        ListConfig.apply {
            addRefreshHeader {
                ClassicsHeader(it)
            }
            addRefreshFooter {
                ClassicsFooter(it)
            }
        }
    }
}