package com.linwei.cams.component.cache.utils.datastore.serializer

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.linwei.cams.datastore.protobuf.UserInfoPreferences
import java.io.InputStream
import java.io.OutputStream

object UserInfoPreferencesSerializer : Serializer<UserInfoPreferences> {
    override val defaultValue: UserInfoPreferences = UserInfoPreferences.getDefaultInstance()

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun readFrom(input: InputStream): UserInfoPreferences {
        try {
            return UserInfoPreferences.parseFrom(input) // 是编译器自动生成的，用于读取并解析 input 的消息
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun writeTo(t: UserInfoPreferences, output: OutputStream) =
        t.writeTo(output) // t.writeTo(output) 是编译器自动生成的，用于写入序列化消息
}