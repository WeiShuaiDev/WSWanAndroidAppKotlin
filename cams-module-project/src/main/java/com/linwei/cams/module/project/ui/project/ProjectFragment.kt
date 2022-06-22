package com.linwei.cams.module.project.ui.project

import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.framework.mvi.base.MviBaseFragment
import com.linwei.cams.module.project.databinding.ProjectFragmentProjectBinding
import com.linwei.cams.module.project.ui.project.mvi.intent.ProjectViewModel
import com.linwei.cams.module.project.ui.project.mvi.view.ProjectView
import com.linwei.cams.service.project.ProjectRouterTable
import com.linwei.cams.service.project.model.ProjectTreeBean
import com.linwei.cams.service.project.model.ProjectTreeDetailsBean
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = ProjectRouterTable.PATH_FRAGMENT_PROJECT)
class ProjectFragment : MviBaseFragment<ProjectFragmentProjectBinding, ProjectViewModel>(), ProjectView {

    @Autowired
    lateinit var title: String

    override fun hasInjectARouter(): Boolean = true

    override fun initView() {

    }

    override fun initData() {

        mViewModel?.fetchProjectTreeData()

        mViewModel?.fetchProjectTreeDetailsData()
    }

    override fun initEvent() {

    }

    override fun projectTreeDataToView(data: List<ProjectTreeBean>?) {
        mViewBinding.projectContentTv.text = data.toString()
    }

    override fun projectTreeDetailsDataToView(data: List<ProjectTreeDetailsBean>?) {

    }
}