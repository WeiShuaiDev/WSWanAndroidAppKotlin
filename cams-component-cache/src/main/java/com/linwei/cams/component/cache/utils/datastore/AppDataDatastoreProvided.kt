package com.linwei.cams.component.cache.utils.datastore

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.linwei.cams.component.cache.utils.CacheConstants
import com.linwei.cams.component.common.ktx.ctx
import com.linwei.cams.datastore.protobuf.LanguagePreferences
import com.linwei.cams.datastore.protobuf.ProjectTreePreferences
import com.linwei.cams.datastore.protobuf.UserInfoPreferences
import com.linwei.cams.service.base.model.UserInfoBean
import com.linwei.cams.service.project.model.ProjectTreeBean
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.util.*

class WanAndroidPreferencesProvided<T>() {

    companion object {
        val USER_INFO = stringPreferencesKey(CacheConstants.USER_INFO)

        val LANGUAGE = stringPreferencesKey(CacheConstants.LANGUAGE)

        val PROJECT_TREE = stringPreferencesKey(CacheConstants.PROJECT_TREE)
    }

    fun fetchPreferencesFlow(key: Preferences.Key<T>, defValue: T): Flow<T> =
        ctx.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                preferences[key] ?: defValue
            }

    suspend fun savePreferencesData(key: Preferences.Key<T>, value: T) {
        ctx.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    suspend fun fetchInitialPreferences() = ctx.dataStore.data.first().toPreferences()

}

class UserInfoPreferencesProvided() {

    val userInfoPreferencesFlow: Flow<UserInfoPreferences> = ctx.userInfoPreferencesStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(UserInfoPreferences.getDefaultInstance())
            } else {
                throw exception
            }
        }

    suspend fun saveUserInfoPreferencesData(userInfo: UserInfoBean) {
        ctx.userInfoPreferencesStore.updateData { data ->
            data.toBuilder().apply {
                userInfo.let {
                    email = it.email
                    icon = it.icon
                    id = it.id
                    password = it.password
                    token = it.token
                    type = it.type
                    username = it.username
                    addAllChapterTops(it.chapterTops?.map { value ->
                        Gson().toJson(value)
                    })
                    addAllCollectIds(userInfo.collectIds)
                    coinCount = it.coinCount
                    level = it.level
                    rank = it.rank
                    userId = it.userId
                    reason = it.reason
                    desc = it.desc
                    date = it.date
                }
            }.build()
        }
    }

    suspend fun fetchInitialPreferences() = ctx.userInfoPreferencesStore.data.first()
}

class LanguagePreferencesProvided() {

    val languagePreferencesFlow: Flow<LanguagePreferences> = ctx.languagePreferencesStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(LanguagePreferences.getDefaultInstance())
            } else {
                throw exception
            }
        }

    suspend fun saveLanguagePreferencesData(language: Locale) {
        ctx.languagePreferencesStore.updateData { data ->
            data.toBuilder().apply {
                language.let {
                    locale = Gson().toJson(it)
                }
            }.build()
        }
    }

    suspend fun fetchInitialPreferences() = ctx.languagePreferencesStore.data.first()
}

class ProjectTreePreferencesProvided() {

    val projectTreePreferencesFlow: Flow<ProjectTreePreferences> =
        ctx.projectTreePreferencesStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(ProjectTreePreferences.getDefaultInstance())
                } else {
                    throw exception
                }
            }

    suspend fun saveProjectTreePreferencesData(projectTreeList: List<ProjectTreeBean>) {
        ctx.projectTreePreferencesStore.updateData { data ->
            data.toBuilder().apply {
                val values = projectTreeList.map {
                    ProjectTreePreferences.ProjectTreeBean.newBuilder()
                        .addAllChildren(it.children?.map { value ->Gson().toJson(value) })
                        .setCourseId(it.courseId)
                        .setId(it.id)
                        .setName(it.name)
                        .setOrder(it.order)
                        .setParentChapterId((it.parentChapterId))
                        .setUserControlSetTop(it.userControlSetTop)
                        .setVisible(it.visible)
                        .build()
                }
                addAllProjectTreeBean(values)
            }.build()
        }
    }

    suspend fun fetchInitialPreferences() = ctx.projectTreePreferencesStore.data.first()
}