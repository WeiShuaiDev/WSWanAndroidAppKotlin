package com.linwei.cams.component.cache.utils.sharedpreferences

import kotlin.reflect.jvm.jvmName

inline fun <reified R, T> R.pref(default: T) = SharePreferenceProperty("", default, R::class.jvmName)