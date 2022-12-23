package com.abner.list.ordinary

import androidx.fragment.app.Fragment
import com.abner.list.R
import com.abner.list.databinding.ActivityPageBinding
import com.abner.list.ordinary.fragment.OrdinaryGridFragment
import com.abner.list.ordinary.fragment.OrdinaryHorizontalFragment
import com.abner.list.ordinary.fragment.OrdinaryStaggeredFragment
import com.abner.list.ordinary.fragment.OrdinaryVerticalFragment
import com.vip.base.activity.BaseActivity
import com.vip.base.adapter.BaseFragmentPagerAdapter

/**
 *AUTHOR:AbnerMing
 *DATE:2022/11/18
 *INTRODUCE:普通的列表调用方式，采取的是RecyclerView+Adapter的方式
 */
class OrdinaryListActivity : BaseActivity<ActivityPageBinding>(R.layout.activity_page) {

    private val mFragments = arrayListOf<Fragment>(
        OrdinaryVerticalFragment(), OrdinaryHorizontalFragment(),
        OrdinaryGridFragment(), OrdinaryStaggeredFragment()
    )

    private var mTitles = arrayListOf("纵向列表", "横向列表", "网格列表", "交错列表")

    override fun initData() {
        setBarTitle("普通列表")

        val adapter =
            BaseFragmentPagerAdapter(supportFragmentManager, mFragments, mTitles)
        mBinding.viewPager.adapter = adapter
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager)
    }
}