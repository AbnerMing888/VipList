@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.vip.list.util

import android.content.Context

/**
 *AUTHOR:AbnerMing
 *DATE:2022/11/7
 *INTRODUCE:列表工具类
 */
/**
 * AUTHOR:AbnerMing
 * INTRODUCE:获取屏幕的高
 */
inline fun Context.getScreenHeight(): Int {
    return resources.displayMetrics.heightPixels
}

/**
 * AUTHOR:AbnerMing
 * INTRODUCE:获取屏幕的宽
 */
inline fun Context.getScreenWidth(): Int {
    return resources.displayMetrics.widthPixels
}

inline fun Context.getStatusBarHeight(): Int {
    var result = 0
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = resources.getDimensionPixelSize(resourceId)
    }
    return result
}