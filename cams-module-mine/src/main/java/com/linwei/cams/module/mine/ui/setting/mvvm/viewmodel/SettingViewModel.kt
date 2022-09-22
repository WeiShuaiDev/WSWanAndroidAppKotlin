package com.linwei.cams.module.mine.ui.setting.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.kongzue.dialogx.dialogs.MessageDialog
import com.kongzue.dialogx.dialogs.PopTip
import com.linwei.cams.component.cache.mmkv.AppDataMMkvProvided
import com.linwei.cams.component.cache.utils.CacheUtils
import com.linwei.cams.component.mvvm.ktx.asLiveData
import com.linwei.cams.component.mvvm.mvvm.viewmodel.MvvmViewModel
import com.linwei.cams.module.mine.R
import com.linwei.cams.module.mine.ui.setting.mvvm.model.SettingModel
import com.linwei.cams.service.base.ErrorMessage
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.mine.provider.MineProviderHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val settingModel: SettingModel) :
    MvvmViewModel() {

    protected val _chcheSize: MutableLiveData<String> =
        MutableLiveData<String>()
    val chcheSize = _chcheSize.asLiveData()

    private fun requestLogOut() {
        settingModel.logout(object : ResponseCallback<Any> {
            override fun onSuccess(data: Any) {
                AppDataMMkvProvided().logout()
            }

            override fun onFailed(errorMessage: ErrorMessage) {
                _showToast.postValue(errorMessage.message)
            }
        })
    }

    /**
     * 语言设置
     */
    fun onLanguageClick() {
        MineProviderHelper.jumpLanguageActivity()
    }

    /**
     * 清除缓存
     */
    fun onCacheClick() {
        MessageDialog.show(R.string.mine_tip, R.string.mine_clear_memory, R.string.mine_sure, R.string.cancel)
            .setOkButton { baseDialog, v ->
                clearCacheFiles()
                return@setOkButton false
            }

    }

    /**
     * 当前版本
     */
    fun onVersionClick() {
        PopTip.show(R.string.mine_version_tip);
    }

    /**
     * 版权声明
     */
    fun onDescClick() {
        MessageDialog.show(R.string.mine_tip, R.string.mine_desc_tip, R.string.mine_sure, R.string.cancel)
            .setOkButton { baseDialog, v ->
                return@setOkButton false
            }
    }

    /**
     * 关于我们
     */
    fun onAboutClick() {
        //跳转到关于我们
    }

    /**
     * 退出登录
     */
    fun onExitClick() {
        MessageDialog.show(R.string.mine_tip, R.string.mine_sure_logout, R.string.mine_sure, R.string.cancel)
            .setOkButton { baseDialog, v ->
                requestLogOut()
                return@setOkButton false
            }
    }

    /**
     * 获取缓存文件大小
     */
    fun getCacheFileSize() {
        Observable.create<String> { emitter ->
            val totalCacheSize: String = CacheUtils.getTotalCacheSize()
            if (!emitter.isDisposed) {
                emitter.onNext(totalCacheSize)
            }
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<String> {
                override fun onSubscribe(d: Disposable) {}

                override fun onNext(t: String) {
                    _chcheSize.postValue(t)
                }

                override fun onError(e: Throwable) {}
                override fun onComplete() {}
            })
    }

    /**
     * 清除缓存文件
     */
    private fun clearCacheFiles() {
        Observable.create<String> { emitter ->
            CacheUtils.clearAllCache()
            val totalCacheSize: String = CacheUtils.getTotalCacheSize()
            if (!emitter.isDisposed) {
                emitter.onNext(totalCacheSize)
            }
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<String> {
                override fun onSubscribe(d: Disposable) {
                    addDisposable(d)
                }

                override fun onNext(t: String) {
                    _chcheSize.postValue(t)
                }

                override fun onError(e: Throwable) {}
                override fun onComplete() {}
            })
    }
}