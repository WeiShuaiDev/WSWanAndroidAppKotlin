package com.linwei.cams.service.mine.provider

import com.alibaba.android.arouter.facade.template.IProvider
import com.linwei.cams.service.base.callback.ResponseCallback

interface MineProvider : IProvider {

    fun collectStatus(id:Int,callback: ResponseCallback<Any>)

    fun unCollectStatus(id:Int,callback: ResponseCallback<Any>)

}