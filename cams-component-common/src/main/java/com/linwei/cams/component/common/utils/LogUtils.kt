package com.linwei.cams.component.common.utils

import android.util.Log
import com.linwei.cams.component.common.BuildConfig
import com.linwei.cams.component.common.global.Tag.LOG_TAG
import com.linwei.tool.utils.LoggerUtils

/**
 * Log 日志
 */
object LogUtils {
    const val logSubLenth = 3000 //每行log长度

    /**
     * Error日志
     * @param content String
     */
    fun e(content: String) {
        takeIf { BuildConfig.DEBUG }.apply {
            logSplitControl(LOG_TAG, content, 1) { tag, message ->
                //日志输出到控制台
                Log.e(tag, message)
                //日志保存到本地文件
                LoggerUtils.e(message)
            }
        }
    }

    /**
     * Info 日志
     * @param content String
     */
    fun i(content: String) {
        takeIf { BuildConfig.DEBUG }.apply {
            logSplitControl(LOG_TAG, content, 1) { tag, message ->
                //日志输出到控制台
                Log.i(tag, message)
                //日志保存到本地文件
                LoggerUtils.i(message)
            }
        }
    }

    /**
     * Debug 日志
     * @param content String
     */
    fun d(content: String) {
        takeIf { BuildConfig.DEBUG }.apply {
            logSplitControl(LOG_TAG, content, 1) { tag, message ->
                //日志输出到控制台
                Log.d(tag, message)
                //日志保存到本地文件
                LoggerUtils.d(message)
            }
        }
    }

    /**
     * Warn 日志
     * @param content String
     */
    fun w(content: String) {
        takeIf { BuildConfig.DEBUG }.apply {
            logSplitControl(LOG_TAG, content, 1) { tag, message ->
                //日志输出到控制台
                Log.w(tag, message)
                //日志保存到本地文件
                LoggerUtils.w(message)
            }
        }
    }

    /**
     * Verbose 日志
     * @param content String
     */
    fun v(content: String) {
        takeIf { BuildConfig.DEBUG }.apply {
            logSplitControl(LOG_TAG, content, 1) { tag, message ->
                //日志输出到控制台
                Log.v(tag, message)
                //日志保存到本地文件
                LoggerUtils.v(message)
            }
        }
    }

    /**
     * 日志分割处理
     * @param tag String 标识
     * @param message String 打印信息
     * @param index:Int 角标
     */
    private fun logSplitControl(
        tag: String,
        message: String,
        index: Int,
        body: (explain: String, message: String) -> Unit
    ) {
        var i = index
        if (i > 10) return
        if (message.length <= logSubLenth) {
            body(tag, "$tag$i：     $message")
            return
        }
        val msg1 = message.substring(0, logSubLenth)
        body(tag, "$tag$i：     $msg1")
        val msg2 = message.substring(logSubLenth)
        logSplitControl(tag, msg2, ++i, body)
    }
}
