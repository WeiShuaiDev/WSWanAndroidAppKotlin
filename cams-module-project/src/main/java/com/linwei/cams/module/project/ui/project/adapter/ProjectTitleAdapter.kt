package com.linwei.cams.module.project.ui.project.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.linwei.cams.module.project.R
import com.linwei.cams.service.project.model.ProjectTreeBean

class ProjectTitleAdapter(data: MutableList<ProjectTreeBean>) : BaseQuickAdapter<ProjectTreeBean,
        BaseViewHolder>(R.layout.project_item_title, data) {

    override fun convert(holder: BaseViewHolder, item: ProjectTreeBean) {
        holder.setText(R.id.projectTitleView,item.name)

    }
}