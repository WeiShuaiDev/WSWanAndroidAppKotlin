package com.linwei.cams.component.mvvm.ktx

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.linwei.cams.component.network.model.ApiResponse


fun <T> MutableLiveData<T>.asLiveData(): LiveData<T> {
    return this
}


