package com.linwei.cams.component.common.widget

import android.graphics.Color
import android.view.View
import android.view.animation.Animation
import android.widget.*
import com.linwei.cams.component.common.ktx.app
import razerdp.basepopup.BasePopupWindow
import razerdp.util.animation.AnimationHelper
import razerdp.util.animation.TranslationConfig
/**
 * 通用的PopupWindow
 */
class CommonPop(private val layoutIdRes: Int, val viewBinding: (commonPop: CommonPop) -> Unit) :
    BasePopupWindow(app) {
    private var mMapViews: MutableMap<Int, View> = mutableMapOf()

    override fun onCreateContentView(): View {
        return createPopupById(layoutIdRes)
    }

    override fun onViewCreated(contentView: View) {
        super.onViewCreated(contentView)
        setBackgroundColor(Color.TRANSPARENT)
    }

    override fun showPopupWindow() {
        viewBinding(this)
        super.showPopupWindow()
    }

    override fun onCreateShowAnimation(): Animation {
        return AnimationHelper.asAnimation()
            .withTranslation(TranslationConfig.FROM_TOP)
            .toShow()
    }

    override fun onCreateDismissAnimation(): Animation {
        return AnimationHelper.asAnimation()
            .withTranslation(TranslationConfig.TO_TOP)
            .toShow()
    }

    /**
     * 根据 `ResId` 获取 [View]
     * @param viewId [Int]
     * @return [View]
     */
    fun getView(viewId: Int): View {
        return findViewById(viewId)
    }

    /**
     * 根据 `ResId` 获取 [TextView]
     * @param viewId [Int]
     * @return [TextView]
     */
    fun getTextView(viewId: Int): TextView {
        return findViewById(viewId)
    }

    /**
     * 根据 `ResId` 获取 [Button]
     * @param viewId [Int]
     * @return [Button]
     */
    fun getButton(viewId: Int): Button {
        return getViewById(viewId)
    }

    /**
     * 根据 `ResId` 获取 [ImageView]
     * @param viewId [Int]
     * @return [ImageView]
     */
    fun getImageView(viewId: Int): ImageView {
        return getViewById(viewId)
    }

    /**
     * 根据 `ResId` 获取 [ImageButton]
     * @param viewId [Int]
     * @return [ImageButton]
     */
    fun getImageButton(viewId: Int): ImageButton {
        return getViewById(viewId)
    }

    /**
     * 根据 `ResId` 获取 [EditText]
     * @param viewId [Int]
     * @return [EditText]
     */
    fun getEditText(viewId: Int): EditText {
        return getViewById(viewId)
    }

    /**
     * 获取控件
     */
    @Suppress("UNCHECKED_CAST")
    private fun <T : View> getViewById(viewId: Int): T {
        var view: View? = mMapViews[viewId]
        val rootView = getContentView()
        if (view == null) {
            if (rootView != null) {
                view = rootView.findViewById(viewId)
                mMapViews[viewId] = view
            }
        }
        return view as T
    }
}