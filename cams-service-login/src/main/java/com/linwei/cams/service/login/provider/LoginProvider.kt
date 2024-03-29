package com.linwei.cams.service.login.provider

import com.alibaba.android.arouter.facade.template.IProvider
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.base.model.UserInfoBean

interface LoginProvider : IProvider {

    fun login(userName: String, passWord: String, callback: ResponseCallback<UserInfoBean>)

    fun register(userName: String, passWord: String,rePassWord:String, callback: ResponseCallback<UserInfoBean>)

    fun logout(callback: ResponseCallback<Any>)

}