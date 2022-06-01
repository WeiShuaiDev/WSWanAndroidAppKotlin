package com.linwei.cams.component.network.configuration

import retrofit2.CallAdapter

/**
 * 网络环境切换
 */
class ApiConfiguration(builder: Builder) {
    val apiEnv = builder.apiEnv

    val adapterFactoryType = builder.adapterFactoryType

    val adapterFactoryList = builder.adapterFactoryList

    companion object {

        fun builder(): Builder {
            return Builder()
        }

        class Builder {
            var apiEnv: ApiEnv? = ApiEnv.ENV_DEBUG

            fun apiEnv(apiEnv: ApiEnv): Builder {
                this.apiEnv = apiEnv
                return this
            }

            var adapterFactoryType: AdapterFactoryType? =
                AdapterFactoryType.RXJAVA_ADAPTER_FACTORY_TYPE

            fun adapterFactory(adapterFactoryType: AdapterFactoryType): Builder {
                this.adapterFactoryType = adapterFactoryType
                return this
            }

            var adapterFactoryList: List<CallAdapter.Factory> = mutableListOf()

            fun adapterFactoryList(adapterFactoryList: List<CallAdapter.Factory>): Builder {
                this.adapterFactoryList = adapterFactoryList
                return this
            }

            fun build(): ApiConfiguration {
                return ApiConfiguration(this)
            }
        }
    }

}
