package com.linwei.cams.module.mine.ui.mine

import android.annotation.SuppressLint
import android.view.View
import android.widget.RelativeLayout
import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.cache.utils.mmkv.AppDataMMkvProvided
import com.linwei.cams.component.mvvm.base.MvvmBaseFragment
import com.linwei.cams.component.weight.waveview.WaveView
import com.linwei.cams.module.mine.R
import com.linwei.cams.module.mine.databinding.MineFragmentMineBinding
import com.linwei.cams.module.mine.ui.mine.mvvm.view.MineView
import com.linwei.cams.module.mine.ui.mine.mvvm.viewmodel.MineViewModel
import com.linwei.cams.service.base.model.UserInfoBean
import com.linwei.cams.service.mine.MineRouterTable
import dagger.hilt.android.AndroidEntryPoint
import java.lang.String

@AndroidEntryPoint
@Route(path = MineRouterTable.PATH_FRAGMENT_MINE)
class MineFragment : MvvmBaseFragment<MineFragmentMineBinding, MineViewModel>(), MineView {

    override fun hasInjectARouter(): Boolean = true

    override fun getRootLayoutId(): Int = R.layout.mine_fragment_mine

    override fun immersionBar(): Boolean = true

    override fun initView() {
        mDataBinding?.viewModel = mViewModel
    }

    override fun initData() {

    }

    private fun userInfoDataToView() {
        val userInfo: UserInfoBean? = AppDataMMkvProvided().getUserInfo()
        if (userInfo != null) {
            mDataBinding?.mineNameView?.text = userInfo.username
            mViewModel?.requestIntegralData()
        } else {
            mDataBinding?.mineIdView?.text = ""
            mDataBinding?.mineMyScoreView?.text = ""
            mDataBinding?.mineNameView?.setText(R.string.mine_unlogin)
            mDataBinding?.mineLevelView?.setVisibility(View.GONE)
        }
    }

    override fun onResume() {
        super.onResume()
        userInfoDataToView()
    }

    override fun initEvent() {
        val layoutParams = mDataBinding?.mineHeadLayout?.layoutParams as RelativeLayout.LayoutParams
        mDataBinding?.mineWaveView?.setOnWaveAnimationListener(object :
            WaveView.OnWaveAnimationListener {
            override fun OnWaveAnimation(y: Float) {
                layoutParams.setMargins(0, y.toInt(), 0, 0)
                mDataBinding?.mineHeadLayout?.layoutParams = layoutParams
            }
        })
    }

    @SuppressLint("DefaultLocale")
    override fun userInfoDataToView(bean: UserInfoBean) {
        mDataBinding?.mineLevelView?.visibility = View.VISIBLE
        mDataBinding?.mineIdView?.text = String.format("ID: %s", bean.userId)
        mDataBinding?.mineLevelView?.text = String.format("lv.%d", bean.level)
        mDataBinding?.mineMyScoreView?.text = String.format(
            getString(R.string.mine_current_score) + ": %s",
            bean.coinCount
        )
    }
}