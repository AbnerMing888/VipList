package com.vip.list.divider

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

/**
 *AUTHOR:AbnerMing
 *DATE:2022/11/10
 *INTRODUCE:分割线
 */
class ItemDivider : ItemDecoration {

    private var mDivider: Drawable? = null

    private var mLineWidth = 1

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:默认横向
     */
    private var mOrientation = RecyclerView.HORIZONTAL

    private var mItemType: Int = 0

    constructor(color: Int, orientation: Int, itemType: Int) {
        mDivider = ColorDrawable(color)
        mOrientation = orientation
        mItemType = itemType
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        if (mOrientation == RecyclerView.HORIZONTAL) {
            drawVertical(c, parent)
        } else {
            drawHorizontal(c, parent)
            if (mItemType != 0) {
                drawVertical(c, parent)
            }

        }
    }


    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:绘制横向
     */
    private fun drawHorizontal(c: Canvas, parent: RecyclerView) {
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            if (mIsHideLast && i == childCount - 1) {
                //隐藏最后一个
                break
            }
            val child = parent.getChildAt(i)
            val params = child
                .layoutParams as RecyclerView.LayoutParams
            val left = child.left - params.leftMargin
            val right = (child.right + params.rightMargin
                    + mLineWidth)
            val top = child.bottom + params.bottomMargin
            val bottom = top + mLineWidth
            mDivider!!.setBounds(left + mMargin, top, right - mMargin, bottom)
            mDivider!!.draw(c)
        }
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:绘制纵向
     */
    private fun drawVertical(c: Canvas, parent: RecyclerView) {
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            if (mIsHideLast && i == childCount - 1) {
                //隐藏最后一个
                break
            }
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.top - params.topMargin
            val bottom = child.bottom + params.bottomMargin
            val left = child.right + params.rightMargin
            val right = left + mLineWidth
            mDivider!!.setBounds(left, top, right, bottom)
            mDivider!!.draw(c)
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if (mOrientation == RecyclerView.HORIZONTAL) {
            outRect.set(0, 0, mLineWidth, 0)
        } else {
            outRect.set(0, 0, 0, mLineWidth)
        }

    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:设置线条，距离左上右下
     */
    private var mMargin: Int = 0
    fun setMargin(margin: Int) {
        mMargin = margin
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:分割线的宽度
     */
    fun setLineWidth(width: Int) {
        mLineWidth = width
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:最后一条线是否展示
     */
    private var mIsHideLast = false
    fun hideLast() {
        mIsHideLast = true
    }
}