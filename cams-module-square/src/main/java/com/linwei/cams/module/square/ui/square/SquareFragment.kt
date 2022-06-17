package com.linwei.cams.module.square.ui.square

import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.framework.mvi.base.MviBaseFragment
import com.linwei.cams.module.square.databinding.SquareFragmentSquareBinding
import com.linwei.cams.module.square.ui.square.mvi.intent.SquareViewModel
import com.linwei.cams.module.square.ui.square.mvi.view.SquareView
import com.linwei.cams.service.square.SquareRouterTable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = SquareRouterTable.PATH_FRAGMENT_SQUARE)
class SquareFragment : MviBaseFragment<SquareFragmentSquareBinding, SquareViewModel>(), SquareView {

    override fun initView() {
    }

    override fun initData() {
    }

    override fun initEvent() {
    }

}