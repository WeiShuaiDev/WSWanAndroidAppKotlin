package com.linwei.cams.component.common.utils

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.os.LocaleList
import com.linwei.cams.component.common.ktx.app
import java.util.*

/**
 * LanguageUtils工具
 */
object LanguageUtils {

    /**
     * 获取系统语言
     */
    fun getSystemLanguage(): String {
        return Locale.getDefault().language + "-" + Locale.getDefault().country
    }

    /**
     * 获取当前语言
     */
    fun getCurrentLanguage(getMemoryLanguage: () -> Locale?): Locale {
        var locale: Locale? = getMemoryLanguage()
        if (locale == null) {
            locale = Locale.getDefault()
        }
        return locale!!
    }

    /**
     * 切换为中文
     */
    fun switchChinese(saveMemoryLanguage: (locale: Locale?) -> Any) {
        changeLanguage(Locale.SIMPLIFIED_CHINESE, saveMemoryLanguage)
    }

    /**
     * 切换为英文
     */
    fun switchEnglish(saveMemoryLanguage: (locale: Locale?) -> Any) {
        changeLanguage(Locale.US, saveMemoryLanguage)
    }

    /**
     * 切换语言
     */
    fun switchLanguage(locale: Locale, saveMemoryLanguage: (locale: Locale?) -> Any) {
        changeLanguage(locale, saveMemoryLanguage)
    }

    private fun changeLanguage(locale: Locale, saveMemoryLanguage: (locale: Locale?) -> Any) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            setConfiguration(app, locale)
        }
        saveMemoryLanguage(locale)
    }

    /**
     * 修改配置
     * @param context
     * @param locale  想要切换的语言类型 比如 "en" ,"zh"
     */
    @SuppressLint("NewApi", "ObsoleteSdkInt")
    private fun setConfiguration(context: Context, locale: Locale?) {
        if (locale == null) {
            return
        }
        //获取应用程序的所有资源对象
        val resources = context.resources
        //获取设置对象
        val configuration = resources.configuration
        //如果API < 17
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.locale = locale
        } else  //如果 17 < = API < 25 Android 7.7.1
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) {
                configuration.setLocale(locale)
            } else { //API 25  Android 7.7.1
                configuration.setLocale(locale)
                configuration.setLocales(LocaleList(locale))
            }
        val dm = resources.displayMetrics
        resources.updateConfiguration(configuration, dm)
    }

    fun attachBaseContext(context: Context?, getMemoryLanguage: () -> Locale?): Context? {
        val locale: Locale = getCurrentLanguage(getMemoryLanguage)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (context != null) {
                updateResources(context, locale)
            } else {
                context
            }
        } else {
            context
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(context: Context, locale: Locale): Context {
        val resources = context.resources
        val configuration = resources.configuration.apply {
            setLocale(locale)
            setLocales(LocaleList(locale))
        }
        return context.createConfigurationContext(configuration)
    }

}