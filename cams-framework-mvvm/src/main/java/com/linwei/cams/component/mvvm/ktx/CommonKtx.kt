package com.linwei.cams.component.mvvm.ktx

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


fun <T> MutableLiveData<T>.asLiveData(): LiveData<T> {
    return this
}

