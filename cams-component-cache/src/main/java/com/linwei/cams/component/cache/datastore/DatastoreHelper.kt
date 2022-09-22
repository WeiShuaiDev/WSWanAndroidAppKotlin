package com.linwei.cams.component.cache.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.migrations.SharedPreferencesMigration
import androidx.datastore.migrations.SharedPreferencesView
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.linwei.cams.component.cache.CacheConstants
import com.linwei.cams.component.cache.datastore.serializer.LanguagePreferencesSerializer
import com.linwei.cams.component.cache.datastore.serializer.ProjectTreePreferencesSerializer
import com.linwei.cams.component.cache.datastore.serializer.UserInfoPreferencesSerializer
import com.linwei.cams.component.common.utils.i
import com.linwei.cams.datastore.protobuf.LanguagePreferences
import com.linwei.cams.datastore.protobuf.ProjectTreePreferences
import com.linwei.cams.datastore.protobuf.UserInfoPreferences
import com.linwei.cams.service.base.model.UserInfoBean
import com.linwei.cams.service.project.model.ProjectTreeBean

val Context.dataStore by preferencesDataStore(
    name = CacheConstants.WAN_ANDROID_PREFERENCES_DATA_STORE_NAME,
    produceMigrations = { context ->
        // Since we're migrating from SharedPreferences, add a migration based on the
        // SharedPreferences name
        listOf(
            SharedPreferencesMigration(
                context,
                CacheConstants.WAN_ANDROID_PREFERENCES_DATA_STORE_NAME
            )
        )
    }
)

val Context.userInfoPreferencesStore: DataStore<UserInfoPreferences> by dataStore(
    fileName = CacheConstants.USER_PROTO_DATA_STORE_NAME,
    serializer = UserInfoPreferencesSerializer,
    produceMigrations = { context ->
        listOf(
            SharedPreferencesMigration(
                context,
                CacheConstants.USER_PREFERENCES_NAME
            ) { sharedPrefs: SharedPreferencesView, currentData: UserInfoPreferences ->

                val gson = Gson()
                // 获取 SharedPreferences 的数据
                val content = sharedPrefs.getString(CacheConstants.USER_INFO)
                i("UserInfo sharedPreferences to datastore data=[${content}}")
                val userInfoBean = gson.fromJson(content, UserInfoBean::class.java)

                // 将 SharedPreferences 每一对 key-value 的数据映射到 Proto DataStore 中
                userInfoBean.let {
                    currentData.toBuilder()
                        .setEmail(it.email)
                        .setIcon(it.icon)
                        .setId(it.id)
                        .setPassword(it.password)
                        .setToken(it.token)
                        .setType(it.type)
                        .setUsername(it.username)
                        .addAllChapterTops(it.chapterTops?.map {
                            gson.toJson(it)
                        })
                        .addAllCollectIds(it.collectIds)
                        .setCoinCount(it.coinCount)
                        .setLevel(it.level)
                        .setRank(it.rank)
                        .setUserId(it.userId)
                        .setReason(it.reason)
                        .setDesc(it.desc)
                        .setDate(it.date)
                        .build()
                }
            }
        )
    }
)

val Context.languagePreferencesStore: DataStore<LanguagePreferences> by dataStore(
    fileName = CacheConstants.LANGUAGE_PROTO_DATA_STORE_NAME,
    serializer = LanguagePreferencesSerializer,
    produceMigrations = { context ->
        listOf(
            SharedPreferencesMigration(
                context,
                CacheConstants.LANGUAGE_PREFERENCES_NAME
            ) { sharedPrefs: SharedPreferencesView, currentData: LanguagePreferences ->

                val content = sharedPrefs.getString(CacheConstants.LANGUAGE)
                i("Language sharedPreferences to datastore data=[${content}}")

                // 将 SharedPreferences 每一对 key-value 的数据映射到 Proto DataStore 中
                currentData.toBuilder()
                    .setLocale(content)
                    .build()
            }
        )
    }
)

val Context.projectTreePreferencesStore: DataStore<ProjectTreePreferences> by dataStore(
    fileName = CacheConstants.PROJECT_TREE_PROTO_DATA_STORE_NAME,
    serializer = ProjectTreePreferencesSerializer,
    produceMigrations = { context ->
        listOf(
            SharedPreferencesMigration(
                context,
                CacheConstants.PROJECT_TREE_PREFERENCES_NAME
            ) { sharedPrefs: SharedPreferencesView, currentData: ProjectTreePreferences ->
                // 获取 SharedPreferences 的数据
                val projectTreeList: MutableList<ProjectTreeBean> = mutableListOf()
                val content = sharedPrefs.getString(CacheConstants.PROJECT_TREE)
                i("ProductTree sharedPreferences to datastore data=[${content}}")

                val gson = Gson()
                try {
                    val element = JsonParser().parse(content).asJsonArray
                    for (jsonElement in element) {
                        projectTreeList.add(gson.fromJson(jsonElement, ProjectTreeBean::class.java))
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                val list = projectTreeList.map {
                    ProjectTreePreferences.ProjectTreeBean.newBuilder()
                        .addAllChildren(it.children?.map { gson.toJson(it) })
                        .setCourseId(it.courseId)
                        .setId(it.id)
                        .setName(it.name)
                        .setOrder(it.order)
                        .setParentChapterId((it.parentChapterId))
                        .setUserControlSetTop(it.userControlSetTop)
                        .setVisible(it.visible)
                        .build()
                }

                // 将 SharedPreferences 每一对 key-value 的数据映射到 Proto DataStore 中
                currentData.toBuilder()
                    .addAllProjectTreeBean(list)
                    .build()
            }
        )
    }
)
