# WSWanAndroidAppKotlin简介

🔥 🔥 🔥 WSWanAndroidAppKotlin项目，使用自主开发  [CamsModular](https://github.com/WeiShuaiDev/CamsModular)包，内部提供组件化工具，支持每个业务模块切换到指定`MVP`、`MVVM`、`AAC`架构(`framework`)，同时可以根据具体业务进行模块化划分。开发该项目主要目的尝试在实际开发中使用[CamsModular](https://github.com/WeiShuaiDev/CamsModular)库，是否会出现不兼容问题，通过这次项目，也发现一些问题，不过已经进行优化处理。

![project_structure](https://github.com/WeiShuaiDev/WSWanAndroidAppKotlin/blob/main/screenshots/project_structure.png?raw=true)

## 一、项目简介

​	`WSWanAndroidAppKotlin`项目所有接口数据来源于`WanAndroid`平台，App中主要分为6个模块:首页、项目、广场、公众号、我的、登录，每个模块通过ARouter路由跳转，模块之间交互进行隔离处理，通过对应模块service接口调用。每个模块可以进行单独运行调试，也可以加载所有模块到容器中，进行项目级运行。

## 二、项目结构

├── cams-component-cache    缓存

├── cams-component-common  工具

├── cams-component-database 数据库

├── cams-component-image  图片

├── cams-component-network 网络

├── cams-component-web     web

├── cams-component-weight   组件

├── cams-framework-mvi  mvi架构

├── cams-framework-mvp  mvp架构

├── cams-framework-mvvm  mvvm架构

├── cams-module-home  首页模块

├── cams-module-login  登录模块

├── cams-module-main  模块容器

├── cams-module-mine 我的模块

├── cams-module-project 项目模块

├── cams-module-publis  公众号模块

├── cams-module-square  广场模块

├── cams-service-base   基础service

├── cams-service-home    首页 service

├── cams-service-login   登录 service

├── cams-service-mine  我的 service

├── cams-service-project  项目 service

├── cams-service-publis  公众号 service

├── cams-service-square  广场 service

├── data    签名

├── gradle

│   ├──app

│   │   ├──app_build.gradle  编译签名gradle

│   │   ├──app_config.gradle  配置gradle

│   │   ├──app_flavors.gradle  多渠道打包gradle

│   ├──wapper

│   ├── global_component_config.gradle   component gradle

│   ├── global_module_config.gradle  module gradle

│   ├── global_service_config.gradle  service gradle

├── res    签名

├── src    容器启动页面源码

## 三、依赖明细

```groovy
 dependencies = [
            //----------------------------------------AndroidX----------------------------------------------------------
            "appcompat"                           : "androidx.appcompat:appcompat:${version.appcompatVersion}",
            "core-ktx"                            : "androidx.core:core-ktx:1.3.1",
            "kotlin-reflect"                      : "org.jetbrains.kotlin:kotlin-reflect:1.3.72",
            "material"                            : "com.google.android.material:material:${version.materialVersion}",
            "constraintlayout"                    : "androidx.constraintlayout:constraintlayout:${version.constraintlayoutVersion}",
            "activity-ktx"                        : "androidx.activity:activity-ktx:${version.activityKtxVersion}",
            "fragment-ktx"                        : "androidx.fragment:fragment-ktx:${version.fragmentKtxVersion}",

            //----------------------------------------Retrofit------------------------------------------------------------
            "retrofit"                            : "com.squareup.retrofit2:retrofit:${version.retrofitVersion}",
            "adapter-rxjava"                      : "com.squareup.retrofit2:adapter-rxjava3:${version.retrofitVersion}",
            "retrofit-converter-gson"             : "com.squareup.retrofit2:converter-gson:${version.retrofitVersion}",
            "logging-interceptor"                 : "com.squareup.okhttp3:logging-interceptor:${version.loggingInterceptorVersion}",

            //----------------------------------------OkHttp3------------------------------------------------------------
            "okhttp3"                             : "com.squareup.okhttp3:okhttp:${version.okHttpVersion}",
            "okhttp3-logging-interceptor"         : "com.squareup.okhttp3:logging-interceptor:${version.okHttpVersion}",
            "persistentCookieJar"                 : "com.github.franmontiel:PersistentCookieJar:${version.persistentCookieJarVersion}",

            //----------------------------------------GSON---------------------------------------------------------------
            "gson"                                : "com.google.code.gson:gson:${version.gsonVersion}",

            //----------------------------------------XTOOL---------------------------------------------------------------
            "xtool"                               : "io.github.weishuaidev:xtool:${version.xtoolVersion}",

            //----------------------------------------LOG-----------------------------------------------------------------
            "logan"                               : "com.dianping.android.sdk:logan:${version.loganVersion}",

            //----------------------------------------RxCache2------------------------------------------------------------
            "rxcache2"                            : "com.github.VictorAlbertos.RxCache:runtime:${version.rxcacheVersion}",

            //----------------------------------------RxLifecycle4--------------------------------------------------------
            // RxLifecycle基础库
            "rxlifecycle4"                        : "com.trello.rxlifecycle4:rxlifecycle:${version.rxlifecycle3SdkVersion}",
            // 支持Kotlin语法的RxLifecycle基础库
            "rxlifecycle4-kotlin"                 : "com.trello.rxlifecycle4:rxlifecycle-kotlin:${version.rxlifecycle3SdkVersion}",
            // Android使用的库，里面使用了Android的生命周期方法
            // 内部引用了基础库，如果使用此库则无需再引用基础库
            "rxlifecycle-android"                 : "com.trello.rxlifecycle2:rxlifecycle-android:${version.rxlifecycle3SdkVersion}",
            // 支持Kotlin语法的Android库
            "rxlifecycle-android-lifecycle-kotlin": "com.trello.rxlifecycle2:rxlifecycle-android-lifecycle-kotlin:${version.rxlifecycle3SdkVersion}",
            // Android组件库，里面定义了例如RxAppCompatActivity、RxFragment之类的Android组件
            // 内部引用了基础库和Android库，如果使用此库则无需再重复引用
            "rxlifecycle4-components"             : "com.trello.rxlifecycle4:rxlifecycle-components:${version.rxlifecycle3SdkVersion}",
            // Android使用的库，继承NaviActivity使用
            "rxlifecycle-navi"                    : "com.trello.rxlifecycle2:rxlifecycle-navi:${version.rxlifecycle3SdkVersion}",
            // Android使用的库，继承LifecycleActivity使用
            //需要引入Google的仓库支持，用法和rxlifecycle-navi类似
            "rxlifecycle-android-lifecycle"       : "com.trello.rxlifecycle2:rxlifecycle-android-lifecycle:${version.rxlifecycle3SdkVersion}",

            //----------------------------------------Rxjava3-------------------------------------------------------------
            "rxjava3"                             : "io.reactivex.rxjava3:rxjava:${version.rxjava3Version}",
            "rxandroid3"                          : "io.reactivex.rxjava3:rxandroid:${version.rxandroidVersion}",
            "rxkotlin3"                           : "io.reactivex.rxjava3:rxkotlin:${version.rxkotlinVersion}",

            //----------------------------------------ARouter-------------------------------------------------------------
            "arouter-api"                         : "com.alibaba:arouter-api:${version.arouterVersion}",
            "arouter-compiler"                    : "com.alibaba:arouter-compiler:${version.arouterVersion}",

            //----------------------------------------Multidex------------------------------------------------------------
            "multidex"                            : "com.android.support:multidex:${version.multidexVersion}",

            //----------------------------------------Coil----------------------------------------------------------------
            "coil"                                : "io.coil-kt:coil:${version.coilVersion}",
            "coil-gif"                            : "io.coil-kt:coil-gif:${version.coilVersion}",
            "coil-svg"                            : "io.coil-kt:coil-svg:${version.coilVersion}",
            "coil-video"                          : "io.coil-kt:coil-video:${version.coilVersion}",

            //----------------------------------------Glide----------------------------------------------------------------
            "glide"                               : "com.github.bumptech.glide:glide:${version.glideVersion}",
            "palette"                             : "com.android.support:palette-v7:${version.paletteVersion}",

            //----------------------------------------StateLayout---------------------------------------------------------
            "stateLayout"                         : "com.github.liangjingkanji:StateLayout:${version.stateLayoutVersion}",

            //----------------------------------------Immersionbar--------------------------------------------------------
            "immersionbar"                        : "com.geyifeng.immersionbar:immersionbar:${version.immersionbarVersion}",
            "immersionbar-ktx"                    : "com.geyifeng.immersionbar:immersionbar-ktx:${version.immersionbarVersion}",

            //----------------------------------------AndroidPicker--------------------------------------------------------
            "androidPicker-common"                : "com.github.gzu-liyujiang.AndroidPicker:Common:${version.androidPickerVersion}",//所有选择器的基础窗体（用于自定义弹窗）
            "androidPicker-wheelView"             : "com.github.gzu-liyujiang.AndroidPicker:WheelView:${version.androidPickerVersion}",//滚轮选择器的滚轮控件（用于自定义滚轮选择器
            "androidPicker-wheelPicker"           : "com.github.gzu-liyujiang.AndroidPicker:WheelPicker:${version.androidPickerVersion}",  //单项/数字、二三级联动、日期/时间等滚轮选择器
            "androidPicker-addressPicker"         : "com.github.gzu-liyujiang.AndroidPicker:AddressPicker:${version.androidPickerVersion}",  //省市区地址选择器
            "androidPicker-filePicker"            : "com.github.gzu-liyujiang.AndroidPicker:FilePicker:${version.androidPickerVersion}",  //文件/目录选择器
            "androidPicker-colorPicker"           : "com.github.gzu-liyujiang.AndroidPicker:ColorPicker:${version.androidPickerVersion}", //颜色选择器
            "androidPicker-calendarPicker"        : "com.github.gzu-liyujiang.AndroidPicker:CalendarPicker:${version.androidPickerVersion}",  //日历日期选择器
            "androidPicker-imagePicker"           : "com.github.gzu-liyujiang.AndroidPicker:ImagePicker:${version.androidPickerVersion}",  //图片选择器

            //------------------------------------------MMKV----------------------------------------------------------------
            "mmkv"                                : "com.tencent:mmkv-static:${version.mmkvVersion}",

            //----------------------------------------屏幕适配-------------------------------------------------------------
            "autosize"                            : "com.github.JessYanCoding:AndroidAutoSize:${version.autosizeVersion}",

            //----------------------------------------View-----------------------------------------------------------------
            "basePopup"                           : "com.github.razerdp:BasePopup:${version.basePopupVersion}",

            "dialogX"                             : "com.github.kongzue.DialogX:DialogX:${version.dialogXVersion}",
            "dialogX-ios-style"                   : "com.github.kongzue.DialogX:DialogXIOSStyle:${version.dialogXVersion}",
            "dialogX-kongzue-style"               : "com.github.kongzue.DialogX:DialogXKongzueStyle:${version.dialogXVersion}",
            "dialogX-miui-style"                  : "com.github.kongzue.DialogX:DialogXMIUIStyle:${version.dialogXVersion}",
            "dialogX-material-you"                : "com.github.kongzue.DialogX:DialogXMaterialYou:${version.dialogXVersion}",

            "baseRecyclerViewAdapterHelper"       : "com.github.CymChad:BaseRecyclerViewAdapterHelper:${version.recyclerViewAdapterVersion}",

            "magicIndicator"                      : "com.github.hackware1993:MagicIndicator:${version.magicIndicatorVersion}",

            "flexbox"                             : "com.google.android.flexbox:flexbox:${version.flexboxVersion}",

            "banner"                              : "io.github.youth5201314:banner:${version.bannerVersion}",

            "tangram"                             : "com.alibaba.android:tangram:1.0.0@aar",

            "vlayout"                             : "com.alibaba.android:vlayout:1.2.8@aar",

            "slidingpanellayout"                  : "androidx.slidingpanelayout:slidingpanelayout:${version.slidingpanelayoutVersion}",

            "refreshLayoutKernel"                 : "io.github.scwang90:refresh-layout-kernel:${version.refreshVersion}",      //核心必须依赖
            "refreshHeaderTwoLevel"               : "io.github.scwang90:refresh-header-two-level:${version.refreshVersion}",   //二级刷新头

            "nineoldandroids"                     : "com.nineoldandroids:library:${version.nineoldandroidsVersion}",

            "recyclerviewX"                       : "com.yanzhenjie.recyclerview:x:${version.recyclerViewXVersion}",

            //----------------------------------------事件处理-----------------------------------------------------------------
            "eventbus"                            : "org.greenrobot:eventbus:${version.eventbusVersion}",
            "eventbusAPT"                         : "org.greenrobot:eventbus-annotation-processor:${version.eventbusVersion}",

            //----------------------------------------请求权限-----------------------------------------------------------------
            "permissionX"                         : "com.permissionx.guolindev:permissionx:${version.permissionXVersion}",

            //----------------------------------------Leakcanary--------------------------------------------------------------
            "leakcanary-android"                  : "com.squareup.leakcanary:leakcanary-android:${version.leakcanaryAndroidVersion}",

            //----------------------------------------数据库-------------------------------------------------------------------
            "glance"                              : "com.guolindev.glance:glance: ${version.glanceVersion}",
            "debug-db"                            : "com.amitshekhar.android:debug-db:${version.debugDbVersion}",

            //----------------------------------------AutoService------------------------------------------------------------
            "auto-service"                        : "com.google.auto.service:auto-service:${version.autoServiceVersion}",
            "auto-service-annotations"            : "com.google.auto.service:auto-service-annotations:${version.autoServiceVersion}",

            //----------------------------------------Sdk--------------------------------------------------------------------
            "crashreport"                         : "com.tencent.bugly:crashreport:${version.tencentBuglyVersion}",
            "nativecrashreport"                   : "com.tencent.bugly:nativecrashreport:${version.tencentBuglyNativeVersion}",
            "tencent-tbs-x5"                      : "com.tencent.tbs:tbssdk:${version.tencentTBSX5Version}",
            "agentweb-core"                       : "com.github.Justson.AgentWeb:agentweb-core:v5.0.6-androidx",
            "agentweb-filechooser"                : "com.github.Justson.AgentWeb:agentweb-filechooser:v5.0.6-androidx",
            "agentweb-downloader"                 : "com.github.Justson:Downloader:v5.0.4-androidx",

            //----------------------------------------Jetpack----------------------------------------------------------------
            //viewModel
            "lifecycle-viewmodel-ktx"             : "androidx.lifecycle:lifecycle-viewmodel-ktx:${version.lifecycleVersion}",
            "lifecycle-viewmodel-savedstate"      : "androidx.lifecycle:lifecycle-viewmodel-savedstate:${version.lifecycleVersion}",
            //LiveData
            "lifecycle-livedata-ktx"              : "androidx.lifecycle:lifecycle-livedata-ktx:${version.lifecycleVersion}",
            "lifecycle-common-java8"              : "androidx.lifecycle:lifecycle-common-java8:${version.lifecycleVersion}",
            "lifecycle-compiler"                  : "androidx.lifecycle:lifecycle-compiler:${version.lifecycleVersion}",
            //lifecycle
            "lifecycle-runtime-ktx"               : "androidx.lifecycle:lifecycle-runtime-ktx:${version.lifecycleVersion}",
            //hilt
            "hilt-android"                        : "com.google.dagger:hilt-android:${version.hiltVersion}",
            "hilt-compiler"                       : "com.google.dagger:hilt-compiler:${version.hiltVersion}",
            "hilt-androidx-compiler"              : "androidx.hilt:hilt-compiler:${version.hiltAndroidXVersion}",
            // Hilt testing dependency
            "hilt-android-testing"                : "com.google.dagger:hilt-android-testing:${version.hiltVersion}",
            // Make Hilt generate code in the androidTest folder
            "hilt-android-compiler"               : "com.google.dagger:hilt-android-compiler:${version.hiltVersion}",
            //Root
            "room"                                : "androidx.room:room-runtime:${version.lifecycleVersion}",
            "roomCompiler"                        : "androidx.room:room-compiler:${version.lifecycleVersion}",
            //Navigation
            "navigation"                          : "androidx.navigation:navigation-fragment-ktx:${version.lifecycleVersion}",
            "navigationUI"                        : "androidx.navigation:navigation-ui-ktx:${version.lifecycleVersion}",
            //work
            "work"                                : "androidx.work:work-runtime:${version.lifecycleVersion}",
            //Preferences Datastore
            "datastore-preferences"               : "androidx.datastore:datastore-preferences:${version.datastoreVersion}",
            "datastore-preferences-rxjava3"       : "androidx.datastore:datastore-preferences-rxjava3:${version.datastoreVersion}",
            "datastore-preferences-core"          : "androidx.datastore:datastore-preferences-core:${version.datastoreVersion}",
            //Proto DataStore
            "datastore"                           : "androidx.datastore:datastore:${version.datastoreVersion}",
            "datastore-rxjava3"                   : "androidx.datastore:datastore-rxjava3:${version.datastoreVersion}",
            "datastore-core"                      : "androidx.datastore:datastore-core:${version.datastoreVersion}",
            //Protobuf JavaLite
            "protobuf-javalite"                   : "com.google.protobuf:protobuf-javalite:${version.protobufJavaliteVersion}",
            //WindowManager
            "window-manager"                      : "androidx.window:window:${version.windowManagerVersion}",
            "window-manager-java"                 : "androidx.window:window-java:${version.windowManagerVersion}",
            "window-manager-java2"                : "androidx.window:window-rxjava2:${version.windowManagerVersion}",
            "window-manager-java3"                : "androidx.window:window-rxjava3:${version.windowManagerVersion}",
            //Slidingpanelayout
            "slidingpanelayout"                   : "androidx.slidingpanelayout:slidingpanelayout:${version.slidingpanelayoutVersion}",
            //Coroutines
            "kotlinx-coroutines-core"             : "org.jetbrains.kotlinx:kotlinx-coroutines-core:${version.coroutinesVersion}",
            "kotlinx-coroutines-android"          : "org.jetbrains.kotlinx:kotlinx-coroutines-android:${version.coroutinesVersion}",

            //----------------------------------------Test---------------------------------------------------------------------
            "junit"                               : "junit:junit:4.13.2",
            "test-junit"                          : "androidx.test.ext:junit:1.1.2",
            "espresso-core"                       : "androidx.test.espresso:espresso-core:3.3.0"
    ]
```