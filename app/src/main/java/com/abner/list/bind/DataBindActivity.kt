package com.abner.list.bind

import androidx.fragment.app.Fragment
import com.abner.list.R
import com.abner.list.databinding.ActivityPageBinding
import com.abner.list.ordinary.fragment.OrdinaryVerticalFragment
import com.vip.base.activity.BaseActivity
import com.vip.base.adapter.BaseFragmentPagerAdapter

/**
 *AUTHOR:AbnerMing
 *DATE:2022/12/6
 *INTRODUCE:DataBinding列表形式
 */
class DataBindActivity : BaseActivity<ActivityPageBinding>(R.layout.activity_page) {
    private val mFragments = arrayListOf<Fragment>(
        ItemBindFragment(), RecyclerViewBindFragment()
    )

    private var mTitles = arrayListOf("item绑定", "RecyclerView绑定")

    override fun initData() {
        setBarTitle("DataBinding列表")

        val adapter =
            BaseFragmentPagerAdapter(supportFragmentManager, mFragments, mTitles)
        mBinding.viewPager.adapter = adapter
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager)

    }


}