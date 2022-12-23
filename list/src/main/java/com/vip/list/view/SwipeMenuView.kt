package com.vip.list.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.*
import android.view.animation.AccelerateInterpolator
import android.view.animation.OvershootInterpolator
import com.vip.list.R
import com.vip.list.listener.SwipeMenuStateListener

/**
 * AUTHOR:AbnerMing
 * DATE:2022/11/16
 * INTRODUCE:自定义侧滑删除
 */
class SwipeMenuView @JvmOverloads constructor(
    private val mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(
    mContext, attrs, defStyleAttr
) {
    private var mScaledTouchSlop = 0
    private var mScaledMaximumFlingVelocity = 0

    //内容view
    private var mContentView: View? = null

    //菜单内容的宽度,也是最大的宽度距离
    private var mMenuWidth = 0
    private var mLastRawX = 0f
    private var mFirstRawX = 0f
    private var mPointerId = 0

    //滑动速度
    private var mVelocityTracker: VelocityTracker? = null

    //多点触摸判断的变量
    private var isFingerTouch = false

    //展开 关闭的动画
    private var mExpandAnim: ValueAnimator? = null
    private var mCloseAnim: ValueAnimator? = null

    //动画时间
    private val animDuration = 300

    //阻塞拦截的一个控制变量
    private var chokeIntercept = false
    //获取是否打开阻塞
    /**
     * 是否开启阻塞效果 默认开启
     * 举个例子 比如你把item1的侧滑菜单划出来了，你继续滑动item2的，
     * 这是默认是开启阻塞效果的，在你滑动item2的时候 会先关闭item1的菜单，
     * 需要再次滑动item2才可以（qq是这样子的）
     * 如果关闭这个效果，你在滑动item2的同时会同时关闭item1
     */
    var isOpenChoke = true
        private set

    //获取是否打开了侧滑菜单功能
    //是否启用侧滑 默认启用 默认左滑动 而且放置右侧
    var isEnableSwipe = true
        private set

    //获取是否打开了 菜单在左侧功能
    //是否启用右滑出现菜单 启用后是menu放置左侧
    var isEnableLeftMenu = false
        private set

    //获取点击菜单后是否直接关闭菜单
    //是否开启点击菜单内容后自动关闭菜单  默认false
    var isClickMenuAndClose = false
        private set
    private var mSwipeMenuStateListener: SwipeMenuStateListener? = null
    private fun init() {
        //获取滑动的最小值，大于这个值就认为他是滑动  默认是8
        mScaledTouchSlop = ViewConfiguration.get(mContext).scaledTouchSlop
        // 获得允许执行fling （抛）的最大速度值 （惯性速度）
        mScaledMaximumFlingVelocity = ViewConfiguration.get(mContext).scaledMaximumFlingVelocity
        isClickable = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //获取测量模式
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        //内容view的宽度
        var contentWidth = 0
        var contentMaxHeight = 0
        mMenuWidth = 0
        val childCount = childCount
        for (i in 0 until childCount) {
            val childAt = getChildAt(i)
            if (childAt.visibility == GONE) {
                continue
            }
            val layoutParams = childAt.layoutParams
            if (i == 0) {
                //让itemView的宽度为parentView的宽度
                layoutParams.width = measuredWidth
                mContentView = childAt
            }
            //测量子view的宽高
            measureChild(childAt, widthMeasureSpec, heightMeasureSpec)
            //如果parentView测量模式不是精准的
            if (heightMode != MeasureSpec.EXACTLY) {
                contentMaxHeight = Math.max(contentMaxHeight, childAt.measuredHeight)
            }
            //child测量结束后才能获取宽高
            if (i == 0) {
                contentWidth = childAt.measuredWidth
            } else {
                mMenuWidth += childAt.measuredWidth
            }
        }
        //取最大值 重新测量
        val height = Math.max(measuredHeight, contentMaxHeight)
        setMeasuredDimension(contentWidth, height)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val childCount = childCount
        val pLeft = paddingLeft
        val pTop = paddingTop
        var left = 0
        var right = 0
        for (i in 0 until childCount) {
            val childAt = getChildAt(i)
            if (childAt.visibility == GONE) {
                continue
            }
            if (i == 0) {
                childAt.layout(
                    pLeft,
                    pTop,
                    pLeft + childAt.measuredWidth,
                    pTop + childAt.measuredHeight
                )
                left += pLeft + childAt.measuredWidth
            } else {
                //放置左侧
                if (isEnableLeftMenu) {
                    childAt.layout(
                        right - childAt.measuredWidth,
                        pTop,
                        right,
                        pTop + childAt.measuredHeight
                    )
                    right -= childAt.measuredWidth
                } else {
                    //放置右侧
                    childAt.layout(
                        left,
                        pTop,
                        left + childAt.measuredWidth,
                        pTop + childAt.measuredHeight
                    )
                    left += childAt.measuredWidth
                }
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                mFirstRawX = ev.rawX
                parent.requestDisallowInterceptTouchEvent(false)
                //关闭上一个打开的SwipeMenuLayout
                chokeIntercept = false
                if (null != cacheView) {
                    if (cacheView !== this) {
                        cacheView!!.closeMenuAnim()
                        chokeIntercept = isOpenChoke
                    }
                    //屏蔽父类的事件,只要有一个侧滑菜单处于打开状态， 就不给外层布局上下滑动了
                    parent.requestDisallowInterceptTouchEvent(true)
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                //多指触摸状态改变
                isFingerTouch = false
                //如果已经侧滑出菜单，菜单范围内的点击事件不拦截
                if (Math.abs(scrollX) == Math.abs(mMenuWidth)) {
                    //菜单范围的判断
                    if (isEnableLeftMenu && ev.x < mMenuWidth
                        || !isEnableLeftMenu && ev.x > measuredWidth - mMenuWidth
                    ) {
                        //点击菜单关闭侧滑
                        if (isClickMenuAndClose) {
                            closeMenuAnim()
                        }
                        return true
                    }
                    //否则点击了item, 直接动画关闭
                    closeMenuAnim()
                    return true
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (!isEnableSwipe) {
            return super.onInterceptTouchEvent(ev)
        }
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                //多跟手指的触摸处理  isFingerTouch为true的话 表示之前已经有一个down事件了,
                isFingerTouch = if (isFingerTouch) {
                    return true
                } else {
                    true
                }
                //第一个触点的id， 此时可能有多个触点，但至少一个，计算滑动速率用
                mPointerId = ev.getPointerId(0)
                mLastRawX = ev.rawX
            }
            MotionEvent.ACTION_MOVE ->                 //大于系统给出的这个数值，就认为是滑动了 事件进行拦截,在onTouch中进行逻辑操作
                if (Math.abs(ev.rawX - mFirstRawX) >= mScaledTouchSlop) {
                    longClickable(false)
                    return true
                }
        }
        return super.onInterceptTouchEvent(ev)
    }

    private var mTouchDownX = 0f
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        //如果关闭了侧滑 直接super
        if (!isEnableSwipe) {
            return super.onTouchEvent(ev)
        }
        acquireVelocityTracker(ev)
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> mTouchDownX = ev.rawX
            MotionEvent.ACTION_MOVE -> {
                //有阻塞
                if (chokeIntercept) {
                    return true
                }
                val moveX = ev.rawX
                //默认往左滑动
                if (moveX < mTouchDownX && isEnableSwipe) {
                    //往左滑动
                    //计算移动的距离
                    val gap = mLastRawX - ev.rawX
                    //view滑动
                    scrollBy(gap.toInt(), 0)
                    if (Math.abs(gap) > mScaledTouchSlop || Math.abs(scrollX) > mScaledTouchSlop) {
                        parent.requestDisallowInterceptTouchEvent(true)
                    }
                }


                //超过范围的话--->归位
                //目前是右滑的话 （菜单在左边）
//                if (isEnableLeftMenu) {
//                    if (getScrollX() < -mMenuWidth) {
//                        scrollTo(-mMenuWidth, 0);
//                    } else if (getScrollX() > 0) {
//                        scrollTo(0, 0);
//                    }
//                } else {
//                    if (getScrollX() < 0) {
//                        scrollTo(0, 0);
//                    } else if (getScrollX() > mMenuWidth) {
//                        scrollTo(mMenuWidth, 0);
//                    }
//                }
                //重新赋值
                mLastRawX = ev.rawX
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                //unitis值为1000（毫秒）时间单位内运动了多少个像素 正负最多为mScaledMaximumFlingVelocity
                mVelocityTracker!!.computeCurrentVelocity(
                    1000,
                    mScaledMaximumFlingVelocity.toFloat()
                )
                val velocityX = mVelocityTracker!!.getXVelocity(mPointerId)
                //释放VelocityTracker
                recycleVelocityTracker()
                if (!chokeIntercept && Math.abs(ev.rawX - mFirstRawX) >= mScaledTouchSlop) {
                    //获取x方向的运动速度
                    //滑动速度超过1000  认为是快速滑动了
                    if (Math.abs(velocityX) > 1000) {
                        if (velocityX < -1000) { //左滑了
                            if (!isEnableLeftMenu) {
                                //展开Menu
                                expandMenuAnim()
                            } else {
                                //关闭Menu
                                closeMenuAnim()
                            }
                        } else { //右滑了
                            if (!isEnableLeftMenu) {
                                //关闭Menu
                                closeMenuAnim()
                            } else {
                                //展开Menu
                                expandMenuAnim()
                            }
                        }
                    } else {
                        //超过菜单布局的40% 就展开 反之关闭
                        if (Math.abs(scrollX) > mMenuWidth * 0.4) { //否则就判断滑动距离
                            //展开Menu
                            expandMenuAnim()
                        } else {
                            //关闭Menu
                            closeMenuAnim()
                        }
                    }
                    return true
                }
            }
        }
        return super.onTouchEvent(ev)
    }

    //向VelocityTracker添加MotionEvent
    private fun acquireVelocityTracker(event: MotionEvent) {
        if (null == mVelocityTracker) {
            mVelocityTracker = VelocityTracker.obtain()
        }
        mVelocityTracker!!.addMovement(event)
    }

    //释放VelocityTracker
    private fun recycleVelocityTracker() {
        if (null != mVelocityTracker) {
            mVelocityTracker!!.clear()
            mVelocityTracker!!.recycle()
            mVelocityTracker = null
        }
    }

    fun expandMenuAnim() {
        longClickable(false)
        //清除动画
        cleanAnim()
        //展开就赋值
        cacheView = this@SwipeMenuView
        mExpandAnim =
            ValueAnimator.ofInt(scrollX, if (isEnableLeftMenu) -mMenuWidth else mMenuWidth)
        mExpandAnim?.addUpdateListener { animation ->
            scrollTo(
                (animation.animatedValue as Int),
                0
            )
        }
        mExpandAnim?.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                if (mSwipeMenuStateListener != null) {
                    mSwipeMenuStateListener!!.menuIsOpen(true)
                }
            }
        })
        mExpandAnim?.interpolator = OvershootInterpolator()
        mExpandAnim?.setDuration(animDuration.toLong())?.start()
    }

    /**
     * 平滑关闭
     */
    fun closeMenuAnim() {
        cacheView = null
        //清除动画
        cleanAnim()
        mCloseAnim = ValueAnimator.ofInt(scrollX, 0)
        mCloseAnim?.addUpdateListener { animation ->
            scrollTo(
                (animation.animatedValue as Int),
                0
            )
        }
        mCloseAnim?.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                longClickable(true)
                if (mSwipeMenuStateListener != null) {
                    mSwipeMenuStateListener!!.menuIsOpen(false)
                }
            }
        })
        mCloseAnim?.interpolator = AccelerateInterpolator()
        mCloseAnim?.setDuration(animDuration.toLong())?.start()
    }

    //清除动画 防止上个动画没执行完 用户操作了另一个item
    private fun cleanAnim() {
        if (mCloseAnim != null && mCloseAnim!!.isRunning) {
            mCloseAnim!!.cancel()
        }
        if (mExpandAnim != null && mExpandAnim!!.isRunning) {
            mExpandAnim!!.cancel()
        }
    }

    /*
        每次ViewDetach的时候
         1 mCacheView置为null 防止内存泄漏(mCacheView是一个静态变量)
         2 侧滑删除后自己后，这个View被Recycler回收；复用 下一个进入屏幕的View的状态应该是普通状态，而不是展开状态。
     */
    override fun onDetachedFromWindow() {
        //避免多次调用
        if (scrollX != 0) {
            quickCloseMenu()
            cacheView = null
        }
        super.onDetachedFromWindow()
    }

    //快速关闭 没有动画时间
    fun quickCloseMenu() {
        if (scrollX != 0) {
            cleanAnim()
            scrollTo(0, 0)
            cacheView = null
        }
    }

    //快速打开 没有动画时间
    fun quickExpandMenu() {
        if (scrollX == 0) {
            cleanAnim()
            val x = if (isEnableLeftMenu) -mMenuWidth else mMenuWidth
            scrollTo(x, 0)
            cacheView = null
        }
    }

    //展开时，禁止自身的长按
    private fun longClickable(enable: Boolean) {
        isLongClickable = enable
        //        if (null != mContentView) {
//            mContentView.setLongClickable(enable);
//        }
    }

    //展开时，禁止自身的长按
    override fun performLongClick(): Boolean {
        return if (scrollX != 0) {
            true
        } else super.performLongClick()
    }

    //当前是否展开
    val isExpandMenu: Boolean
        get() = Math.abs(scaleX) >= mMenuWidth

    //设置是否打开阻塞
    fun setOpenChoke(openChoke: Boolean): SwipeMenuView {
        isOpenChoke = openChoke
        return this
    }

    //设置是否开启侧滑菜单
    fun setEnableSwipe(enableSwipe: Boolean): SwipeMenuView {
        isEnableSwipe = enableSwipe
        return this
    }

    //设置菜单是否在左侧
    fun setEnableLeftMenu(enableLeftMenu: Boolean): SwipeMenuView {
        isEnableLeftMenu = enableLeftMenu
        return this
    }

    //设置 点击菜单后是否直接关闭菜单
    fun setClickMenuAndClose(clickMenuAndClose: Boolean): SwipeMenuView {
        isClickMenuAndClose = clickMenuAndClose
        return this
    }

    fun setSwipeMenuStateListener(listener: SwipeMenuStateListener?): SwipeMenuView {
        mSwipeMenuStateListener = listener
        return this
    }

    companion object {
        //获取上一个打开的view，用来关闭 上一个打开的。暂时应该用不到
        @SuppressLint("StaticFieldLeak")
        var cacheView: SwipeMenuView? = null
    }

    init {
        val ta =
            mContext.obtainStyledAttributes(attrs, R.styleable.SwipeMenuLayout, defStyleAttr, 0)
        isEnableSwipe = ta.getBoolean(R.styleable.SwipeMenuLayout_isEnableSwipe, true)
        isEnableLeftMenu = ta.getBoolean(R.styleable.SwipeMenuLayout_isEnableLeftMenu, false)
        isOpenChoke = ta.getBoolean(R.styleable.SwipeMenuLayout_isOpenChoke, true)
        isClickMenuAndClose = ta.getBoolean(R.styleable.SwipeMenuLayout_isClickMenuAndClose, false)
        ta.recycle()
        init()
    }
}