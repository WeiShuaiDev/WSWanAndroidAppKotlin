package com.linwei.cams.module.home.ui.search.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.SingleLayoutHelper
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.linwei.cams.module.home.R

class SearchClearAdapter : DelegateAdapter.Adapter<BaseViewHolder>() {

    private var mShowStatus: Boolean = false

    override fun onCreateLayoutHelper(): LayoutHelper = SingleLayoutHelper()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.home_item_search_clear, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.getView<TextView>(R.id.homeClearView).apply{
            visibility = if (mShowStatus) View.VISIBLE else View.GONE
        }.setOnClickListener {
            mOnSearchClearCallBack?.onClear()
        }
        holder.getView<LinearLayout>(R.id.homeClearLayout).apply {
            visibility = if (mShowStatus) View.VISIBLE else View.GONE
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    fun refreshStatus(status: Boolean) {
        mShowStatus = status
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = 1

    private var mOnSearchClearCallBack: OnSearchClearCallBack? = null

    fun setOnSearchClearCallBack(onSearchClearCallBack: OnSearchClearCallBack?) {
        this.mOnSearchClearCallBack = onSearchClearCallBack
    }

    interface OnSearchClearCallBack {
        fun onClear()
    }
}