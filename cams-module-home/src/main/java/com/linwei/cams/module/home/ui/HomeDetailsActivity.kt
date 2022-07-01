package com.linwei.cams.module.home.ui

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.linwei.cams.component.common.base.CommonBaseApplication
import com.linwei.cams.component.common.ktx.getExtra
import com.linwei.cams.component.common.ktx.requestPermission
import com.linwei.cams.component.common.ktx.setResult
import com.linwei.cams.component.mvp.base.MvpBaseActivity
import com.linwei.cams.component.mvp.mvp.model.IMvpModel
import com.linwei.cams.component.mvp.mvp.presenter.MvpPresenter
import com.linwei.cams.component.mvp.mvp.view.IMvpView
import com.linwei.cams.module.home.databinding.HomeActivityHomeDetailsBinding
import com.linwei.tool.XToolReporter
import com.linwei.tool.ui.crash.CrashReporterActivity
import com.linwei.tool.ui.network.NetworkReporterActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeDetailsActivity :
    MvpBaseActivity<HomeActivityHomeDetailsBinding, MvpPresenter<IMvpView, IMvpModel>>(), IMvpView {

    override fun hasInjectARouter(): Boolean = true

    override fun initView() {
        val name = intent.getExtra("name", "没有名称") as String
        mViewBinding.homeTitleTv.text = name
    }

    override fun initData() {
        requestPermission(
            success = {
                showToast("请求成功！")
            }, failed = {
                showToast("请求失败！")
            }).launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }


    override fun initEvent() {

        mViewBinding.homeStartBtn.setOnClickListener {
            setResult("title", "HomeDetailsActivity")
            finish()
        }
        mViewBinding.homeStartBtn.setOnClickListener {
            XToolReporter.disableAndzu()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(mContext)) {
                //If the draw over permission is not available open the settings screen
                //to grant the permission.
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:com.linwei.camsmodular")
                )
                startActivityForResult(intent, 1)
            } else {
                XToolReporter.initBubbles(CommonBaseApplication.application)
            }
        }

        mViewBinding.homeCrashBtn.setOnClickListener {
            startActivity(Intent(mContext, CrashReporterActivity::class.java))
        }

        mViewBinding.homeNetworkBtn.setOnClickListener {
            startActivity(Intent(mContext, NetworkReporterActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1) {
            if (resultCode == AppCompatActivity.RESULT_OK) {
                XToolReporter.initBubbles(CommonBaseApplication.application)
            } else { //Permission is not available
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            XToolReporter.recycle()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getPresenter(): MvpPresenter<IMvpView, IMvpModel>? = null
}