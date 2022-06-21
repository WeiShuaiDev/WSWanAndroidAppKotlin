package com.linwei.cams.module.mine.ui.mine

import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.common.base.CommonBaseActivity
import com.linwei.cams.component.mvvm.base.MvvmBaseFragment
import com.linwei.cams.module.mine.R
import com.linwei.cams.module.mine.databinding.MineActivityMineBinding
import com.linwei.cams.module.mine.databinding.MineFragmentMineBinding
import com.linwei.cams.module.mine.ui.mine.mvvm.view.MineView
import com.linwei.cams.module.mine.ui.mine.mvvm.viewmodel.MineViewModel
import com.linwei.cams.service.mine.MineRouterTable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = MineRouterTable.PATH_FRAGMENT_MINE)
class MineFragment: MvvmBaseFragment<MineFragmentMineBinding, MineViewModel>(),
MineView{

    override fun getRootLayoutId(): Int = R.layout.mine_fragment_mine

    override fun hasInjectARouter(): Boolean = true

    override fun initView() {

    }

    override fun initData() {

    }

    override fun initEvent() {

    }

}