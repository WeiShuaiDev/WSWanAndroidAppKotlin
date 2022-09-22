package com.linwei.cams.component.cache

import com.linwei.cams.component.cache.sharedpreferences.SharePreferenceProperty
import kotlin.reflect.jvm.jvmName

inline fun <reified R, T> R.pref(default: T) = SharePreferenceProperty("", default, R::class.jvmName)