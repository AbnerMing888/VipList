package com.abner.list.refresh

import com.abner.list.BR
import com.abner.list.R
import com.abner.list.databinding.ActivityRefreshBinding
import com.vip.base.activity.BaseVMActivity
import com.vip.list.util.divider
import com.vip.list.util.linear
import com.vip.list.util.set

/**
 *AUTHOR:AbnerMing
 *DATE:2022/12/22
 *INTRODUCE:上拉和下拉
 */
class RefreshListActivity : BaseVMActivity<ActivityRefreshBinding,
        RefreshListViewModel>(R.layout.activity_refresh) {

    override fun initVMData() {
        setBarTitle("刷新加载")
        mBinding.refresh.getRecycler().linear()
            .divider()
            .set<String> {
                addLayout(R.layout.layout_item, BR.str)
            }
        //刷新和加载
        mBinding.refresh.refresh { isRefresh, refreshLayout ->
            mViewModel.doHttp {
                addData(it)
            }
        }.refresh()
    }
}