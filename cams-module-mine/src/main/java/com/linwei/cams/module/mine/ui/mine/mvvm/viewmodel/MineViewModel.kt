package com.linwei.cams.module.mine.ui.mine.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.linwei.cams.component.cache.mmkv.AppDataMMkvProvided
import com.linwei.cams.component.common.global.ConstantParams
import com.linwei.cams.component.mvvm.ktx.asLiveData
import com.linwei.cams.component.mvvm.mvvm.viewmodel.MvvmViewModel
import com.linwei.cams.module.mine.ui.mine.mvvm.model.MineModel
import com.linwei.cams.service.base.ErrorMessage
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.base.model.UserInfoBean
import com.linwei.cams.service.mine.provider.MineProviderHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MineViewModel @Inject constructor(private val mineModel: MineModel) : MvvmViewModel() {

    private val _UserInfoBean: MutableLiveData<UserInfoBean> =
        MutableLiveData<UserInfoBean>()
    val userInfoBean = _UserInfoBean.asLiveData()

    fun requestIntegralData() {
        mineModel.fetchIntegralData(object : ResponseCallback<UserInfoBean> {

            override fun onSuccess(data: UserInfoBean) {
                AppDataMMkvProvided().saveUserInfo(data)
                _UserInfoBean.postValue(data)
            }

            override fun onFailed(errorMessage: ErrorMessage) {
                _showToast.postValue(errorMessage.message)
            }
        })
    }

    fun onSettingClick() {
        onItemClick(-1)
    }

    fun onScoreRankClick() {
        MineProviderHelper.jumpScoreRankListActivity()
    }

    fun onItemClick(index: Int) {
        when (index) {
            ConstantParams.CONSTANT_STARTS_0 -> {
                //我的积分
                MineProviderHelper.jumpMyScoreActivity()
            }
            ConstantParams.CONSTANT_STARTS_1 -> {
                //我的收藏
                MineProviderHelper.jumpMyCollectActivity()
            }
            ConstantParams.CONSTANT_STARTS_2 -> {
                //我的分享
                MineProviderHelper.jumpMyShareActivity()
            }
            ConstantParams.CONSTANT_STARTS_3 -> {
                //开源项目
                MineProviderHelper.jumpOpenSourceActivity()
            }
            ConstantParams.CONSTANT_STARTS_4 -> {
                //关于作者
                MineProviderHelper.jumpAboutAuthorActivity()
            }
            else -> {
                //设置
                MineProviderHelper.jumpSettingActivity()
            }
        }
    }

}