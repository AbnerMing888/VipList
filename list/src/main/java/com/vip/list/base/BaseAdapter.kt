package com.vip.list.base

import android.annotation.SuppressLint
import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.vip.list.R
import com.vip.list.data.BaseMultipleChoiceBean
import com.vip.list.data.BaseNoDragBean
import java.util.*
import kotlin.collections.ArrayList


/**
 *AUTHOR:AbnerMing
 *DATE:2022/10/27
 *INTRODUCE:适配器父类
 */
abstract class BaseAdapter<T>
    (@LayoutRes private val layoutId: Int = 0) :
    RecyclerView.Adapter<BaseViewHolder>() {

    //条目点击带ViewHolder
    private var mItemViewHolderClick: ((Int, BaseViewHolder) -> Unit?)? = null

    //条目点击不带ViewHolder
    private var mItemClick: ((Int) -> Unit?)? = null

    //条目点击带View
    private var mItemViewClick: ((View, Int) -> Unit?)? = null

    //多选的最终回调
    private var mItemMultipleChoiceListener: ((List<T>) -> Unit?)? = null

    //多选的数据集合
    private var mMultipleChoiceList: MutableList<T> = arrayListOf()

    //上下文
    private var mContext: Context? = null

    //数据集合
    private var mList: MutableList<T> = arrayListOf()

    //绑定的RecyclerView
    private var mRecyclerView: RecyclerView? = null

    //添加的头集合
    private var mHeaderLayoutList = ArrayList<Int>()

    //添加的尾集合
    private var mFooterLayoutList = ArrayList<Int>()

    //多条目布局
    val mLayouts: SparseArray<Int> by lazy(LazyThreadSafetyMode.NONE) { SparseArray() }

    //是否展示空的视图
    private var mIsEmpty = false

    //是否展示错误的视图
    private var mIsError = false

    //空的视图View
    private var mEmptyView: View? = null

    private var mErrorView: View? = null

    private var mItemDecorationAt: RecyclerView.ItemDecoration? = null

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:选中的条目索引
     */
    var mCheckPosition = -1

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:上一个选中的索引
     */
    var mOldCheckPosition = -1

    @SuppressLint("ClickableViewAccessibility")
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mContext = recyclerView.context
        mRecyclerView = recyclerView

        if (mRecyclerView?.itemDecorationCount!! > 0) {
            mItemDecorationAt = mRecyclerView?.getItemDecorationAt(0)
        }

        //支持拖拽或者滑动删除，方可进行初始化
        if (mIsDrag || mIsSlideDelete) {
            bindItemTouchHelper()
        }
    }


    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        mRecyclerView = null

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view: View = if (mIsEmpty || mIsError) {
            //展示为空的视图
            LayoutInflater.from(mContext)
                .inflate(R.layout.layout_list_empty_or_error, parent, false)
        } else {
            if (isMultiple()) {
                //多条目
                val layoutId = mLayouts[viewType]
                if (layoutId != null) {
                    LayoutInflater.from(mContext).inflate(layoutId, parent, false)
                } else {
                    LayoutInflater.from(mContext).inflate(viewType, parent, false)
                }
            } else {
                if (getIsSlideDelete()) {
                    //如果是侧滑删除
                    val slideDelete = LayoutInflater.from(mContext)
                        .inflate(R.layout.layout_list_slide_delete, parent, false)

                    //获取用于加载子类布局的父View
                    val parentView =
                        slideDelete.findViewById<LinearLayout>(R.id.ll_content)
                    //获取子类视图
                    val childView = LayoutInflater.from(mContext).inflate(viewType, parent, false)
                    //追加View
                    parentView.addView(childView)
                    val height = childView.layoutParams.height
                    //设置高度
                    val layoutSlide = slideDelete.findViewById<LinearLayout>(R.id.ll_slide)
                    val slideLayout =
                        LayoutInflater.from(mContext).inflate(getSlideLayout(), parent, false)

                    layoutSlide.addView(slideLayout)
                    layoutSlide.layoutParams.height = height
                    slideDelete
                } else {
                    //否则不是
                    LayoutInflater.from(mContext).inflate(viewType, parent, false)
                }

            }
        }

        return BaseViewHolder(view)
    }

    open fun getSlideLayout(): Int {
        return R.layout.layout_list_slide_item
    }

    override fun getItemViewType(position: Int): Int {
        super.getItemViewType(position)
        return getAdapterLayout(position)//以当前的layout做为类型区分
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:是否需要单选更新
     */
    open fun isNotifySingleChoice(): Boolean {
        return false
    }


    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:是否需要多选更新
     */
    open fun isNotifyMultipleChoice(): Boolean {
        return false
    }


    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:是否是多条目
     */
    open fun isMultiple(): Boolean {
        return false
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:多条目返回的类型
     */
    open fun getMultipleItemViewType(position: Int): Int {
        return 0
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:获取传递的最终的layout
     */
    private fun getAdapterLayout(position: Int): Int {
        if (mIsEmpty || mIsError) {
            return position
        }
        //头View
        if (mHeaderLayoutList.isNotEmpty() && position < mHeaderLayoutList.size) {
            return mHeaderLayoutList[position]
        }
        //尾部
        val listSize = mList.size - mFooterLayoutList.size - 1
        if (position > listSize) {
            val index = mList.size - 1 - position
            val footerIndex = mFooterLayoutList.size - 1 - index
            return mFooterLayoutList[footerIndex]
        }

        if (isMultiple()) {
            return getMultipleItemViewType(position)
        }

        var layoutRes = layoutId
        val layout = getLayoutId()

        if (layoutRes == 0 && layout != 0) {
            layoutRes = layout
        }

        return layoutRes
    }


    override fun getItemCount(): Int {
        if (mIsEmpty || mIsError) {
            //如果数据为空或者展示错误的视频
            return 1
        }
        return mList.size
    }


    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:初始化数据添加
     */
    fun setList(list: MutableList<T>) {
        mList = list
        notifyDataSetChanged()
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:追加数据
     */
    fun addData(list: MutableList<T>) {
        val position = mList.size
        //这里要判断是否有尾，如果有尾，那么索引就要上移
        if (mFooterLayoutList.isEmpty()) {
            mList.addAll(list)
        } else {
            mList.addAll((position - mFooterLayoutList.size), list)
        }

        val endPosition = mList.size
        //从指定位置刷新
        notifyItemRangeChanged(position, endPosition)
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:追加数据
     */
    fun addData(t: T) {
        val position = mList.size
        //这里要判断是否有尾，如果有尾，那么索引就要上移
        if (mFooterLayoutList.isEmpty()) {
            mList.add(t)
        } else {
            mList.add((position - mFooterLayoutList.size), t)
        }
        val endPosition = mList.size
        //从指定位置刷新
        notifyItemRangeChanged(position, endPosition)
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:移除数据
     * @param position 索引
     */
    fun removeData(position: Int) {
        //移除数据必须在去除头和尾部之间
        val headerSize = mHeaderLayoutList.size
        val footerSize = mFooterLayoutList.size
        val listHeaderSize = mList.size - headerSize//除去头部还剩下的数据
        val listFooterSize = mList.size - footerSize//除去尾部还剩下的数据
        val index = position + headerSize
        if (listHeaderSize > 0 && index < listFooterSize) {
            mList.removeAt(index)
            notifyItemRemoved(index)
        } else {
            toast("没数据可删了")
        }

    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:获取传递的集合
     */
    fun getList(): MutableList<T> {
        return mList
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:获取对应的数据对象
     */
    fun getModel(position: Int): T {
        return mList[position]
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:子类需要传递的layout
     */
    open fun getLayoutId(): Int {
        return 0
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:添加头View
     * @param headerView View
     * @param refresh  true 刷新  false 不刷新
     */
    fun addHead(headerView: View, refresh: Boolean = false) {
        if (mList.isNotEmpty()) {
            val sourceLayoutResId = headerView.sourceLayoutResId
            mList.add(0, mList[0])
            mHeaderLayoutList.add(0, sourceLayoutResId)//追加资源
            if (refresh) {
                notifyItemInserted(0)
            }
        } else {
            toast("集合数据为空，不能添加头部View")
        }
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:添加头View  以layout id的方式
     * @param layoutId layout
     * @param refresh  true 刷新  false 不刷新
     */
    fun addHead(layoutId: Int, refresh: Boolean = false) {
        addHead(View.inflate(mContext, layoutId, null), refresh)
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:移除头View
     * @param position 移除的头部索引
     */
    fun removeHead(position: Int = 0) {
        if (position < mHeaderLayoutList.size) {
            //进行移除
            mList.removeAt(position)
            mHeaderLayoutList.removeAt(position)
            notifyItemRemoved(position)
        } else {
            toast("没有头部View了")
        }
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:追加尾View
     */
    fun addFoot(footerView: View, refresh: Boolean = false) {
        if (mList.isNotEmpty()) {
            val sourceLayoutResId = footerView.sourceLayoutResId
            mList.add(mList[0])
            mFooterLayoutList.add(sourceLayoutResId)//追加资源
            if (refresh) {
                notifyItemInserted(mList.size - 1)
            }
        } else {
            toast("集合数据为空，不能添加尾部View")
        }
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:追加尾View 以layout id的方式
     */
    fun addFoot(layoutId: Int, refresh: Boolean = false) {
        addFoot(View.inflate(mContext, layoutId, null), refresh)
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:移除尾部View
     * @param position 移除的尾部索引
     */
    fun removeFooter(position: Int = 0) {
        if (mFooterLayoutList.isNotEmpty() && position < mFooterLayoutList.size) {
            //删除最后一个
            mFooterLayoutList.removeAt(mFooterLayoutList.size - 1 - position)
            mList.removeAt(mList.size - 1 - position)
            notifyItemRemoved(mList.size - position)
        } else {
            toast("没有尾部View了")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        try {
            if (mIsEmpty || mIsError) {
                //设置空或者错误页面
                val layoutEmpty = holder.findView<FrameLayout>(R.id.layout_empty)
                layoutEmpty.removeAllViews()
                layoutEmpty.addView(if (mIsEmpty) mEmptyView else mErrorView)
            } else if (mHeaderLayoutList.isNotEmpty() && position < mHeaderLayoutList.size) {
                //头
                dataOperationHead(holder, position)
            } else if (mFooterLayoutList.isNotEmpty() && position > (mList.size - mFooterLayoutList.size - 1)) {
                //尾
                dataOperationFoot(holder, position)
            } else {
                //正常视图
                val bean = mList[position]
                dataOperation(holder, bean, position)
                holder.itemView.setOnClickListener {
                    if (mOldCheckPosition != mCheckPosition) {
                        mOldCheckPosition = mCheckPosition
                    }
                    mCheckPosition = position

                    //单选更新
                    if (isNotifySingleChoice()) {
                        notifySingleChoice()
                    }

                    //多选更新
                    if (isNotifyMultipleChoice()) {
                        val t = getList()[position]
                        if (t is BaseMultipleChoiceBean) {
                            t.isChoiceItem = !t.isChoiceItem
                            if (t.isChoiceItem) {
                                mMultipleChoiceList.add(t)
                            } else {
                                mMultipleChoiceList.remove(t)
                            }
                            //回调
                            mItemMultipleChoiceListener?.let {
                                it(mMultipleChoiceList)
                            }
                            notifyItemChanged(position)
                        }
                    }
                    //条目点击事件
                    mItemClick?.let {
                        it(position)
                    }
                    mItemViewClick?.let {
                        it(holder.itemView, position)
                    }
                    mItemViewHolderClick?.let {
                        it(position, holder)
                    }
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:单选更新
     */
    private fun notifySingleChoice() {
        notifyItemChanged(mCheckPosition)
        notifyItemChanged(mOldCheckPosition)
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:绑定尾
     */
    open fun dataOperationFoot(holder: BaseViewHolder, position: Int) {

    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:绑定头
     */
    open fun dataOperationHead(holder: BaseViewHolder, position: Int) {

    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:数据操作
     */
    abstract fun dataOperation(holder: BaseViewHolder, t: T?, position: Int)

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:设置条目点击事件
     */
    fun setOnItemClickListener(itemClick: (position: Int) -> Unit) {
        mItemClick = itemClick
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:设置条目点击事件
     */
    fun setOnItemClickListener(itemViewClick: (view: View, position: Int) -> Unit) {
        mItemViewClick = itemViewClick
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:多选的最终回调
     */
    fun setOnMultipleChoiceListener(itemMultipleChoiceListener: (list: List<T>) -> Unit) {
        mItemMultipleChoiceListener = itemMultipleChoiceListener
    }


    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:设置条目点击事件 带ViewHolder
     */
    fun setOnItemViewHolderClickListener(itemClick: (position: Int, holder: BaseViewHolder) -> Unit) {
        mItemViewHolderClick = itemClick
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:获取上下文
     */
    fun getContext(): Context? {
        return mContext
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:弹出提示
     */
    private fun toast(msg: String) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
    }


    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:添加空的布局
     */
    fun addEmptyView(layoutId: Int) {
        addEmptyView(View.inflate(mContext, layoutId, null))
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:添加空的View
     */

    fun addEmptyView(view: View) {
        mEmptyView = view
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:添加错误View
     */
    fun addErrorView(view: View) {
        mErrorView = view
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:添加空的布局
     */
    fun addErrorView(layoutId: Int) {
        addErrorView(View.inflate(mContext, layoutId, null))
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:展示错误视图
     */
    fun showErrorView() {
        if (!mIsError) {
            removeViewItemDecoration()
        }
        mIsError = true
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:隐藏错误视图
     */
    fun hintErrorView() {
        if (mIsError) {
            addViewItemDecoration()
        }
        mIsError = false
        notifyDataSetChanged()
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:显示空的布局
     */
    fun showEmptyView() {
        if (!mIsEmpty) {
            //移除线
            removeViewItemDecoration()
        }
        mIsEmpty = true
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:隐藏空的布局
     */
    fun hintEmptyView() {
        if (mIsEmpty) {
            //重新添加线
            addViewItemDecoration()
        }
        mIsEmpty = false
        notifyDataSetChanged()
    }

    private fun removeViewItemDecoration() {
        mItemDecorationAt?.let {
            mRecyclerView?.removeItemDecoration(it)
        }
    }

    private fun addViewItemDecoration() {
        mItemDecorationAt?.let {
            mRecyclerView?.addItemDecoration(it)
        }
    }


    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:绑定helper
     */
    private fun bindItemTouchHelper() {
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            /**
             * 滑动或者拖拽的方向，上下左右
             * @param recyclerView 目标RecyclerView
             * @param viewHolder 目标ViewHolder
             * @return 方向
             */
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {

                val list = getList()
                val position = viewHolder.adapterPosition
                val bean = list[position]
                //禁止拖拽
                if (bean is BaseNoDragBean && !bean.isDrag) {
                    return 0
                }
                //禁止哪些拖拽
                if (mNoDragArray != null && mNoDragArray?.contains(viewHolder.adapterPosition)!!) {
                    return 0
                }
                //设置拖拽方向
                return makeMovementFlags(mDragDirection, mSlideDirection)
            }


            /**
             * 拖拽item移动时产生回调
             * @param recyclerView 目标RecyclerView
             * @param viewHolder 拖拽的item对应的viewHolder
             * @param target 拖拽目的地的ViewHOlder
             * @return 是否消费拖拽事件
             */
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                //得到当拖拽的viewHolder的Position
                val fromPosition = viewHolder.adapterPosition
                val toPosition = target.adapterPosition

                //处理拖拽事件
                if (mList.isEmpty()) {
                    return false
                }
                return if (fromPosition >= 0 && fromPosition < mList.size
                    && toPosition >= 0 && toPosition < mList.size
                ) {
                    //交换数据位置
                    Collections.swap(mList, fromPosition, toPosition)

                    notifyItemMoved(fromPosition, toPosition)

                    true
                } else {
                    false
                }
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //侧滑删除
                val position = viewHolder.adapterPosition
                notifyItemRemoved(position)
                mList.removeAt(position)
            }

            /**
             * AUTHOR:AbnerMing
             * INTRODUCE:拖拽是否可用
             */
            override fun isLongPressDragEnabled(): Boolean {
                return mIsDrag
            }

            /**
             * AUTHOR:AbnerMing
             * INTRODUCE:是否可以滑动删除
             */
            override fun isItemViewSwipeEnabled(): Boolean {
                return mIsSlideDelete
            }

        })


        itemTouchHelper.attachToRecyclerView(mRecyclerView)
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:是否支持拖拽
     */
    private var mIsDrag = false

    fun isDrag() {
        mIsDrag = true
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:哪些不能拖动
     */
    private var mNoDragArray: IntArray? = null
    fun setRecyclerNoDrag(dragArray: IntArray) {
        mNoDragArray = dragArray
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:是否支持滑动删除
     */
    private var mIsSlideDelete = false
    fun isSlideDelete() {
        mIsSlideDelete = true
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:滑动删除方向，默认只有左边
     */
    private var mSlideDirection = ItemTouchHelper.LEFT

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:设置滑动为左右方向
     */
    fun setSlideLeftOrRight() {
        mSlideDirection = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:单独设置方向
     */
    fun setSlideDirection(direction: Int) {
        mSlideDirection = direction
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:设置拖拽方向，默认上下左右
     */
    private var mDragDirection =
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT or ItemTouchHelper.UP or ItemTouchHelper.DOWN

    fun setDragDirection(direction: Int) {
        mDragDirection = direction
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:设置拖拽为上下方向
     */
    fun setDragUpOrDown() {
        mDragDirection = ItemTouchHelper.UP or ItemTouchHelper.DOWN
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:是否是侧滑按钮的形式删除
     */
    open fun getIsSlideDelete(): Boolean {
        return false
    }

}
