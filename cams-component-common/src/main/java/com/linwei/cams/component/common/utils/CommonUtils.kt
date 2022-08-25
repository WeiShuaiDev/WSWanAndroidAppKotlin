package com.linwei.cams.component.common.utils

import android.os.Build
import android.view.View
import android.widget.Toast
import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.material.snackbar.Snackbar
import com.linwei.cams.component.common.ktx.ctx

/**************************************************************************************************/

/**
 * 发送普通EventBus事件
 */
fun sendEvent(event: Any) = EventBusUtils.postEvent(event)

/**************************************************************************************************/
/**
 * 阿里路由不带参数跳转
 * @param routerUrl String 路由地址
 */
fun aRouterJump(routerUrl: String) {
    ARouter.getInstance().build(routerUrl).navigation()
}

/**************************************************************************************************/
/**
 * toast
 * @param msg String 文案
 * @param duration Int 时间
 */
fun toast(msg: String?, duration: Int = Toast.LENGTH_SHORT) {
    if (msg?.isNotEmpty() == true) {
        ToastUtils.showToast(msg, duration)
    }
}

/**
 * toast
 * @param msgId Int String资源ID
 * @param duration Int 时间
 */
fun toast(msgId: Int, duration: Int = Toast.LENGTH_SHORT) {
    ToastUtils.showToast(msgId, duration)
}

/**************************************************************************************************/
/**
 * snackbar
 * @param view View
 * @param message String
 * @param duration Int 时间
 */
fun snackBar(view: View, message: String, length: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(view, message, length)
        .show()
}

/**************************************************************************************************/
/**
 * Error日志
 * @param content String
 */
fun e(message:String){
    LogUtils.e(message)
}
/**
 * Error日志
 * @param resId Int
 */
fun e(resId:Int){
    LogUtils.e(ctx.getString(resId))
}

/**
 * Info日志
 * @param content String
 */
fun i(message:String){
    LogUtils.i(message)
}
/**
 * Info日志
 * @param resId Int
 */
fun i(resId:Int){
    LogUtils.i(ctx.getString(resId))
}

/**
 * Debug日志
 * @param content String
 */
fun d(message:String){
    LogUtils.d(message)
}
/**
 * Debug日志
 * @param resId Int
 */
fun d(resId:Int){
    LogUtils.d(ctx.getString(resId))
}

/**
 * Warn日志
 * @param content String
 */
fun w(message:String){
    LogUtils.w(message)
}
/**
 * Warn日志
 * @param resId Int
 */
fun w(resId:Int){
    LogUtils.w(ctx.getString(resId))
}

/**
 * Verbose日志
 * @param content String
 */
fun v(message:String){
    LogUtils.v(message)
}
/**
 * Verbose日志
 * @param resId Int
 */
fun v(resId:Int){
    LogUtils.v(ctx.getString(resId))
}

/**************************************************************************************************/
/**
 * 获取App版本号
 * @return Long App版本号
 */
fun getVersionCode(): Long {
    val packageInfo = ctx.packageManager
        .getPackageInfo(ctx.packageName, 0)
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        packageInfo.longVersionCode
    } else {
        packageInfo.versionCode.toLong()
    }
}

/**************************************************************************************************/
/**
 * 获取App版本名
 * @return String App版本名
 */
fun getVersionName(): String {
    return ctx.packageManager
        .getPackageInfo(ctx.packageName, 0)
        .versionName
}