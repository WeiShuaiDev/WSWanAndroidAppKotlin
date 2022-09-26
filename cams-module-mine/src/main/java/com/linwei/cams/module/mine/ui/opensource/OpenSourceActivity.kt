package com.linwei.cams.module.mine.ui.opensource

import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.common.base.CommonBaseActivity
import com.linwei.cams.component.common.ktx.idToString
import com.linwei.cams.component.weight.CustomItemDecoration
import com.linwei.cams.module.mine.R
import com.linwei.cams.module.mine.databinding.MineActivityOpenSourceBinding
import com.linwei.cams.module.mine.model.OpenSourceBean
import com.linwei.cams.module.mine.ui.opensource.adapter.OpenSourceListAdapter
import com.linwei.cams.module.mine.utils.DataUtils
import com.linwei.cams.service.mine.MineRouterTable

@Route(path = MineRouterTable.PATH_ACTIVITY_OPEN_SOURCE)
class OpenSourceActivity : CommonBaseActivity<MineActivityOpenSourceBinding>() {

    override fun hasInjectARouter(): Boolean = true

    private val mOpenSourceList: MutableList<OpenSourceBean> = DataUtils.getOpenSourceList()

    override fun initView() {
        mViewBinding?.topRootLayout?.let {
            it.titleView.text = R.string.mine_open_source_project.idToString(mContext)
            it.leftTitleView.setOnClickListener {
                this.finish()
            }
        }

        val openSourceListAdapter = OpenSourceListAdapter()
        mViewBinding?.mineRecyclerView?.apply {
            addItemDecoration(
                CustomItemDecoration(
                    mContext,
                    CustomItemDecoration.ItemDecorationDirection.VERTICAL_LIST,
                    R.drawable.linear_split_line
                )
            )
            adapter = openSourceListAdapter
        }
        openSourceListAdapter.setList(mOpenSourceList)
    }

    override fun initData() {

    }

    override fun initEvent() {

    }

}