package com.linwei.cams.component.network.factory.rxjava

import com.linwei.cams.component.network.ApiCall
import com.linwei.cams.component.network.model.ApiResponse
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ApiCallAdapterFactory private constructor() : CallAdapter.Factory() {

    override fun get(
        returnType: Type, annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        val rawType = getRawType(returnType)
        if (rawType != ApiCall::class.java) {
            return null
        }
        require(returnType is ParameterizedType) { "Call return type must be parameterized as Call<Foo> or Call<? extends Foo>" }

        val responseType = getParameterUpperBound(0, returnType)
        return MapiCallAdapter<Any>(responseType, annotations)
    }

    /**
     * Mapi的call
     *
     * @param <R> 数据类型
     * @author Administrator
    </R> */
    inner class MapiCallAdapter<R> internal constructor(
        private val responseType: Type,
        private val annotations: Array<Annotation>
    ) : CallAdapter<ApiResponse<R>, ApiCall<R>> {

        override fun responseType(): Type {
            return ParameterizedTypeImpl(
                arrayOf(responseType), null,
                ApiResponse::class.java
            )
        }

        override fun adapt(call: Call<ApiResponse<R>>): ApiCall<R> {
            return ApiCall(annotations, call)
        }
    }

    companion object {
        fun create(): ApiCallAdapterFactory {
            return ApiCallAdapterFactory()
        }
    }
}