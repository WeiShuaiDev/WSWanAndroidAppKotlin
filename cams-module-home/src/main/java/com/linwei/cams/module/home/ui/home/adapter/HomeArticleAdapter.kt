package com.linwei.cams.module.home.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.linwei.cams.component.weight.shadow.ShadowLayout
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data: CommonArticleBean = list[position]
        holder.apply {
            data.let {
                homeTitleView.text = it.shareUser ?: it.author
                homeContentView.text = it.title
                homeChapterView.text = String.format(
                    "%s·%s", it.superChapterName, it.chapterName
                )
                homeTimeView.text = it.niceDate
                homeTagView.visibility = if (it.fresh) View.VISIBLE else View.GONE
                homeTopView.visibility = if (position == 0 && hasTop) View.VISIBLE else View.GONE

                homeShineButtonView.isChecked = it.collect
                homeShineButtonView.setOnCheckStateChangeListener { view, checked ->
                    onArticleCollectListener?.onCollect(it)
                    it.collect = !it.collect
                    notifyItemChanged(position)
                }

                homeShadowLayout.setOnClickListener { view ->
                    //跳转到详情页面
                }
            }
        }
    }

    override fun getItemCount(): Int = list.size

    override fun onCreateLayoutHelper(): LayoutHelper = LinearLayoutHelper()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val homeShadowLayout: ShadowLayout = view.findViewById(R.id.homeShadowLayout)
        val homeTopView: View = view.findViewById(R.id.homeTopView)
        val homeTagView: TextView = view.findViewById(R.id.homeTagView)
        val homeTitleView: TextView = view.findViewById(R.id.homeTitleView)
        val homeTimeView: TextView = view.findViewById(R.id.homeTimeView)
        val homeContentView: TextView = view.findViewById(R.id.homeContentView)
        val homeChapterView: TextView = view.findViewById(R.id.homeChapterView)
        val homeShineButtonView: ShineButton = view.findViewById(R.id.homeShineButtonView)
    }

    interface OnArticleCollectListener {
        fun onCollect(commonarticleBean: CommonArticleBean?)
    }
}