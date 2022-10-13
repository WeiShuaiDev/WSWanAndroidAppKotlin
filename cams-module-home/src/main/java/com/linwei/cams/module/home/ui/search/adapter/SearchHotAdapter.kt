package com.linwei.cams.module.home.ui.search.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.flexbox.FlexboxLayout
import com.linwei.cams.component.common.ktx.isNotNullOrSize
import com.linwei.cams.module.home.R
import com.linwei.cams.service.home.model.SearchBean
import java.util.*

class SearchHotAdapter(private var list: MutableList<SearchBean>) :
    DelegateAdapter.Adapter<BaseViewHolder>() {

    private val mFlexItemTextViewCaches: Queue<AppCompatTextView> = LinkedList()
    private var mLayoutInflater: LayoutInflater? = null

    override fun onCreateLayoutHelper(): LayoutHelper = LinearLayoutHelper()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.home_item_search_hot, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val flexboxLayout: FlexboxLayout = holder.getView(R.id.homeFlexLayout)
        flexboxLayout.removeAllViews() //注释这条属性，用下面onViewRecycled()方法也行

        list.get(position).data?.let {
            if (it.isNotNullOrSize()) {
                for (i in it.indices) {
                    it[i].apply {
                        val labelView = createOrGetCacheTv(flexboxLayout)
                        labelView.text = name
                        labelView.setOnClickListener { v: View? ->
                            mOnSearchHotCallBack?.onHot(name)
                        }
                        flexboxLayout.addView(labelView)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = list.size

    override fun onViewRecycled(holder: BaseViewHolder) {
        super.onViewRecycled(holder)
//        FlexboxLayout flexLayout = holder.getView(R.id.homeFlexLayout);
//        if (flexLayout != null) {
//            for (int i = 0; i < flexLayout.getChildCount(); i++) {
//                mFlexItemTextViewCaches.offer((AppCompatTextView) flexLayout.getChildAt(i));
//            }
//            flexLayout.removeAllViews();
//        }
    }

    private fun createOrGetCacheTv(flexboxLayout: FlexboxLayout): AppCompatTextView {
        val tv = mFlexItemTextViewCaches.poll()
        return tv ?: findLabel(flexboxLayout)
    }

    private fun findLabel(flexboxLayout: FlexboxLayout): AppCompatTextView {
        if (mLayoutInflater == null) mLayoutInflater = LayoutInflater.from(flexboxLayout.context)
        return mLayoutInflater?.inflate(
            R.layout.home_item_flexlayout_label,
            flexboxLayout,
            false
        ) as AppCompatTextView
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refresh(searchList: List<SearchBean>) {
        list.clear()
        list.addAll(searchList)
        notifyDataSetChanged()
    }

    interface OnSearchHotCallBack {
        fun onHot(title: String?)
    }

    private var mOnSearchHotCallBack: OnSearchHotCallBack? = null

    fun setOnSearchHotCallBack(onSearchHotCallBack: OnSearchHotCallBack?) {
        this.mOnSearchHotCallBack = onSearchHotCallBack
    }

}