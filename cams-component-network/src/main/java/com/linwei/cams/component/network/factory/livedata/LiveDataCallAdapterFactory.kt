package com.linwei.cams.component.network.factory.livedata

import android.util.Log
import androidx.lifecycle.LiveData
import com.linwei.cams.component.network.model.ApiResponse
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * LiveData转换适配器工厂类
 */
class LiveDataCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != LiveData::class.java) {
            return null
        }
        //获取第一个泛型类型
        val observableType = getParameterUpperBound(0, returnType as ParameterizedType)
        val rawType = getRawType(observableType)

        var isApiResponse = true
        if (rawType != ApiResponse::class.java) {
            //不是返回ResponseData类型的返回值
            isApiResponse = false
        }
        if (observableType !is ParameterizedType) {
            Log.e("TAG", "rawType = resource must be parameterized$rawType")
            //            throw new IllegalArgumentException("resource must be parameterized");
        }
        return LiveDataCallAdapter<String>(observableType, isApiResponse)
    }
}