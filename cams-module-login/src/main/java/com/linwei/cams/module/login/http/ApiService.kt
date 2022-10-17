package com.linwei.cams.module.login.http

import com.linwei.cams.component.network.ApiCall
import com.linwei.cams.component.network.annotation.RetryCount
import com.linwei.cams.component.network.annotation.RetryDelay
import com.linwei.cams.component.network.annotation.RetryIncreaseDelay
import com.linwei.cams.service.base.model.UserInfoBean
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiService {

    /**
     * 登录
     *
     * @param userName 账号
     * @param passWord 密码
     * @return
     */
    @RetryCount(3)
    @RetryDelay(1000)
    @RetryIncreaseDelay(1000)
    @FormUrlEncoded
    @POST("user/login")
    fun login(
        @Field("username") userName: String?,
        @Field("password") passWord: String?
    ): ApiCall<UserInfoBean>

    /**
     * 注册
     * @param userName 账号
     * @param passWord 密码
     * @param rePassWord 确认密码
     * @return
     */
    @FormUrlEncoded
    @POST("user/register")
    fun register(
        @Field("username") userName: String?,
        @Field("password") passWord: String?,
        @Field("repassword") rePassWord: String?
    ): ApiCall<UserInfoBean>

    /**
     * 退出登录
     */
    @GET("user/logout/json")
    fun logout(): ApiCall<Any>

}