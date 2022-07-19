package com.linwei.cams.framework.mvi.ktx

import androidx.annotation.MainThread
import androidx.lifecycle.*


fun <X> LiveData<X>.onlyUntilChangeds(): LiveData<X> =
    onlyUntilChanged(this)


/**
 * Creates a new [LiveData] object that does not emit a value until the source LiveData
 * value has been changed.  The value is considered changed if `equals()` yields
 * `false`.
 *
 * @param source the input [LiveData]
 * @param <X>    the generic type parameter of `source`
 * @return       a new [LiveData] of type `X`
</X> */
@MainThread
fun <X> onlyUntilChanged(source: LiveData<X>): LiveData<X> {
    val outputLiveData = MediatorLiveData<X>()
    outputLiveData.addSource(source, object : Observer<X> {
        var mFirstTime = true
        override fun onChanged(currentValue: X) {
            val previousValue = outputLiveData.value
            if (mFirstTime
                || previousValue == null && currentValue != null
                || previousValue != null && previousValue != currentValue
            ) {
                mFirstTime = false
                outputLiveData.value = currentValue!!
            }
        }
    })
    return outputLiveData
}
