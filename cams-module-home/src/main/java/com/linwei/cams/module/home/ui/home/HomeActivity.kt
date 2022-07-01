package com.linwei.cams.module.home.ui.home

import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.common.base.CommonBaseActivity
import com.linwei.cams.module.home.databinding.HomeActivityHomeBinding
import com.linwei.cams.service.home.HomeRouterTable

@Route(path = HomeRouterTable.PATH_ACTIVITY_HOME)
class HomeActivity : CommonBaseActivity<HomeActivityHomeBinding>() {

    @Autowired
    lateinit var title: String

    override fun hasInjectARouter(): Boolean = true


    override fun initEvent() {

    }

    override fun initData() {
    }

    override fun initView() {
        //val activityResultLauncher= startActivityForResult{
        //     val title= it.data?.getExtra("title", "没有标题") as String
        //     mViewBinding.homeTitleTv.text=title
        // }
        // mViewBinding.homeStartBtn.setOnClickListener {
        //     activityResultLauncher.launch(mContext,HomeDetailsActivity::class.java,"name","HomeActivity")
        // }
    }
}