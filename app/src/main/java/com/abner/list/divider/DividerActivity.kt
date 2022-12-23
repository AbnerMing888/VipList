package com.abner.list.divider

import androidx.fragment.app.Fragment
import com.abner.list.R
import com.abner.list.databinding.ActivityPageBinding
import com.abner.list.divider.fragment.DividerGridFragment
import com.abner.list.divider.fragment.DividerHorizontalFragment
import com.abner.list.divider.fragment.DividerVerticalFragment
import com.vip.base.activity.BaseActivity
import com.vip.base.adapter.BaseFragmentPagerAdapter

/**
 *AUTHOR:AbnerMing
 *DATE:2022/12/6
 *INTRODUCE:分割线
 */
class DividerActivity : BaseActivity<ActivityPageBinding>(R.layout.activity_page) {

    private val mFragments = arrayListOf<Fragment>(
        DividerVerticalFragment(), DividerHorizontalFragment(), DividerGridFragment()
    )

    private var mTitles = arrayListOf("纵向分割线", "横向分割线", "网格分割线")

    override fun initData() {
        setBarTitle("分割线")
        val adapter =
            BaseFragmentPagerAdapter(supportFragmentManager, mFragments, mTitles)
        mBinding.viewPager.adapter = adapter
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager)
    }


}