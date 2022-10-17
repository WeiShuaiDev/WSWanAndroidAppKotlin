package com.linwei.cams.component.network

import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.linwei.cams.component.common.base.CommonBaseApplication
import com.linwei.cams.component.common.ktx.isNotNullOrSize
import com.linwei.cams.component.network.configuration.AdapterFactoryType
import com.linwei.cams.component.network.configuration.ApiConfiguration
import com.linwei.cams.component.network.factory.livedata.LiveDataCallAdapterFactory
import com.linwei.cams.component.network.factory.rxjava.ApiCallAdapterFactory
import com.linwei.cams.component.network.intercepter.SignInterceptor
import com.linwei.cams.component.network.service.ServiceWrap
import com.linwei.tool.utils.LoggingInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient private constructor(var apiConfiguration: ApiConfiguration?) {

    /**
     * OkHttpClient
     */
    private var mOkHttpClient: OkHttpClient

    private val mApiServiceMap: MutableMap<String, Any> = mutableMapOf()

    companion object {
        private var INSTANCE: ApiClient? = null

        /**
         * 链接时间
         */
        private val CONNECT_TIME_OUT: Long = 60

        /**
         * 读取时间
         */
        private val READ_TIME_OUT: Long = 60

        /**
         * 写入时间
         */
        private val WHITE_TIME_OUT: Long = 60

        @JvmStatic
        fun getInstance(apiConfiguration: ApiConfiguration? = null): ApiClient {
            return INSTANCE ?: ApiClient(apiConfiguration).apply {
                INSTANCE = this
            }
        }
    }

    init {
        val builder = OkHttpClient().newBuilder().apply {
            connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
            readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
            writeTimeout(WHITE_TIME_OUT, TimeUnit.SECONDS)

            retryOnConnectionFailure(true)  //设置出现错误进行重新连接

            fetchCookieJar()?.let {
                cookieJar(it)
            }
            takeIf { BuildConfig.DEBUG }.apply {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(httpLoggingInterceptor)
            }

            addNetworkInterceptor(LoggingInterceptor())

            addNetworkInterceptor(SignInterceptor())
        }
        this.mOkHttpClient = builder.build()
    }

    private var cookieJar: ClearableCookieJar? = null

    private fun fetchCookieJar(): ClearableCookieJar? {
        if (cookieJar == null) {
            cookieJar = PersistentCookieJar(
                SetCookieCache(),
                SharedPrefsCookiePersistor(CommonBaseApplication.application)
            )
        }
        return cookieJar
    }

    /**
     * 获取Service Api
     */
    fun <T> getService(proxy: ServiceWrap<T>): T {
        val host = proxy.getHost()
        val serviceClass: Class<T> = proxy.fetchRealService()

        val key = proxy.getModuleName() + "&" + proxy.getIdentify()

        if (mApiServiceMap.containsKey(key)) {
            mApiServiceMap.get(key)?.let {
                if (serviceClass.isInstance(it)) {
                    return serviceClass.cast(it)!!
                }
            }
        }
        // `adapterFactory`切换
        var adapterFactory: CallAdapter.Factory = RxJava3CallAdapterFactory.createSynchronous()
        if (apiConfiguration?.adapterFactoryType == AdapterFactoryType.LIVE_DATA_ADAPTER_FACTORY_TYPE) {
            adapterFactory = LiveDataCallAdapterFactory()
        }

        val builder = Retrofit.Builder()
            .baseUrl(host)
            .addCallAdapterFactory(adapterFactory)
            .addCallAdapterFactory(ApiCallAdapterFactory.create())

        apiConfiguration?.adapterFactoryList?.takeIf {
            it.isNotNullOrSize()
        }?.forEach {
            builder.addCallAdapterFactory(it)
        }
        val retrofit = builder.addConverterFactory(GsonConverterFactory.create())
            .client(mOkHttpClient)
            .build()

        return retrofit.create(serviceClass).apply {
            mApiServiceMap[key] = this!!
        }
    }
}