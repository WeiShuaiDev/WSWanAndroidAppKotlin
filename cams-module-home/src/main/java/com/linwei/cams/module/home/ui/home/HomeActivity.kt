package com.linwei.cams.module.home.ui.home

import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.common.base.CommonBaseActivity
import com.linwei.cams.component.common.ktx.getExtra
import com.linwei.cams.component.common.ktx.launch
import com.linwei.cams.component.common.ktx.startActivityForResult
import com.linwei.cams.component.mvp.base.MvpBaseActivity
import com.linwei.cams.module.home.databinding.HomeActivityHomeBinding
import com.linwei.cams.module.home.ui.HomeDetailsActivity
import com.linwei.cams.module.home.ui.home.mvp.contract.IHomeView
import com.linwei.cams.module.home.ui.home.mvp.presenter.HomePresenter
import com.linwei.cams.service.home.HomeRouterTable
import com.linwei.cams.service.home.model.BannerBean
import com.linwei.cams.service.home.model.HomeBean

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