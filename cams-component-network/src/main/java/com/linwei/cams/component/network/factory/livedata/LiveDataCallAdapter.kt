package com.linwei.cams.component.network.factory.livedata

import androidx.lifecycle.LiveData
import com.linwei.cams.component.common.ktx.notNullAndEmpty
import com.linwei.cams.component.network.ApiConstants
import com.linwei.cams.component.network.model.ApiResponse
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

/**
 * LiveData转换适配器
 */
class LiveDataCallAdapter<T> internal constructor(
    private val responseType: Type,
    private val isApiResponse: Boolean
) : CallAdapter<T, LiveData<T>> {
    override fun responseType(): Type = responseType

    override fun adapt(call: Call<T>): LiveData<T> {
        return MyLiveData(call, isApiResponse)
    }

    private class MyLiveData<T>(
        private val call: Call<T>,
        private val isApiResponse: Boolean
    ) : LiveData<T>() {

        private val operateStatus = AtomicBoolean(false)

        override fun onActive() {
            super.onActive()
            val bool = operateStatus.compareAndSet(false, true)
            if (bool) {
                call.enqueue(object : Callback<T> {
                    override fun onResponse(call: Call<T>, response: Response<T>) {
                        val body = response.body()
                        postValue(body)
                    }

                    @Suppress("UNCHECKED_CAST")
                    override fun onFailure(call: Call<T>, t: Throwable) {
                        t.message?.let {
                            if(it.notNullAndEmpty()){
                                if (isApiResponse) {
                                    postValue(ApiResponse(ApiConstants.UNKNOWN, it, null) as T)
                                } else {
                                    postValue(t.message as T)
                                }
                            }
                        }
                    }
                })
            }
        }
    }
}