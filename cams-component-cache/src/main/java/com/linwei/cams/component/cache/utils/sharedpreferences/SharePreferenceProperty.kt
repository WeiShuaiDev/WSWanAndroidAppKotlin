package com.linwei.cams.component.cache.utils.sharedpreferences

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class SharePreferenceProperty<T>(
    val name: String,
    val default: T,
    prefName: String
) : ReadWriteProperty<Any?, T> {

    private val mSharePreferenceHelper: SharePreferenceHelper<T> =
        SharePreferenceHelper(prefName, default)

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return mSharePreferenceHelper.findPreference(findProperName(property));
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        mSharePreferenceHelper.putPreference(findProperName(property), value);
    }

    /**
     * 查询数据存储标识 `Key`
     * @param property
     */
    private fun findProperName(property: KProperty<*>): String =
        name.ifEmpty { property.name }

}
