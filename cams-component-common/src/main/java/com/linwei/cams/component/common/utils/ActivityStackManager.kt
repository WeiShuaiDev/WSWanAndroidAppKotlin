package com.linwei.cams.component.common.utils

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import java.util.*

object ActivityStackManager {

    // 管理栈
    private val mActivityStack by lazy { Stack<Activity>() }

    /**
     * 添加 Activity 到管理栈
     * @param activity Activity
     */
    fun addActivityToStack(activity: Activity) {
        mActivityStack.push(activity)
    }

    /**
     * 弹出栈内指定Activity 不finish
     * @param activity Activity
     */
    fun popActivityToStack(activity: Activity) {
        if (!mActivityStack.empty()) {
            mActivityStack.forEach {
                if (it == activity) {
                    mActivityStack.remove(activity)
                    return
                }
            }
        }
    }

    /**
     * 返回到上一个 Activity 并结束当前 Activity
     */
    fun backToPreviousActivity() {
        if (!mActivityStack.empty()) {
            val activity = mActivityStack.pop()
            if (!activity.isFinishing) activity.finish()
        }
    }

    /**
     * 根据类名 判断是否是当前的 Activity
     * @param cls Class<*> 类名
     * @return Boolean
     */
    fun isCurrentActivity(cls: Class<*>): Boolean {
        val currentActivity = getCurrentActivity()
        return if (currentActivity != null) currentActivity.javaClass == cls else false
    }

    /**
     * 获取当前的 Activity
     */
    fun getCurrentActivity(): Activity? =
        if (!mActivityStack.empty()) mActivityStack.lastElement() else null

    /**
     * 结束一个栈内指定类名的 Activity
     * @param cls Class<*>
     */
    fun finishActivity(cls: Class<*>) {
        mActivityStack.forEach {
            if (it.javaClass == cls) {
                if (!it.isFinishing) it.finish()
                return
            }
        }
    }

    /**
     * 弹出其他 Activity
     */
    fun popOtherActivity() {
        val activityList = mActivityStack.toList()
        getCurrentActivity()?.run {
            activityList.forEach { activity ->
                if (this != activity) {
                    mActivityStack.remove(activity)
                    activity.finish()
                }
            }
        }
    }

    /**
     * 返回到指定 Activity
     */
    fun backToSpecifyActivity(activityClass: Class<*>) {
        val activityList = mActivityStack.toList().reversed()
        activityList.forEach {
            if (it.javaClass == activityClass) {
                return
            } else {
                mActivityStack.pop()
                it.finish()
            }
        }
    }

    /**
     * 结束所有Activity
     */
    fun finishAllActivity() {
        for (activity in mActivityStack) {
            activity?.finish()
        }
        mActivityStack.clear()
    }

    /**
     * 退出应用程序
     */
    fun AppExit(context: Context) {
        try {
            finishAllActivity()
            val manager = context
                .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            manager.killBackgroundProcesses(context.packageName)
            System.exit(0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}