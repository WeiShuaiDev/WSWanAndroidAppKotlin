package com.linwei.cams.component.cache.utils.sharedpreferences

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.linwei.cams.component.cache.utils.CacheConstants
import com.linwei.cams.component.common.ktx.isNotNullOrSize
import com.linwei.cams.service.login.model.UserInfoBean
import com.linwei.cams.service.project.model.ProjectTreeBean
import java.util.*

class AppDataSharePreferenceProvided {

    private val mGson: Gson by lazy {
        Gson()
    }

    /**
     * 保存用户信息
     * @param userInfo UserInfoBean
     */
    fun saveUserInfo(userInfo: UserInfoBean) {
        SharePreferenceHelper(
            CacheConstants.USER_INFO,
            ""
        ).putPreference(CacheConstants.USER_INFO, mGson.toJson(userInfo))
    }

    /**
     * 获取用户信息
     */
    fun getUserInfo(): UserInfoBean? {
        val strJson = SharePreferenceHelper(
            CacheConstants.USER_INFO,
            ""
        ).findPreference(CacheConstants.USER_INFO)
        return if (strJson.isNotEmpty()) {
            null
        } else try {
            mGson.fromJson(strJson, UserInfoBean::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 保存语言信息
     * @param local Locale
     */
    fun saveLanguage(locale: Locale) {
        SharePreferenceHelper(
            CacheConstants.LANGUAGE,
            ""
        ).putPreference(CacheConstants.LANGUAGE, mGson.toJson(locale))
    }

    /**
     * 获取语言信息
     */
    fun getLanguage(): Locale? {
        val strJson = SharePreferenceHelper(
            CacheConstants.LANGUAGE,
            ""
        ).findPreference(CacheConstants.LANGUAGE)
        return if (strJson.isNotEmpty()) {
            null
        } else try {
            mGson.fromJson(strJson, Locale::class.java)
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
        saveList(CacheConstants.PROJECT_TREE, dataList)
    }

    /**
     * 获取项目标签
     * @param cls [Class]
     */
    fun getProjectTree(): List<ProjectTreeBean> {
        return getList(CacheConstants.PROJECT_TREE, ProjectTreeBean::class.java)
    }

    /**
     * 保存 List 类型数据
     * @param key [String]
     * @param list [List]
     */
    private fun <T : Any> saveList(key: String, list: List<T>?) {
        if (list.isNotNullOrSize()) {
            SharePreferenceHelper(
                key,
                ""
            ).putPreference(key, mGson.toJson(list))
        }
    }

    /**
     * 获取 List 类型数据
     */
    private fun <T> getList(key: String, clazz: Class<T>): List<T> {
        val dataList: MutableList<T> = mutableListOf()
        val strJson = SharePreferenceHelper(
            key,
            ""
        ).findPreference(key)

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
        SharePreferenceHelper(
            CacheConstants.USER_INFO,
            ""
        ).removePreferenceKey(CacheConstants.USER_INFO)
    }

}