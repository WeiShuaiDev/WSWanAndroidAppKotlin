package com.linwei.cams.module.home.ui.home

import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.mvp.base.MvpBaseFragment
import com.linwei.cams.module.home.databinding.HomeFragmentHomeBinding
import com.linwei.cams.module.home.ui.home.mvp.contract.IHomeView
import com.linwei.cams.module.home.ui.home.mvp.presenter.HomePresenter
import com.linwei.cams.service.home.HomeRouterTable
import com.linwei.cams.service.home.model.BannerBean
import com.linwei.cams.service.home.model.HomeBean

@Route(path = HomeRouterTable.PATH_FRAGMENT_HOME)
class HomeFragment : MvpBaseFragment<HomeFragmentHomeBinding, HomePresenter>(), IHomeView {

    @Autowired
    lateinit var title: String

    private var mPage: Int = 0

    override fun hasInjectARouter(): Boolean = true

    override fun initView() {

    }

    override fun initData() {
        mMvpPresenter?.requestHomeData(mPage)
    }

    override fun initEvent() {

    }

    override fun getPresenter(): HomePresenter = HomePresenter(this)

    override fun updateHomeDataToView(homeBean: HomeBean) {
    }

    override fun updateBannerDataToView(bannerList: List<BannerBean>) {

    }

    override fun refreshCollectStatus(status: Boolean) {

    }
}