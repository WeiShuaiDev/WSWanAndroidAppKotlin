package com.linwei.cams.component.cache.sharedpreferences

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.linwei.cams.component.common.ktx.ctx

class SharePreferenceHelper<T>(val prefName: String, val default: T) {

    private val mSharedPreferencesMap: MutableMap<String, SharedPreferences> = mutableMapOf()

    private lateinit var mSharedPreferences: SharedPreferences

    init {
        if (prefName.isNotEmpty()) {
            mSharedPreferencesMap[prefName].takeIf { mSharedPreferencesMap.isNotEmpty() }?.apply {
                mSharedPreferences = this
            } ?: apply {
                mSharedPreferences = ctx.getSharedPreferences(prefName, Context.MODE_PRIVATE)
                mSharedPreferencesMap[prefName] = mSharedPreferences
            }
        }
    }

    /**
     * 根据 `key` 标识获取 `value` 数据
     * @param key [String] 存储标识
     */
    @Suppress("UNCHECKED_CAST")
    fun findPreference(key: String): T {
        return when (default) {
            is Long -> mSharedPreferences.getLong(key, default)
            is Int -> mSharedPreferences.getInt(key, default)
            is Boolean -> mSharedPreferences.getBoolean(key, default)
            is String -> mSharedPreferences.getString(key, default)
            else -> throw IllegalAccessException("Unsupported type.")
        } as T
    }

    /**
     * 根据 `key` 标识存储 `value` 数据
     * @param key [String] 存储标识
     * @param val [T] 存储数据
     */
    fun putPreference(key: String, value: T) {
        //定义 with 代表内部通过 this能够调用到 prefs.edit 返回的对象
        with(mSharedPreferences.edit()) {
            when (value) {
                is Long -> putLong(key, value)
                is Int -> putInt(key, value)
                is Boolean -> putBoolean(key, value)
                is String -> putString(key, value)
                else -> throw IllegalAccessException("Unsupported type.")
            }.apply()
        }
    }

    /**
     * 根据 `key` 标识删除存储
     * @param key [String] 存储标识
     */
    @SuppressLint("CommitPrefEdits")
    fun removePreferenceKey(key:String){
        if(mSharedPreferences.contains(key)){
            mSharedPreferences.edit().remove(key)
        }
    }

    /**
     * 删除所有存储
     */
    @SuppressLint("CommitPrefEdits")
    fun removePreferenceAllKey(){
        mSharedPreferences.edit().clear()
    }

}