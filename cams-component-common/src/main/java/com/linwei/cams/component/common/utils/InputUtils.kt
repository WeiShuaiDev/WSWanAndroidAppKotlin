package com.linwei.cams.component.common.utils

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

object InputUtils {

    fun hideInputMethod(view: View?, event: MotionEvent, activity: Activity): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            if (isShouldHideKeyboard(view, event)) {
                hideInputMethod(activity)
                return true
            }
        }
        return false
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    fun isShouldHideKeyboard(v: View?, event: MotionEvent): Boolean {
        if (v != null && v is EditText) {
            val l = intArrayOf(0, 0)
            v.getLocationInWindow(l)
            val left = l[0]
            val top = l[1]
            val bottom = top + v.getHeight()
            val right = left + v.getWidth()
            return event.x <= left || event.x >= right || event.y <= top || event.y >= bottom
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false
    }

    /**
     * 设置输入法,如果当前页面输入法打开则关闭
     *
     * @param activity
     */
    fun hideInputMethod(activity: Activity) {
        val a = activity.currentFocus
        if (a != null) {
            val imm =
                activity.applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            try {
                imm.hideSoftInputFromWindow(
                    activity.currentFocus!!.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun hideInputMethod(editText: EditText, context: Context) {
        val imm =
            context.applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        try {
            imm.hideSoftInputFromWindow(
                editText.windowToken,
                InputMethodManager.RESULT_UNCHANGED_SHOWN
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun showSoftInput(view: View) {
        Handler().postDelayed({ toggleSoftInput(view) }, 500)
    }

    /**
     * 强制显示输入法
     *
     * @param
     */
    private fun toggleSoftInput(view: View) {
        try {
            view.isFocusable = true
            view.isFocusableInTouchMode = true
            view.requestFocus()
            val imm =
                view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.RESULT_UNCHANGED_SHOWN)
            imm.toggleSoftInput(
                InputMethodManager.HIDE_IMPLICIT_ONLY,
                InputMethodManager.HIDE_IMPLICIT_ONLY
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun isSoftInputShow(activity: Activity): Boolean {
        // 虚拟键盘隐藏 判断view是否为空
        val view = activity.window.peekDecorView()
        if (view != null) {
            // 隐藏虚拟键盘
            val inputmanger = activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            //       inputmanger.hideSoftInputFromWindow(view.getWindowToken(),0);
            return inputmanger.isActive && activity.window.currentFocus != null
        }
        return false
    }
}