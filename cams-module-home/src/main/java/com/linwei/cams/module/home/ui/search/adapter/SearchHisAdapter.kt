package com.linwei.cams.module.home.ui.search.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.linwei.cams.component.common.ktx.isNotNullOrSize
import com.linwei.cams.component.database.entity.SearchHistoryEntity
import com.linwei.cams.module.home.R

class SearchHisAdapter(private var list: MutableList<SearchHistoryEntity>) :
    DelegateAdapter.Adapter<BaseViewHolder>() {

    override fun onCreateLayoutHelper(): LayoutHelper = LinearLayoutHelper()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.home_item_search_his, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val data: SearchHistoryEntity = list[position]
        holder.apply {
            data.let { d ->
                setText(R.id.homeTitleView, d.name)

                getView<ImageView>(R.id.homeClearView).setOnClickListener {
                    mOnSearchHisCallBack?.onItemDelete(d.id)
                    remove(position)
                }

                itemView.setOnClickListener {
                    mOnSearchHisCallBack?.onItemClick(d.name)
                }

            }
        }
    }

    override fun getItemCount(): Int = list.size

    @SuppressLint("NotifyDataSetChanged")
    fun refresh(searchHistoryList: List<SearchHistoryEntity>) {
        if (list.isNotNullOrSize()) {
            list.clear()
            list.addAll(searchHistoryList)
            notifyDataSetChanged()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun remove(index: Int) {
        list.removeAt(index)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }

    private var mOnSearchHisCallBack: OnSearchHisCallBack? = null

    fun setOnSearchHisCallBack(onSearchHisCallBack: OnSearchHisCallBack?) {
        this.mOnSearchHisCallBack = onSearchHisCallBack
    }

    interface OnSearchHisCallBack {
        fun onItemClick(searchContent: String?)
        fun onItemDelete(id: Long)
    }
}