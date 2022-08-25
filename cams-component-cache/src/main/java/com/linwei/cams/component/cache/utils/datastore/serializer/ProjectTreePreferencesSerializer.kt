package com.linwei.cams.component.cache.utils.datastore.serializer

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.linwei.cams.datastore.protobuf.ProjectTreePreferences
import java.io.InputStream
import java.io.OutputStream

object ProjectTreePreferencesSerializer : Serializer<ProjectTreePreferences> {
    override val defaultValue: ProjectTreePreferences = ProjectTreePreferences.getDefaultInstance()

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun readFrom(input: InputStream): ProjectTreePreferences {
        try {
            return ProjectTreePreferences.parseFrom(input) // 是编译器自动生成的，用于读取并解析 input 的消息
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun writeTo(t: ProjectTreePreferences, output: OutputStream) =
        t.writeTo(output) // t.writeTo(output) 是编译器自动生成的，用于写入序列化消息
}