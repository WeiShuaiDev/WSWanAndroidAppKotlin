package com.linwei.cams.component.cache.utils

import com.linwei.cams.component.common.ktx.ctx
import com.linwei.cams.component.common.utils.FileUtils

/**
 * CacheUtils工具
 */
object CacheUtils {

    /**
     * 获取系统默认缓存文件夹内的缓存大小
     */
    fun getTotalCacheSize(): String {
        var cacheSize: Long = FileUtils.getSize(ctx.cacheDir)
        if (FileUtils.isSDCardAlive()) {
            cacheSize += FileUtils.getSize(ctx.externalCacheDir)
        }
        return FileUtils.formatSize(cacheSize.toDouble())
    }

    /**
     * 清除所有缓存文件
     */
    fun clearAllCache() {
        FileUtils.delete(ctx.cacheDir)
        if (FileUtils.isSDCardAlive()) {
            FileUtils.delete(ctx.externalCacheDir)
        }
    }
}