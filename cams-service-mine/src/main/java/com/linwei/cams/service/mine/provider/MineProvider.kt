package com.linwei.cams.service.mine.provider

import com.alibaba.android.arouter.facade.template.IProvider
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.base.model.Page
import com.linwei.cams.service.base.model.UserInfoBean
import com.linwei.cams.service.mine.model.RankBean

interface MineProvider : IProvider {

    fun collectStatus(id: Int, callback: ResponseCallback<Any>)

    fun unCollectStatus(id: Int, callback: ResponseCallback<Any>)

    fun fetchIntegralData(callback: ResponseCallback<UserInfoBean>)

    fun fetchListIntegralData(page:Int,callback: ResponseCallback<Page<RankBean>>)

}