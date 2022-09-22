package com.linwei.cams.component.common.utils

import android.os.Environment
import java.io.File
import java.math.BigDecimal

object FileUtils {
    fun isSDCardAlive(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    /**
     * 文件大小
     */
    fun getSize(file: File?): Long {
        var size: Long = 0
        if(file==null){
            return size
        }
        try {
            val fileList = file.listFiles()
            for (f in fileList) {
                size = if (f.isDirectory) {
                    size + getSize(f)
                } else {
                    size + f.length()
                }
            }
        } catch (ignore: Exception) {
        }
        return size
    }

    /**
     * 格式化单位
     */
    fun formatSize(size: Double): String {
        val kiloByte = size / 1024
        if (kiloByte < 1) {
            return "0KB"
        }
        val megaByte = kiloByte / 1024
        if (megaByte < 1) {
            val result1 = BigDecimal(java.lang.Double.toString(kiloByte))
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB"
        }
        val gigaByte = megaByte / 1024
        if (gigaByte < 1) {
            val result2 = BigDecimal(java.lang.Double.toString(megaByte))
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB"
        }
        val teraBytes = gigaByte / 1024
        if (teraBytes < 1) {
            val result3 = BigDecimal(java.lang.Double.toString(gigaByte))
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB"
        }
        val result4 = BigDecimal(teraBytes)
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB"
    }

    /**
     * 删除文件
     */
    fun delete(file: File?): Boolean {
        if (file == null) {
            return false
        }
        if (file.isDirectory) {
            val children = file.list()
            for (c in children) {
                val success = delete(File(file, c))
                if (!success) {
                    return false
                }
            }
        }
        return file.delete()
    }

}