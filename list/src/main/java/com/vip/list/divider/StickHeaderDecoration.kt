package com.vip.list.divider

import android.content.Context
import android.graphics.*
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.vip.list.adapter.BAdapter

/**
 *AUTHOR:AbnerMing
 *DATE:2022/12/19
 *INTRODUCE:吸顶效果
 */
class StickHeaderDecoration : ItemDecoration {

    private var mLinePaint: Paint
    private var mTextPaint: Paint
    private var mItemHeaderPaint: Paint
    private var mTextRect: Rect
    private var mTextPaddingLeft: Int
    private var mItemHeaderHeight: Int
    private var mIsTextCenter = false
    private var mIsShowLine = true

    constructor(
        context: Context, height: Float = 40f, bgColor: Int = 0,//背景颜色
        textColor: Int = 0,//文字颜色
        textSize: Float = 14f,//文字大小
        textPaddingLeft: Float = 0f,//文字距离左边的距离
        isTextCenter: Boolean = false,//文字是否居中
        lineColor: Int = Color.parseColor("#cccccc"), isShowLine: Boolean = true//是否绘制分割线
    ) {

        mIsTextCenter = isTextCenter

        mIsShowLine = isShowLine


        mItemHeaderHeight = dp2px(context, height)
        mTextPaddingLeft = dp2px(context, textPaddingLeft)

        mTextRect = Rect()

        mItemHeaderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        //设置背景颜色
        if (bgColor != 0) {
            mItemHeaderPaint.color = bgColor
        }
        mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)

        mTextPaint.textSize = sp2px(context, textSize)

        //设置字体颜色
        if (textColor == 0) {
            mTextPaint.color = Color.WHITE
        } else {
            mTextPaint.color = textColor
        }

        //分割线的颜色
        mLinePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mLinePaint.color = lineColor
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:绘制Item的分割线和组头
     */
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val adapter = parent.adapter
        if (adapter is BAdapter<*>) {
            val count = parent.childCount//获取可见范围内Item的总数
            for (i in 0 until count) {
                val view = parent.getChildAt(i)
                val position = parent.getChildLayoutPosition(view)
                val isHeader = adapter.isItemStick(position)
                val left = parent.paddingLeft.toFloat()
                val right = parent.width - parent.paddingRight.toFloat()
                if (isHeader) {
                    c.drawRect(
                        left,
                        (view.top - mItemHeaderHeight).toFloat(),
                        right,
                        view.top.toFloat(),
                        mItemHeaderPaint
                    )

                    mTextPaint.getTextBounds(
                        adapter.getGroupName(position),
                        0,
                        adapter.getGroupName(position).length,
                        mTextRect
                    )
                    //居中
                    var tempX = left + mTextPaddingLeft

                    if (mIsTextCenter) {
                        tempX = right / 2 - mTextRect.width() / 2
                    }

                    c.drawText(
                        adapter.getGroupName(position),
                        tempX,
                        ((view.top - mItemHeaderHeight) + mItemHeaderHeight / 2 + mTextRect.height() / 2).toFloat(),
                        mTextPaint
                    )

                } else {
                    if (mIsShowLine) {
                        //是否绘制分割线
                        c.drawRect(
                            left, (view.top - 1).toFloat(), right, view.top.toFloat(), mLinePaint
                        )
                    }
                }
            }
        }
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:Item 吸顶效果
     */
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val adapter = parent.adapter
        if (adapter is BAdapter<*>) {
            val position =
                (parent.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            val view = parent.findViewHolderForAdapterPosition(position)?.itemView
            val isHeader = adapter.isItemStick(position + 1)
            val top = parent.paddingTop.toFloat()
            val left = parent.paddingLeft.toFloat()
            val right = parent.width - parent.paddingRight.toFloat()


            //居中
            var tempX = left + mTextPaddingLeft

            if (mIsTextCenter) {
                tempX = right / 2 - mTextRect.width() / 2
            }
            if (isHeader) {
                val bottom = Math.min(mItemHeaderHeight, view!!.bottom)
                c.drawRect(
                    left, top + view.top - mItemHeaderHeight, right, top + bottom, mItemHeaderPaint
                )
                mTextPaint.getTextBounds(
                    adapter.getGroupName(position),
                    0,
                    adapter.getGroupName(position).length,
                    mTextRect
                )

                c.drawText(
                    adapter.getGroupName(position),
                    tempX,
                    top + mItemHeaderHeight / 2 + mTextRect.height() / 2 - (mItemHeaderHeight - bottom),
                    mTextPaint
                )
            } else {
                c.drawRect(left, top, right, top + mItemHeaderHeight, mItemHeaderPaint)
                mTextPaint.getTextBounds(
                    adapter.getGroupName(position),
                    0,
                    adapter.getGroupName(position).length,
                    mTextRect
                )
                c.drawText(
                    adapter.getGroupName(position),
                    tempX,
                    top + mItemHeaderHeight / 2 + mTextRect.height() / 2,
                    mTextPaint
                )
            }
            c.save()
        }
    }


    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:设置Item的间距
     */
    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        val adapter = parent.adapter
        if (adapter is BAdapter<*>) {
            val position = parent.getChildLayoutPosition(view)
            val isHeader = adapter.isItemStick(position)
            if (isHeader) {
                outRect.top = mItemHeaderHeight
            } else {
                outRect.top = 1
            }
        }
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:dp转换
     */
    private fun dp2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:sp转换
     */
    private fun sp2px(context: Context, spValue: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, spValue, context.resources.displayMetrics
        )
    }
}