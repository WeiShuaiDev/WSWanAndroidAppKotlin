package com.linwei.cams.component.cache.mmkv

import android.content.Context
import com.tencent.mmkv.MMKV

/**
 * MMKV使用封装
 */
object MMkvHelper {

    /**
     * 初始化
     */
    fun init(context: Context): String? = MMKV.initialize(context)

    /**
     * 保存数据（简化）
     * 根据value类型自动匹配需要执行的方法
     */
    fun put(key: String, value: Any) =
        when (value) {
            is Int -> putInt(key, value)
            is Long -> putLong(key, value)
            is Float -> putFloat(key, value)
            is Double -> putDouble(key, value)
            is String -> putString(key, value)
            is Boolean -> putBoolean(key, value)
            else -> false
        }

    private fun putString(key: String, value: String): Boolean? =
        getMMkv()?.encode(key, value)

    fun getString(key: String, defValue: String): String? =
        getMMkv()?.decodeString(key, defValue)

    private fun putInt(key: String, value: Int): Boolean? = getMMkv()?.encode(key, value)

    fun getInt(key: String, defValue: Int): Int? = getMMkv()?.decodeInt(key, defValue)

    private fun putLong(key: String, value: Long): Boolean? = getMMkv()?.encode(key, value)

    fun getLong(key: String, defValue: Long): Long? = getMMkv()?.decodeLong(key, defValue)

    private fun putDouble(key: String, value: Double): Boolean? =
        getMMkv()?.encode(key, value)

    fun getDouble(key: String, defValue: Double): Double? =
        getMMkv()?.decodeDouble(key, defValue)

    private fun putFloat(key: String, value: Float): Boolean? =
        getMMkv()?.encode(key, value)

    fun getFloat(key: String, defValue: Float): Float? =
        getMMkv()?.decodeFloat(key, defValue)

    private fun putBoolean(key: String, value: Boolean): Boolean? =
        getMMkv()?.encode(key, value)

    fun getBoolean(key: String, defValue: Boolean): Boolean? =
        getMMkv()?.decodeBool(key, defValue)

    fun getMMkv(): MMKV? = MMKV.defaultMMKV()

    fun contains(key: String): Boolean? = getMMkv()?.contains(key)

    fun remove(key: String) = getMMkv()?.removeValueForKey(key)
}