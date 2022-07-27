package com.linwei.cams.module.square.ui.system

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.flexbox.FlexboxLayout
import com.linwei.cams.component.common.utils.ResourceUtils
import com.linwei.cams.component.weight.indicatorview.IndicatorItem
import com.linwei.cams.framework.mvi.base.MviBaseFragment
import com.linwei.cams.module.square.R
import com.linwei.cams.module.square.databinding.SquareFragmentBaseSquareBinding
import com.linwei.cams.module.square.ui.square.mvi.intent.SystemViewModel
import com.linwei.cams.module.square.ui.square.mvi.view.SystemView
import com.linwei.cams.service.square.SquareRouterTable
import com.linwei.cams.service.square.model.SquareTreeBean
import com.linwei.cams.service.square.provider.SquareProviderHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = SquareRouterTable.PATH_FRAGMENT_SYSTEM)
class SystemFragment : MviBaseFragment<SquareFragmentBaseSquareBinding, SystemViewModel>(),
    SystemView {

    override fun hasInjectARouter(): Boolean = true

    override fun immersionBar(): Boolean = true

    private val mColorLists = mutableListOf<Int>()

    private var mLayoutInflater: LayoutInflater? = null

    override fun initView() {
        initColors()

        mViewBinding.squareIndicatorScrollView.bindIndicatorView(mViewBinding.squareIndicatorView)
    }

    /**
     * 初始化Color资源
     */
    private fun initColors() {
        for (i in 0..18) {
            val resId: Int = ResourceUtils.getResId("squareColorIn" + (i + 1), R.color::class.java)
            mColorLists.add(resId)
        }
    }

    override fun initData() {
        mViewModel?.requestSquareTreeData()
    }

    override fun initEvent() {

    }

    override fun squareTreeDataToView(data: List<SquareTreeBean>) {
        mViewBinding.squareIndicatorView.removeAllViews()
        mViewBinding.squareRightContentLayout.removeAllViews()

        for (i in data.indices) {
            val res: SquareTreeBean = data[i]
            val name: String? = res.name
            val firstName = if (TextUtils.isEmpty(name)) "" else name?.substring(0, 1)
            val view = findItem()
            val tvTitle = view!!.findViewById<TextView>(R.id.squareItemTitleView)
            tvTitle.text = name
            val flexboxLayout: FlexboxLayout = view.findViewById(R.id.flexLayout)

            val articles: List<SquareTreeBean.ChildrenBean>? = res.children
            articles?.forEach {
                findLabel(flexboxLayout)?.apply {
                    text = it.name
                    setOnClickListener { v: View? ->
                        //跳转列表页面
                        SquareProviderHelper.jumpSquareListActivity(it.name, it.id)
                    }
                    flexboxLayout.addView(this)
                }
            }
            mViewBinding.squareRightContentLayout.addView(view)

            val index: Int = i % mColorLists.size
            mViewBinding.squareIndicatorView.addIndicatorItem(
                IndicatorItem.Builder(view).setItemText(firstName)
                    .setItemColorResource(mColorLists.get(index))
                    .setItemIconResource(R.mipmap.common_ic_collect).build()
            )
        }
    }

    private fun findItem(): View? {
        if (mLayoutInflater == null) mLayoutInflater = LayoutInflater.from(mContext)
        return mLayoutInflater!!.inflate(R.layout.square_item_flex, null, false)
    }

    private fun findLabel(flexboxLayout: FlexboxLayout): AppCompatTextView? {
        if (mLayoutInflater == null) mLayoutInflater = LayoutInflater.from(flexboxLayout.context)
        return mLayoutInflater!!.inflate(
            R.layout.square_item_flex_label,
            flexboxLayout,
            false
        ) as AppCompatTextView?
    }
}