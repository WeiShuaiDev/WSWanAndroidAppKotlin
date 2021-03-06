package com.linwei.cams.component.cache.utils.mmkv

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import com.linwei.cams.component.common.ktx.isNotNullOrSize
import com.linwei.cams.component.common.ktx.isNullOrEmpty
import com.linwei.cams.service.project.model.ProjectTreeBean
import com.tencent.bugly.crashreport.biz.UserInfoBean
import java.util.*

class AppDataProvided {

    private val mGson: Gson by lazy {
        Gson()
    }

    /**
     * 保存用户信息
     * @param userInfo UserInfoBean
     */
    fun saveUserInfo(userInfo: UserInfoBean) {
        MMkvHelper.put(MMkvCacheConstants.USER_INFO, mGson.toJson(userInfo))
    }

    /**
     * 获取用户信息
     */
    fun getUserInfo(): UserInfoBean? {
        return MMkvHelper.getMMkv()?.decodeParcelable(
            MMkvCacheConstants.USER_INFO,
            UserInfoBean::class.java
        )
    }

    /**
     * 保存语言信息
     * @param local Locale
     */
    fun saveLanguage(locale: Locale) {
        MMkvHelper.put(MMkvCacheConstants.LANGUAGE, mGson.toJson(locale))
    }

    /**
     * 获取语言信息
     */
    fun getLanguage(): Locale? {
        val language = MMkvHelper.getString(MMkvCacheConstants.LANGUAGE, "")
        return if (language.isNullOrEmpty()) {
            null
        } else try {
            mGson.fromJson(language, Locale::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 保存项目标签
     * @param dataList [List]
     */
    fun saveProjectTree(dataList: List<ProjectTreeBean>?) {
        saveList(MMkvCacheConstants.PROJECT_TABS, dataList)
    }

    /**
     * 获取项目标签
     * @param cls [Class]
     */
    fun getProjectTree(): List<ProjectTreeBean> {
        return getList(MMkvCacheConstants.PROJECT_TABS, ProjectTreeBean::class.java)
    }

    /**
     * 保存 List 类型数据
     * @param key [String]
     * @param list [List]
     */
    private fun <T : Any> saveList(key: String, list: List<T>?) {
        if (list.isNotNullOrSize()) {
            MMkvHelper.put(key, mGson.toJson(list))
        }
    }

    /**
     * 获取 List 类型数据
     */
    private fun <T> getList(key: String, clazz: Class<T>): List<T> {
        val dataList: MutableList<T> = mutableListOf()
        val strJson: String =
            MMkvHelper.getString(key, "") ?: return dataList
        try {
            val gson = Gson()
            val element = JsonParser().parse(strJson).asJsonArray
            for (jsonElement in element) {
                dataList.add(gson.fromJson(jsonElement, clazz))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return dataList
    }

    /**
     * 删除用户信息
     */
    fun logout() {
        MMkvHelper.getMMkv()?.remove(MMkvCacheConstants.USER_INFO)
    }

}