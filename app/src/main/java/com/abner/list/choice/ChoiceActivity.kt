package com.abner.list.choice

import androidx.fragment.app.Fragment
import com.abner.list.R
import com.abner.list.choice.multiple.MultipleFragment
import com.abner.list.choice.single.SingleFragment
import com.abner.list.databinding.ActivityPageBinding
import com.vip.base.activity.BaseActivity
import com.vip.base.adapter.BaseFragmentPagerAdapter

/**
 *AUTHOR:AbnerMing
 *DATE:2022/12/9
 *INTRODUCE:单选、多选、全选、反选状态
 */
class ChoiceActivity : BaseActivity<ActivityPageBinding>(R.layout.activity_page) {

    private val mFragments = arrayListOf<Fragment>(
        SingleFragment(), MultipleFragment()
    )

    private var mTitles = arrayListOf("单选", "多选")

    override fun initData() {
        setBarTitle("单、多、全、反选状态")
        val adapter =
            BaseFragmentPagerAdapter(supportFragmentManager, mFragments, mTitles)
        mBinding.viewPager.adapter = adapter
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager)
    }

}