package com.linwei.cams.component.common.ktx

/**
 * Int转换为String
 */
fun Int?.idToString(): String {
    this?.takeIf {
        return ctx.getString(it)
    }
    return ""
}

/**
 * Int 转换为Color
 */
fun Int?.idToColor(): Int {
    this?.takeIf {
        return ctx.resources.getColor(it)
    }
    return -1
}

/**
 * 不为null且不空字符串判断
 */
fun String?.notNullAndEmpty(): Boolean {
    this?.takeIf {
        return it.isNotEmpty()
    }
    return false
}

/**
 * null或空字符串判断
 */
fun String?.isNullOrEmpty(): Boolean {
    this?.takeIf {
        return it.isEmpty()
    }
    return true
}

/**
 * 判断字符串数组是否为空
 */
fun isEmptyArraysParameter(params: Array<out String?>): Boolean {
    for (p: String? in params)
        if (p.isNullOrEmpty() || p == "null" || p == "NULL") {
            return true
        }
    return false
}


/**
 * 判断字符串是否为空
 */
fun isEmptyParameter(vararg params: String?): Boolean {
    for (p: String? in params)
        if (p.isNullOrEmpty() || p == "null" || p == "NULL") {
            return true
        }
    return false
}

/**
 * 判断集合是否为空，数据不为0
 */
fun List<Any>?.isNotNullOrSize(): Boolean {
    this?.let {
        if (it.isNotEmpty()) {
            return true
        }
    }
    return false
}

/**
 * 判断集合是否为空，数据不为0
 */
fun Array<*>?.isNotNullOrSize(): Boolean {
    this?.let {
        if (it.isNotEmpty()) {
            return true
        }
    }
    return false

}

