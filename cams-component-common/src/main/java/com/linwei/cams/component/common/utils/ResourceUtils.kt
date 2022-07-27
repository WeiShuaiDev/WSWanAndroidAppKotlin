package com.linwei.cams.component.common.utils

/**
 * 资源数据
 */
object ResourceUtils {

    /**
     * 获取资源Id
     */
    fun getResId(variableName: String, c: Class<*>): Int {
        return try {
            val idField = c.getDeclaredField(variableName)
            idField.getInt(idField)
        } catch (e: Exception) {
            e.printStackTrace()
            -1
        }
    }
}