package com.linwei.cams.module.home.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.SingleLayoutHelper
import com.bumptech.glide.Glide
import com.linwei.cams.module.home.R
import com.linwei.cams.service.home.model.BannerEntity
import com.youth.banner.Banner
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.transformer.MZScaleInTransformer

class HomeBannerAdapter(
    private var lifecyclerOwner: LifecycleOwner,
    private var list: List<BannerEntity>
) :
    DelegateAdapter.Adapter<HomeBannerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeBannerAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.home_item_banner, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HomeBannerAdapter.ViewHolder, position: Int) {
        holder.apply {
            homeBannerView.indicator = CircleIndicator(homeBannerView.context)
            homeBannerView.setPageTransformer(MZScaleInTransformer())
            homeBannerView.addBannerLifecycleObserver(lifecyclerOwner)
            homeBannerView.setAdapter(object : BannerImageAdapter<BannerEntity>(list) {
                override fun onBindView(
                    holder: BannerImageHolder?,
                    data: BannerEntity?,
                    position: Int,
                    size: Int
                ) {
                    holder?.imageView?.let { view ->
                        Glide.with(homeBannerView.context).load(data?.imagePath).into(
                            view
                        )
                    }
                }
            })
        }
    }

    override fun getItemCount(): Int = 1

    override fun onCreateLayoutHelper(): LayoutHelper = SingleLayoutHelper()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val homeBannerView =
            view.findViewById<Banner<BannerEntity, BannerImageAdapter<BannerEntity>>>(R.id.homeBannerView)
    }
}