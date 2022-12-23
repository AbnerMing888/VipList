package com.vip.list.util

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.*
import com.vip.list.adapter.BAdapter
import com.vip.list.adapter.MAdapter
import com.vip.list.adapter.SAdapter
import com.vip.list.base.BaseAdapter
import com.vip.list.divider.ItemDivider
import com.vip.list.divider.StickHeaderDecoration

/**
 *AUTHOR:AbnerMing
 *DATE:2022/11/1
 *INTRODUCE:RecyclerView相关配置
 */

/**
 * AUTHOR:AbnerMing
 * INTRODUCE:垂直或者横向布局
 */
fun RecyclerView.linear(
    @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL
): RecyclerView {
    clearListValue()
    val manager = LinearLayoutManager(context)
    manager.orientation = orientation
    layoutManager = manager
    return this
}

/**
 * AUTHOR:AbnerMing
 * INTRODUCE:设置分割线
 */
fun RecyclerView.divider(
    color: Int = Color.parseColor("#cccccc"),
    itemType: Int = 0,
    orientation: Int = RecyclerView.VERTICAL,
    hideLast: Boolean = false,
    lineWidth: Int = 1,
    margin: Int = 0,
): RecyclerView {
    val dividerItemDecoration = ItemDivider(color, orientation, itemType)
    dividerItemDecoration.apply {
        setLineWidth(lineWidth)//设置分割线
        setMargin(margin)//设置边距
        if (hideLast) {
            hideLast()
        }
    }

    addItemDecoration(dividerItemDecoration)
    return this
}

/**
 * AUTHOR:AbnerMing
 * INTRODUCE:网格
 */
fun RecyclerView.grid(
    spanCount: Int = 1,
    @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL,
): RecyclerView {
    clearListValue()
    val manager = GridLayoutManager(context, spanCount)
    manager.orientation = orientation
    layoutManager = manager
    return this
}

fun RecyclerView.staggered(
    spanCount: Int,
    @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL
): RecyclerView {
    clearListValue()
    val manager = StaggeredGridLayoutManager(spanCount, orientation)
    layoutManager = manager
    return this
}

/**
 * AUTHOR:AbnerMing
 * INTRODUCE:支持拖拽 默认就是上下左右
 */
private var mIsDrag = false
private var mDragDirection = -1
fun RecyclerView.drag(direction: Int = -1): RecyclerView {
    mIsDrag = true
    mDragDirection = if (direction == 0) {
        ItemTouchHelper.UP or ItemTouchHelper.DOWN
    } else {
        direction
    }
    return this
}

/**
 * AUTHOR:AbnerMing
 * INTRODUCE:哪些不能拖拽
 */
private var mNoDragArray: IntArray? = null
fun RecyclerView.noDrag(vararg position: Int): RecyclerView {
    mNoDragArray = position
    return this
}

/**
 * AUTHOR:AbnerMing
 * INTRODUCE:是否支持滑动删除
 */
private var mIsSlide = false
private var mSlideDirection = -1
fun RecyclerView.slideDelete(direction: Int = -1): RecyclerView {
    mIsSlide = true
    mSlideDirection = when (direction) {
        0 -> {
            ItemTouchHelper.RIGHT
        }
        1 -> {
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        }
        else -> {
            ItemTouchHelper.LEFT
        }
    }
    return this
}

/**
 * AUTHOR:AbnerMing
 * INTRODUCE:设置适配器
 */
fun <T> RecyclerView.set(block: BAdapter<T>.() -> Unit): BAdapter<T> {
    val bAdapter = BAdapter<T>()
    if (mIsDrag) {
        bAdapter.isDrag()
        if (mDragDirection != -1) {
            bAdapter.setDragDirection(mDragDirection)
        }
    }
    //是否滑动
    if (mIsSlide) {
        bAdapter.isSlideDelete()
        if (mSlideDirection != -1) {
            bAdapter.setSlideDirection(mSlideDirection)
        }
    }
    //哪些不能拖拽
    if (mNoDragArray != null) {
        bAdapter.setRecyclerNoDrag(mNoDragArray!!)
    }
    adapter = bAdapter
    bAdapter.block()
    return bAdapter
}

/**
 * AUTHOR:AbnerMing
 * INTRODUCE:设置侧滑
 */

fun <T> RecyclerView.setSlide(block: SAdapter<T>.() -> Unit): SAdapter<T> {
    val bAdapter = SAdapter<T>()
    adapter = bAdapter
    bAdapter.block()
    return bAdapter
}

/**
 * AUTHOR:AbnerMing
 * INTRODUCE:获取多条目适配器
 */

fun RecyclerView.setMore(block: MAdapter.() -> Unit): MAdapter {
    val bAdapter = MAdapter()
    adapter = bAdapter
    bAdapter.block()
    return bAdapter
}


/**
 * AUTHOR:AbnerMing
 * INTRODUCE:吸顶效果
 */
fun RecyclerView.stick(
    height: Float = 40f,
    bgColor: Int = 0,
    textColor: Int = 0,
    textSize: Float = 14f,
    textPaddingLeft: Float = 0f,
    isTextCenter: Boolean = false,
    lineColor: Int = Color.parseColor("#cccccc"),
    isShowLine: Boolean = true,
    stickView: View? = null
): RecyclerView {
    addItemDecoration(
        StickHeaderDecoration(
            context, height, bgColor,
            textColor, textSize, textPaddingLeft, isTextCenter, lineColor, isShowLine, stickView
        )
    )
    return this
}

fun clearListValue() {
    mIsSlide = false
    mIsDrag = false
    mDragDirection = -1
    mSlideDirection = -1
}


/**
 * AUTHOR:AbnerMing
 * INTRODUCE:获取适配器
 */
fun <T> RecyclerView.get(): BAdapter<T>? {
    if (adapter != null) {
        return adapter as BAdapter<T>
    }
    return null
}

/**
 * AUTHOR:AbnerMing
 * INTRODUCE:获取BaseAdapter
 */
fun <T> RecyclerView.getAdapter(): BaseAdapter<T>? {
    if (adapter != null) {
        return adapter as BaseAdapter<T>
    }
    return null
}