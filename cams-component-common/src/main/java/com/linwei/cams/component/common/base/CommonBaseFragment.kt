package com.linwei.cams.component.common.base

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.ktx.isSupportStatusBarDarkFont
import com.linwei.cams.component.common.R
import com.linwei.cams.component.common.opensource.ARouterManager
import com.linwei.cams.component.common.utils.EventBusUtils
import com.quyunshuo.androidbaseframemvvm.base.utils.status.ViewStatusHelper
import com.trello.rxlifecycle4.components.support.RxFragment
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.ParameterizedType

/**
 * 基类CommonBaseFragment
 */
abstract class CommonBaseFragment<VB : ViewBinding> : RxFragment() {

    protected lateinit var mViewBinding: VB

    protected lateinit var mContext: Context

    protected var mActivity: FragmentActivity? = null

    /**
     * 基础状态管理帮助类
     */
    protected var mBaseStatusHelper: ViewStatusHelper? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        mActivity = activity
        //在新版本的Fragment#onActivityCreated()废除.使用LifecycleObserver监听
        //onActivityCreated() 方法现已弃用。与 Fragment 视图有关的代码应在 onViewCreated()（在 onActivityCreated() 之前调用）中执行，而其他初始化代码应在 onCreate() 中执行。如需专门在 Activity 的 onCreate() 完成时接收回调，应在 onAttach() 中的 Activity 的 Lifecycle 上注册 LifeCycleObserver，并在收到 onCreate() 回调后将其移除。
        //版权声明：本文为CSDN博主「Ym Android开发工程师」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
        requireActivity().lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                onActivityCreated()
                owner.lifecycle.removeObserver(this)
            }
        })
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        context?.let {
            mContext = it
        }
        if (activity is FragmentActivity) {
            mActivity = activity
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        routerInjectBinding()
        eventBusBinding()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        onCreateViewExpand(inflater, container, savedInstanceState)
        if (hasViewBinding()) {
            return viewBindingLogic(inflater, container)
        }

        if (hasLayoutIdBinding()) {
            return inflater.inflate(
                getRootLayoutId(),
                container,
                false
            )
        }

        return dynamicFetchRootView(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreatedExpand(view, savedInstanceState)
        //注册状态帮助类
        mBaseStatusHelper = onRegisterStatusHelper()
        //恢复状态数据
        mBaseStatusHelper?.onRestoreInstanceStatus(savedInstanceState)

        initImmersionBar()
        initView()
        initData()
        initEvent()
    }

    /**
     * ViewBinding绑定逻辑
     */
    @Suppress("UNCHECKED_CAST")
    private fun viewBindingLogic(inflater: LayoutInflater, container: ViewGroup?): View? {
        val type: ParameterizedType = javaClass.genericSuperclass as ParameterizedType
        try {
            val cls = type.actualTypeArguments[0] as Class<*>
            val inflate = cls.getDeclaredMethod(
                "inflate",
                LayoutInflater::class.java,
                ViewGroup::class.java,
                Boolean::class.javaPrimitiveType
            )
            mViewBinding = inflate.invoke(null, inflater, container, false) as VB
            return mViewBinding.root
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * EventBus绑定
     */
    private fun eventBusBinding() {
        hasEventBus().takeIf { it }?.apply {
            EventBusUtils.register(mContext)
        }
    }

    /**
     * Router注入绑定
     */
    private fun routerInjectBinding() {
        hasInjectARouter().takeIf { it }?.apply {
            activity?.let {
                ARouterManager.inject(it)
            }
        }
    }

    protected open fun getRootLayoutId(): Int = -1

    protected open fun hasInjectARouter(): Boolean = false

    protected open fun hasViewBinding(): Boolean = true

    protected open fun hasLayoutIdBinding(): Boolean = false

    protected open fun hasEventBus(): Boolean = false

    protected open fun immersionBar(): Boolean = false

    protected open fun initImmersionBar() {
        if (immersionBar()) {
            ImmersionBar.with(this)
                .titleBar(R.id.stateBarView, false)
                .statusBarDarkFont(true)
                .keyboardEnable(true)
                .hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
                .init()
        }
    }

    protected open fun onCreateViewExpand(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
    }

    protected open fun dynamicFetchRootView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): View? = null

    protected abstract fun initView()

    protected abstract fun initData()

    protected abstract fun initEvent()

    /**
     * [Fragment]宿主[Activity]初始化结束调用
     */
    protected open fun onActivityCreated() {

    }

    protected open fun onViewCreatedExpand(view: View, savedInstanceState: Bundle?) {
    }

    /**
     * 保存状态
     */
    override fun onSaveInstanceState(outState: Bundle) {
        mBaseStatusHelper?.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    /**
     * 注册状态管理帮助类,子类重写此方法以注册帮助类。
     * 每一层都有可能有自己的状态管理帮助类，所以继承重写的时候，需要将super的对象传入自己的帮助类构造函数中
     */
    protected open fun onRegisterStatusHelper(): ViewStatusHelper? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        hasEventBus().takeIf { it }?.apply {
            EventBusUtils.unRegister(mContext)
        }
    }
}