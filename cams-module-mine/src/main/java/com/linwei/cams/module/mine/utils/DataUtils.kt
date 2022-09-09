package com.linwei.cams.module.mine.utils

import com.linwei.cams.module.mine.model.OpenSourceBean

object DataUtils {

    fun getOpenSourceList(): MutableList<OpenSourceBean> = mutableListOf<OpenSourceBean>().apply {
        add(
            OpenSourceBean(
                "Justson/AgentWeb",
                "AgentWeb is a powerful library based on Android WebView.",
                "https://github.com/Justson/AgentWeb"
            )
        )
        add(
            OpenSourceBean(
                "google/flexbox-layout",
                "Flexbox for Android",
                "https://github.com/google/flexbox-layout"
            )
        )
        add(
            OpenSourceBean(
                "alibaba/vlayout",
                "Project vlayout is a powerfull LayoutManager extension for RecyclerView, it provides a group of layouts for RecyclerView. Make it able to handle a complicate situation when grid, list and other layouts in the same recyclerview.",
                "https://github.com/alibaba/vlayout"
            )
        )
        add(
            OpenSourceBean(
                "square/okhttp",
                "Square’s meticulous HTTP client for Java and Kotlin.",
                "https://github.com/square/okhttp"
            )
        )

        add(
            OpenSourceBean(
                "square/retrofit",
                "A type-safe HTTP client for Android and the JVM",
                "https://github.com/square/retrofit"
            )
        )
        add(
            OpenSourceBean(
                "ReactiveX/RxJava",
                "RxJava – Reactive Extensions for the JVM – a library for composing asynchronous and event-based programs using observable sequences for the Java VM.",
                "https://github.com/ReactiveX/RxJava"
            )
        )
        add(
            OpenSourceBean(
                "ReactiveX/RxAndroid",
                "RxJava bindings for Android",
                "https://github.com/ReactiveX/RxAndroid"
            )
        )
        add(
            OpenSourceBean(
                "greenrobot/EventBus",
                "Event bus for Android and Java that simplifies communication between Activities, Fragments, Threads, Services, etc. Less code, better quality.",
                "https://github.com/greenrobot/EventBus"
            )
        )
        add(
            OpenSourceBean(
                "bumptech/glide",
                "An image loading and caching library for Android focused on smooth scrolling",
                "https://github.com/bumptech/glide"
            )
        )
        add(
            OpenSourceBean(
                "alibaba/ARouter",
                "A framework for assisting in the renovation of Android componentization (帮助 Android App 进行组件化改造的路由框架)",
                "https://github.com/alibaba/ARouter"
            )
        )
        add(
            OpenSourceBean(
                "gyf-dev/ImmersionBar",
                "android 4.4以上沉浸式状态栏和沉浸式导航栏管理，适配横竖屏切换、刘海屏、软键盘弹出等问题，可以修改状态栏字体颜色和导航栏图标颜色，以及不可修改字体颜色手机的适配，适用于Activity、Fragment、DialogFragment、Dialog，PopupWindow，一句代码轻松实现，以及对bar的其他设置，详见README。",
                "https://github.com/gyf-dev/ImmersionBar"
            )
        )
        add(
            OpenSourceBean(
                "JeasonWong/Particle",
                "手摸手教你用Canvas实现简单粒子动画",
                "https://github.com/JeasonWong/Particle"
            )
        )
        add(
            OpenSourceBean(
                "youth5201314/banner",
                "一款2.0版本Banner ！Android广告图片轮播控件，内部基于ViewPager2实现，Indicator和UI都可以自定义",
                "https://github.com/youth5201314/banner"
            )
        )
        add(
            OpenSourceBean(
                "KingJA/LoadSir",
                "A lightweight, good expandability Android library used for displaying different pages like loading, error, empty, timeout or even your custom page when you load a page.(优雅地处理加载中，重试，无数据等)",
                "https://github.com/KingJA/LoadSir"
            )
        )
        add(
            OpenSourceBean(
                "CymChad/BaseRecyclerViewAdapterHelper",
                "BRVAH:Powerful and flexible RecyclerAdapter",
                "https://github.com/CymChad/BaseRecyclerViewAdapterHelper"
            )
        )
        add(
            OpenSourceBean(
                "hackware1993/MagicIndicator",
                "A powerful, customizable and extensible ViewPager indicator framework. As the best alternative of ViewPagerIndicator, TabLayout and PagerSlidingTabStrip.",
                "https://github.com/hackware1993/MagicIndicator"
            )
        )
        add(
            OpenSourceBean(
                "Tencent/MMKV",
                "MMKV 是基于 mmap 内存映射的 key-value 组件，底层序列化/反序列化使用 protobuf 实现，性能高，稳定性强。从 2015 年中至今在微信上使用，其性能和稳定性经过了时间的验证。近期也已移植到 Android / macOS / Win32 / POSIX 平台，一并开源。",
                "https://github.com/Tencent/MMKV"
            )
        )
        add(
            OpenSourceBean(
                "scwang90/SmartRefreshLayout",
                "下拉刷新、上拉加载、二级刷新、淘宝二楼、RefreshLayout、OverScroll，Android智能下拉刷新框架，支持越界回弹、越界拖动，具有极强的扩展性，集成了几十种炫酷的Header和 Footer。",
                "https://github.com/scwang90/SmartRefreshLayout"
            )
        )
        add(
            OpenSourceBean(
                "franmontiel/PersistentCookieJar",
                "A persistent CookieJar implementation for OkHttp 3 based on SharedPreferences.",
                "https://github.com/franmontiel/PersistentCookieJar"
            )
        )
    }
}