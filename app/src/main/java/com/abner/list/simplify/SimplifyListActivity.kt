package com.abner.list.simplify

import androidx.fragment.app.Fragment
import com.abner.list.R
import com.abner.list.databinding.ActivityPageBinding
import com.abner.list.simplify.fragment.SimplifyGridFragment
import com.abner.list.simplify.fragment.SimplifyHorizontalFragment
import com.abner.list.simplify.fragment.SimplifyStaggeredFragment
import com.abner.list.simplify.fragment.SimplifyVerticalFragment
import com.vip.base.activity.BaseActivity
import com.vip.base.adapter.BaseFragmentPagerAdapter

/**
 *AUTHOR:AbnerMing
 *DATE:2022/12/6
 *INTRODUCE:简化封装之后的列表展示，采用无适配器模式
 */
class SimplifyListActivity : BaseActivity<ActivityPageBinding>(R.layout.activity_page) {
    private val mFragments = arrayListOf<Fragment>(
        SimplifyVerticalFragment(), SimplifyHorizontalFragment(),
        SimplifyGridFragment(), SimplifyStaggeredFragment()
    )
    private var mTitles = arrayListOf("纵向列表", "横向列表", "网格列表", "交错列表")

    override fun initData() {

        setBarTitle("封装列表")

        val adapter =
            BaseFragmentPagerAdapter(supportFragmentManager, mFragments, mTitles)
        mBinding.viewPager.adapter = adapter
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager)
    }

}