package com.linwei.camsmodular.ui.welcome

import com.linwei.cams.framework.mvi.base.MviBaseActivity
import com.linwei.cams.service.project.model.ProjectTreeBean
import com.linwei.camsmodular.databinding.ActivityWelcomeBinding
import com.linwei.camsmodular.ui.welcome.mvi.intent.WelcomeViewModel
import com.linwei.camsmodular.ui.welcome.mvi.view.WelcomeView

class WelcomeActivity : MviBaseActivity<ActivityWelcomeBinding, WelcomeViewModel>(),
    WelcomeView {

    override fun initView() {

    }

    override fun initData() {
        mViewModel?.fetchProjectTreeData()
    }

    override fun initEvent() {

    }

    override fun projectTreeDataToView(data: List<ProjectTreeBean>?) {
        System.out.println("成功返回的数据${data}")
    }
}