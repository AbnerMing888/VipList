package com.vip.list.listener

import android.content.Context
import android.graphics.Rect
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout
import android.widget.Scroller
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vip.list.base.BaseViewHolder
import kotlin.math.abs

/**
 *AUTHOR:AbnerMing
 *DATE:2022/11/10
 *INTRODUCE:
 */
class OnItemTouchListener : View.OnTouchListener {

    private var mScroller: Scroller? = null
    private var mMaxLength: Float? = null
    private var mTouchSlop: Int? = null
    private var mContext: Context? = null
    private var mRecyclerView: RecyclerView? = null

    private var xDown = 0
    private var yDown = 0
    private var xMove = 0
    private var yMove = 0

    private var mCurSelectPosition = 0//选中的item索引

    private var mIsFirst = true//记录第一次touch

    private var mMoveWidth = 0//记录连续移动的长度

    private var mHiddenWidth = 0//隐藏部分长度

    private var mCurItemLayout: LinearLayout? = null
    private var mLastItemLayout: LinearLayout? = null

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:初始化基本信息
     */
    fun init(recyclerView: RecyclerView) {
        mRecyclerView = recyclerView
        mContext = recyclerView.context
        //滑动到最小距离
        mTouchSlop = ViewConfiguration.get(mContext).scaledTouchSlop
        //滑动的最大距离
        mMaxLength = (180 * mContext?.resources?.displayMetrics?.density!! + 0.5f)
        //初始化Scroller
        mScroller = Scroller(mContext, LinearInterpolator(mContext, null))

    }

    override fun onTouch(p0: View?, e: MotionEvent?): Boolean {
        val x = e?.x?.toInt()!!
        val y = e.y.toInt()
        when (e.action) {
            MotionEvent.ACTION_DOWN -> {
                //按下
                //记录当前按下的坐标
                xDown = x
                yDown = y

                //计算选中哪个Item
                val firstPosition = (mRecyclerView?.layoutManager as LinearLayoutManager)
                    .findFirstVisibleItemPosition()
                val itemRect = Rect()

                val count: Int = mRecyclerView?.childCount!!
                for (i in 0 until count) {
                    val child: View = mRecyclerView?.getChildAt(i)!!
                    if (child.visibility == View.VISIBLE) {
                        child.getHitRect(itemRect)
                        if (itemRect.contains(x, y)) {
                            mCurSelectPosition = firstPosition + i
                            break
                        }
                    }
                }

                if (mIsFirst) { //第一次时，不用重置上一次的Item
                    mIsFirst = false
                } else {
                    //屏幕再次接收到点击时，恢复上一次Item的状态
                    if (mLastItemLayout != null && mMoveWidth > 0) {
                        //将Item右移，恢复原位
                        scrollRight(mLastItemLayout!!, 0 - mMoveWidth)
                        //清空变量
                        mHiddenWidth = 0
                        mMoveWidth = 0
                    }
                }
                //取到当前选中的Item，赋给mCurItemLayout，以便对其进行左移
                val item: View = mRecyclerView?.getChildAt(mCurSelectPosition - firstPosition)!!

                //获取当前选中的Item
                val childViewHolder = mRecyclerView?.getChildViewHolder(item) as BaseViewHolder
               // mCurItemLayout = childViewHolder.findView<LinearLayout>(R.id.ll_item)

                //这里将删除按钮的宽度设为可以移动的距离
                mHiddenWidth = 100

            }
            MotionEvent.ACTION_MOVE -> {
                //移动
                xMove = x
                yMove = y
                val dx = xMove - xDown //为负时：手指向左滑动；为正时：手指向右滑动。这与Android的屏幕坐标定义有关
                val dy = yMove - yDown //

                //左滑
                if (dx < 0 && abs(dx) > mTouchSlop!! && abs(dy) < mTouchSlop!!) {
                    var newScrollX = abs(dx)
                    if (mMoveWidth >= mHiddenWidth) { //超过了，不能再移动了
                        newScrollX = 0
                    } else if (mMoveWidth + newScrollX > mHiddenWidth) { //这次要超了，
                        newScrollX = mHiddenWidth - mMoveWidth
                    }
                    //左滑，每次滑动手指移动的距离
                    scrollLeft(mCurItemLayout!!, newScrollX)
                    //对移动的距离叠加
                    mMoveWidth += newScrollX
                } else if (dx > 0) { //右滑
                    //执行右滑，这里没有做跟随，瞬间恢复
                    mCurItemLayout?.let {
                        scrollRight(it, 0 - mMoveWidth)
                    }
                    mMoveWidth = 0
                }
            }
            MotionEvent.ACTION_UP -> {
                mCurItemLayout?.let {
                    //抬起
                    val scrollX = it.scrollX
                    if (mHiddenWidth > mMoveWidth) {
                        val toX = mHiddenWidth - mMoveWidth
                        mMoveWidth = if (scrollX > mHiddenWidth / 2) { //超过一半长度时松开，则自动滑到左侧
                            scrollLeft(it, toX)
                            mHiddenWidth
                        } else { //不到一半时松开，则恢复原状
                            scrollRight(it, 0 - mMoveWidth)
                            0
                        }
                    }
                    mLastItemLayout = it
                }

            }
        }
        return false
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:向左滑动
     */
    private fun scrollLeft(item: View, scrollX: Int) {
        item.scrollBy(scrollX, 0)
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:向右滑动
     */
    private fun scrollRight(item: View, scrollX: Int) {
        item.scrollBy(scrollX, 0)
    }
}