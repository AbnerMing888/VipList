package com.abner.list.stick

import com.abner.list.BR
import com.abner.list.R
import com.abner.list.databinding.LayoutListBinding
import com.vip.base.activity.BaseActivity
import com.vip.list.util.linear
import com.vip.list.util.set
import com.vip.list.util.stick
import org.json.JSONObject


/**
 *AUTHOR:AbnerMing
 *DATE:2022/11/19
 *INTRODUCE:吸顶效果
 */
class StickHeaderActivity : BaseActivity<LayoutListBinding>(R.layout.layout_list) {
    override fun initData() {

        setBarTitle("列表吸顶")
        mBinding.recycler.linear()
            .stick()
            .set<StickHeaderBean> {
                addLayout(R.layout.layout_stick_item, BR.stick)
            }.setList(getList())
    }

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:模拟的json
     */
    private val mStickJson =
        "{\"code\":0,\"message\":\"返回成功\",\"items\":[{\"groupName\":\"电脑\",\"groupItems\":[{\"name\":\"苹果\",\"price\":10000},{\"name\":\"华为\",\"price\":11000},{\"name\":\"小米\",\"price\":12000},{\"name\":\"惠普\",\"price\":13000},{\"name\":\"联想\",\"price\":13100},{\"name\":\"戴尔\",\"price\":13700}]},{\"groupName\":\"手机\",\"groupItems\":[{\"name\":\"小米\",\"price\":3000},{\"name\":\"红米\",\"price\":1000},{\"name\":\"荣耀\",\"price\":3000},{\"name\":\"VIVO\",\"price\":2300},{\"name\":\"老人机\",\"price\":300},{\"name\":\"苹果\",\"price\":7300}]},{\"groupName\":\"汽车\",\"groupItems\":[{\"name\":\"欧拉\",\"price\":100000},{\"name\":\"长城炮\",\"price\":211000},{\"name\":\"特斯拉\",\"price\":311000},{\"name\":\"奔驰\",\"price\":451000},{\"name\":\"宝马\",\"price\":451000},{\"name\":\"马自达\",\"price\":451000}]},{\"groupName\":\"运动\",\"groupItems\":[{\"name\":\"跑步\",\"price\":3000},{\"name\":\"跳绳\",\"price\":1000},{\"name\":\"足球\",\"price\":3000},{\"name\":\"篮球\",\"price\":2300},{\"name\":\"排球\",\"price\":300},{\"name\":\"乒乓球\",\"price\":7300}]}]}"

    /**
     * AUTHOR:AbnerMing
     * INTRODUCE:模拟数据
     */
    private fun getList(): MutableList<StickHeaderBean> {

        return mutableListOf<StickHeaderBean>().apply {
            //解析json
            val json = JSONObject(mStickJson)
            val code = json.getInt("code")
            if (code == 0) {
                val jsonArray = json.getJSONArray("items")//获取数组
                for (a in 0 until jsonArray.length()) {
                    val obj = jsonArray.getJSONObject(a)
                    val groupName = obj.getString("groupName")//最外层的分组名字
                    val groupItems = obj.getJSONArray("groupItems")//每个分组下的数据

                    for (b in 0 until groupItems.length()) {
                        val childObject = groupItems.getJSONObject(b)
                        val name = childObject.getString("name")
                        val bean = StickHeaderBean()
                        bean.stickGroup = groupName
                        bean.name = name
                        add(bean)
                    }
                }
            }
        }
    }
}