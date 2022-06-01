package com.linwei.cams.component.common.ktx

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.graphics.Point
import android.net.Uri
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.*
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.linwei.cams.component.common.R
import com.linwei.cams.component.common.base.CommonBaseApplication
import java.lang.reflect.Field


@SuppressLint("StaticFieldLeak")
val ctx: Context = CommonBaseApplication.context

@SuppressLint("StaticFieldLeak")
val app: Context = CommonBaseApplication.application

/**
 * 设置 `Activity`全屏
 */
fun Activity.setFullScreen() {
    this.apply {
        val params: WindowManager.LayoutParams = this.window.attributes
        params.flags = params.flags or WindowManager.LayoutParams.FLAG_FULLSCREEN
        this.window.attributes = params
        this.window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }
}

/**
 * 取消 `Activity` 全屏
 */
fun Activity.cancelFullScreen() {
    this.apply {
        val params: WindowManager.LayoutParams = this.window.attributes
        params.flags = params.flags and (WindowManager.LayoutParams.FLAG_FULLSCREEN.inv())
        this.window.attributes = params
        this.window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

    }
}

/**
 * 判断是否有状态栏
 * @return [Boolean] true:存在；false:不存在
 */
fun Activity.hasStatusBar(): Boolean {
    val attrs: WindowManager.LayoutParams = this.window.attributes
    return (attrs.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN) != WindowManager.LayoutParams.FLAG_FULLSCREEN
}

/**
 * 判断 `Activity` 是否全屏
 * @return [Boolean] true:全屏； false:不是全屏
 */
fun Activity.isFullScreen(): Boolean {
    val params: WindowManager.LayoutParams = this.window.attributes
    return (params.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN) == WindowManager.LayoutParams.FLAG_FULLSCREEN
}

/**
 * 获取ActionBar高度
 * @return [Int] 高度
 */
fun Context.fetchActionBarHeight(): Int {
    var actionBarHeight = 0
    val tv = TypedValue()
    if (this.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
        actionBarHeight = TypedValue.complexToDimensionPixelSize(
            tv.data, this.resources.displayMetrics
        )
    }
    return actionBarHeight
}

/**
 * 获取状态栏高度
 * @return [Int] 高度
 */
@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
@SuppressLint("PrivateApi")
fun Context.fetchStatusBarHeight(): Int {
    val c: Class<*>
    val obj: Any
    val field: Field
    val x: Int
    try {
        c = Class.forName("com.android.internal.R\$dimen")
        obj = c.newInstance()
        field = c.getField("status_bar_height")
        x = field[obj].toString().toInt()
        return this.getResources()
            .getDimensionPixelSize(x)
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
    return 0
}


/**
 * 获取屏幕的真实宽高
 * @return [IntArray]
 */
fun Context.fetchRealScreenSize(): IntArray {
    val size = IntArray(2)
    var widthPixels: Int
    var heightPixels: Int
    val w: WindowManager =
        this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val d: Display = w.defaultDisplay
    val metrics = DisplayMetrics()
    d.getMetrics(metrics)
    // since SDK_INT = 1;
    widthPixels = metrics.widthPixels
    heightPixels = metrics.heightPixels
    try {
        // used when 17 > SDK_INT >= 14; includes window decorations (statusbar bar/menu bar)
        widthPixels = Display::class.java.getMethod("getRawWidth").invoke(d) as Int
        heightPixels = Display::class.java.getMethod("getRawHeight").invoke(d) as Int
    } catch (ignored: java.lang.Exception) {
    }
    try {
        // used when SDK_INT >= 17; includes window decorations (statusbar bar/menu bar)
        val realSize = Point()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            d.getRealSize(realSize)
        }
        Display::class.java.getMethod("getRealSize", Point::class.java)
            .invoke(d, realSize)
        widthPixels = realSize.x
        heightPixels = realSize.y
    } catch (ignored: java.lang.Exception) {
    }
    size[0] = widthPixels
    size[1] = heightPixels
    return size
}

/**
 * 判断是否存在实体按键
 * @return bool [Boolean]
 */
fun Context.isNavMenuExist(): Boolean {
    //通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)来确定是否有navigation bar
    val hasMenuKey: Boolean = ViewConfiguration.get(this).hasPermanentMenuKey()
    val hasBackKey: Boolean =
        KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)
    return !hasMenuKey && !hasBackKey
}

/**
 * 获取运算后内存大小
 * {@code maxSize}最大内存大小，{@ multiplierSize}内存运算阈值
 * @param maxSize [Int]
 * @param multiplierSize [Float]
 */
fun Context.getCalculateCacheSize(maxSize: Int, multiplierSize: Float): Int {
    val activityManager: ActivityManager =
        getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val targetMemoryCacheSize: Int = (activityManager.memoryClass * multiplierSize * 1024).toInt()
    if (targetMemoryCacheSize > maxSize) {
        return maxSize
    }
    return targetMemoryCacheSize
}

/**
 * {@code frameId} 容器中Fragment,替换为 {@code fragment},并设置标识为tag
 */
fun AppCompatActivity.replaceFragmentInActivity(
    fragment: Fragment,
    @IdRes frameId: Int,
    tag: String
) {
    supportFragmentManager.transact {
        replace(frameId, fragment, tag)
    }
}

/**
 * 设置 {@link FragmentManager} 切换动画
 */
@SuppressLint("PrivateResource")
private inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        setCustomAnimations(
            R.anim.abc_grow_fade_in_from_bottom,
            R.anim.abc_fade_out,
            R.anim.abc_fade_in,
            R.anim.abc_shrink_fade_out_from_bottom
        )
        action()
    }.commit()
}
