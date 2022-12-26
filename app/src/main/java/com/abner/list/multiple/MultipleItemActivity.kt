package com.abner.list.multiple

import androidx.fragment.app.Fragment
import com.abner.list.R
import com.abner.list.databinding.ActivityPageBinding
import com.abner.list.multiple.fragment.OrdinaryMultipleItemFragment
import com.abner.list.multiple.fragment.SimplifyMultipleItemFragment
import com.vip.base.activity.BaseActivity
import com.vip.base.adapter.BaseFragmentPagerAdapter

/**
 *AUTHOR:AbnerMing
 *DATE:2022/12/23
 *INTRODUCE:多条目
 */
class MultipleItemActivity : BaseActivity<ActivityPageBinding>(R.layout.activity_page) {

    private val mFragments = arrayListOf<Fragment>(
        OrdinaryMultipleItemFragment(),
        SimplifyMultipleItemFragment()
    )

    private var mTitles = arrayListOf("普通多条目", "封装多条目")

    override fun initData() {
        setBarTitle("多条目")

        val adapter =
            BaseFragmentPagerAdapter(supportFragmentManager, mFragments, mTitles)
        mBinding.viewPager.adapter = adapter
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager)
    }

}