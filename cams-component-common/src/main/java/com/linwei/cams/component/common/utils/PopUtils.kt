package com.linwei.cams.component.common.utils

import android.os.Handler
import android.os.Looper
import com.linwei.cams.component.common.widget.CommonPop

object PopUtil {
    /**
     * 显示通用的PopupWindow
     */
    fun showPop(
        layoutIdRes: Int,
        onPopCallBack: OnPopCallBack? = null,
        viewBinding: (commonPop: CommonPop) -> Unit
    ) {
        val commonPop = CommonPop(layoutIdRes, viewBinding)
        commonPop.showPopupWindow()
        Handler(Looper.getMainLooper()).postDelayed({
            if (commonPop.isShowing) {
                commonPop.dismiss()
            }
            onPopCallBack?.dismiss()
        }, 2000)
    }

    interface OnPopCallBack {
        fun dismiss()
    }
}