package com.linwei.camsmodular.ui.welcome

import com.linwei.cams.component.cache.utils.mmkv.AppDataProvided
import com.linwei.cams.framework.mvi.base.MviBaseActivity
import com.linwei.cams.module.main.provider.MainProviderHelper
import com.linwei.cams.service.project.model.ProjectTreeBean
import com.linwei.camsmodular.databinding.ActivityWelcomeBinding
import com.linwei.camsmodular.ui.welcome.mvi.intent.WelcomeViewModel
import com.linwei.camsmodular.ui.welcome.mvi.view.WelcomeView
import java.util.*


class WelcomeActivity : MviBaseActivity<ActivityWelcomeBinding, WelcomeViewModel>(),
    WelcomeView {

    private var mTimer: Timer? = null

    override fun initView() {

    }

    override fun initData() {
        mViewModel?.fetchProjectTreeData()
    }

    override fun initEvent() {
        mTimer = Timer().apply {
            schedule(object : TimerTask() {
                override fun run() {
                    MainProviderHelper.jumpMainActivity()
                    finish()
                }
            }, 500)
        }
    }

    override fun projectTreeDataToView(data: List<ProjectTreeBean>?) {
        //projectTreeBean保存数据到MMkv
        AppDataProvided().saveProjectTree(data)
        System.out.println("获取数据成功")
    }

    override fun onDestroy() {
        super.onDestroy()
        mTimer?.cancel()
    }
}