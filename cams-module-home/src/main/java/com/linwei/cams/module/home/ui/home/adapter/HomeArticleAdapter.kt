package com.linwei.cams.module.home.ui.home.adapter

import android.annotation.SuppressLint
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.linwei.cams.component.common.ktx.notNullAndEmpty
import com.linwei.cams.component.web.ui.CommonWebActivity
import com.linwei.cams.component.weight.shinebutton.ShineButton
import com.linwei.cams.module.home.R
import com.linwei.cams.service.base.model.CommonArticleBean

class HomeArticleAdapter(
    private var list: List<CommonArticleBean>,
    private var hasTop: Boolean = false,
    private var onArticleCollectListener: OnArticleCollectListener?
) : DelegateAdapter.Adapter<HomeArticleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.home_item_article, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data: CommonArticleBean = list[position]
        holder.apply {
            data.let {
                val superChapterName: String? = it.superChapterName
                val chapterName: String? = it.chapterName
                homeChapterView.text =
                    " ${if (TextUtils.isEmpty(it.author)) it.shareUser else it.author} / ${
                        if (TextUtils.isEmpty(superChapterName)) chapterName else String.format(
                            "%s·%s", superChapterName, chapterName
                        )
                    }"
                homeTagView.visibility = if (it.fresh) View.VISIBLE else View.GONE
                homeContentView.text = it.title
                homeTimeView.text = it.niceDate
                homeTopView.visibility = if (position == 0 && hasTop) View.VISIBLE else View.GONE
                homeShineButtonView.setOnCheckStateChangeListener { view, checked ->
                    onArticleCollectListener?.onCollect(it)
                    it.collect = !it.collect
                    notifyItemChanged(position)
                }

                itemView.setOnClickListener { view ->
                    //跳转到详情页面
                    if (it.link.notNullAndEmpty()) {
                        CommonWebActivity.start(it.link!!)
                    }
                }
                homeShineButtonView.isChecked = it.collect
            }
        }
    }

    override fun getItemCount(): Int = list.size

    override fun onCreateLayoutHelper(): LayoutHelper = LinearLayoutHelper()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val homeTopView: View = view.findViewById(R.id.homeTopView)
        val homeTagView: TextView = view.findViewById(R.id.homeTagView)
        val homeTimeView: TextView = view.findViewById(R.id.homeTimeView)
        val homeContentView: TextView = view.findViewById(R.id.homeContentView)
        val homeChapterView: TextView = view.findViewById(R.id.homeChapterView)
        val homeShineButtonView: ShineButton = view.findViewById(R.id.homeShineButtonView)
    }

    interface OnArticleCollectListener {
        fun onCollect(commonArticleBean: CommonArticleBean?)
    }
}