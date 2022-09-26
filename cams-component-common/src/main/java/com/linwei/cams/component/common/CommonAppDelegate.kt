package com.linwei.cams.component.common

import android.app.Application
import android.content.Context
import com.google.auto.service.AutoService
import com.kongzue.dialogx.DialogX
import com.kongzue.dialogx.interfaces.BaseDialog
import com.kongzue.dialogx.interfaces.DialogLifecycleCallback
import com.kongzue.dialogx.style.MaterialStyle
import com.kongzue.dialogx.util.InputInfo
import com.kongzue.dialogx.util.TextInfo
import com.linwei.cams.component.common.app.AppDelegate
import com.linwei.cams.component.common.ktx.idToColor
import com.linwei.cams.component.common.ktx.idToString
import com.linwei.cams.component.common.opensource.ARouterManager
import com.linwei.cams.component.common.utils.LogUtils
import com.linwei.cams.component.common.utils.ProcessUtils
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.tencent.bugly.crashreport.CrashReport

@AutoService(AppDelegate::class)
class CommonAppDelegate : AppDelegate {

    private lateinit var mContext: Context

    private lateinit var mApplication: Application

    /**
     * 同[Application.attachBaseContext]
     * @param context [Context]
     */
    override fun onAttachBaseContext(context: Context) {
        this.mContext = context
    }

    /**
     * 同[Application.onCreate]
     * @param application [Application]
     */
    override fun onCreate(application: Application) {
        this.mApplication = application
    }

    /**
     * 同[Application.onTerminate]
     * @param application [Application]
     */
    override fun onTerminate(application: Application) {
    }

    /**
     * 同[Application.onLowMemory]低内存时执行
     * @param application [Application]
     */
    override fun onLowMemory(application: Application) {
    }

    /**
     * 同[Application.onTrimMemory]清理内存时执行
     * @param level [Int]
     */
    override fun onTrimMemory(level: Int) {
    }

    /**
     * 主线程前台初始化
     * @return MutableList<() -> String> 初始化方法集合
     */
    override fun initByFrontDesk(): MutableList<() -> String> {
        val list = mutableListOf<() -> String>()
        // 以下只需要在主进程当中初始化 按需要调整
        if (ProcessUtils.isMainProcess(mContext)) {
            list.add { initARouter() }
        }
        list.add {
            initTencentBugly()
        }
        return list
    }

    /**
     * 不需要立即初始化的放在这里进行后台初始化
     */
    override fun initByBackstage() {
        initSmartRefreshLayout()
        initDialogX()
    }

    /**
     * 初始化 SmartRefreshLayout
     */
    private fun initSmartRefreshLayout() {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context: Context?, layout: RefreshLayout? ->
            ClassicsHeader(
                mApplication
            )
        }
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context: Context?, layout: RefreshLayout? ->
            ClassicsFooter(
                mApplication
            )
        }
    }

    /**
     * 初始化 DialogX
     */
    private fun initDialogX(): String {
        //开启调试模式，在部分情况下会使用 Log 输出日志信息
        DialogX.DEBUGMODE = true
        //设置主题样式
        DialogX.globalStyle = MaterialStyle.style()
        //设置亮色/暗色（在启动下一个对话框时生效）
        DialogX.globalTheme = DialogX.THEME.LIGHT
        //设置对话框最大宽度（单位为像素）
        DialogX.dialogMaxWidth = 1920
        //设置 InputDialog 自动弹出键盘
        DialogX.autoShowInputKeyboard = true
        //限制 PopTip 一次只显示一个实例（关闭后可以同时弹出多个 PopTip）
        DialogX.onlyOnePopTip = true

        //设置对话框默认按钮文本字体样式
        DialogX.buttonTextInfo =
            TextInfo().setFontColor(R.color.colorGlobalGrayText.idToColor()).setFontSize(14).setFontSizeUnit(
                TextInfo.FONT_SIZE_UNIT.SP
            )

        //设置对话框默认确定按钮文字样式
        DialogX.okButtonTextInfo = TextInfo()
            .setBold(true).setFontColor(R.color.colorPrimaryText.idToColor()).setFontSize(14).setFontSizeUnit(
                TextInfo.FONT_SIZE_UNIT.SP
            )

        //设置对话框默认标题文字样式
        DialogX.titleTextInfo = TextInfo()
            .setBold(true).setFontColor(R.color.colorPrimaryText.idToColor()).setFontSize(16).setFontSizeUnit(
                TextInfo.FONT_SIZE_UNIT.SP
            )

        //设置对话框默认内容文字样式
        DialogX.messageTextInfo = TextInfo()
            .setFontColor(R.color.colorPrimaryText.idToColor()).setFontSize(14).setFontSizeUnit(
                TextInfo.FONT_SIZE_UNIT.SP
            )

        //设置默认 WaitDialog 和 TipDialog 文字样式
        DialogX.tipTextInfo =
            TextInfo().setFontColor(R.color.colorPrimaryText.idToColor()).setFontSize(14).setFontSizeUnit(
                TextInfo.FONT_SIZE_UNIT.SP
            )

        //设置默认输入框文字样式
        DialogX.inputInfo = InputInfo().setTextInfo(
            TextInfo().setFontColor(R.color.colorPrimaryText.idToColor()).setFontSize(14).setFontSizeUnit(
                TextInfo.FONT_SIZE_UNIT.SP
            )
        )

        //设置默认底部菜单、对话框的标题文字样式
        DialogX.menuTitleInfo =
            TextInfo().setFontColor(R.color.colorPrimaryText.idToColor()).setFontSize(14).setFontSizeUnit(
                TextInfo.FONT_SIZE_UNIT.SP
            )

        //设置默认底部菜单文本样式
        DialogX.menuTextInfo =
            TextInfo().setFontColor(R.color.colorPrimaryText.idToColor()).setFontSize(14).setFontSizeUnit(
                TextInfo.FONT_SIZE_UNIT.SP
            )

        //设置默认对话框背景颜色（值为ColorInt，为-1不生效）
        DialogX.backgroundColor = R.color.colorGlobalWhite.idToColor()

        //设置默认对话框默认是否可以点击外围遮罩区域或返回键关闭，此开关不影响提示框（TipDialog）以及等待框（TipDialog）
        DialogX.cancelable = true

        //设置默认提示框及等待框（WaitDialog、TipDialog）默认是否可以关闭
        DialogX.cancelableTipDialog = false

        //设置默认取消按钮文本文字，影响 BottomDialog
        DialogX.cancelButtonText = R.string.cancel.idToString(mContext)

        //设置默认 PopTip 文本样式
        DialogX.popTextInfo =
            TextInfo().setFontColor(R.color.colorPrimaryText.idToColor()).setFontSize(14).setFontSizeUnit(
                TextInfo.FONT_SIZE_UNIT.SP
            )

        //设置全局 Dialog 生命周期监听器
        DialogX.dialogLifeCycleListener = object : DialogLifecycleCallback<BaseDialog>() {
            override fun onShow(dialog: BaseDialog?) {
                super.onShow(dialog)
                LogUtils.i("dialogX now status to show")
            }

            override fun onDismiss(dialog: BaseDialog?) {
                super.onDismiss(dialog)
                LogUtils.i("dialogX now status to dismiss")
            }
        }

        //设置 TipDialog 和 WaitDialog 明暗风格，不设置则默认根据 globalTheme 定义
        DialogX.tipTheme = DialogX.THEME.LIGHT

        //默认 TipDialog 和 WaitDialog 背景颜色（值为 ColorInt，为-1不生效）
        DialogX.tipBackgroundColor = R.color.colorGlobalWhite.idToColor()

        /**
         * 重写 TipDialog 和 WaitDialog 进度动画颜色，
         * 注意此属性为覆盖性质，即设置此值将替换提示框原本的进度动画的颜色，包括亮暗色切换的颜色变化也将被替代
         * （值为 ColorInt，为-1不生效）
         */
        DialogX.tipProgressColor = R.color.colorGlobalBlackText.idToColor()

        /**
         * 设置 BottomDialog 导航栏背景颜色
         */
        DialogX.bottomDialogNavbarColor = R.color.colorGlobalTransparent.idToColor()

        //是否自动在主线程执行
        DialogX.autoRunOnUIThread = true;

        DialogX.init(mApplication)
        return "DialogX -->> init complete"
    }

    /**
     * 初始化 阿里路由 ARouter
     */
    private fun initARouter(): String {
        ARouterManager.init(mApplication)
        return "ARouter -->> init complete"
    }

    /**
     * 初始化 腾讯Bugly
     * 测试环境应该与正式环境的日志收集渠道分隔开
     * 目前有两个渠道 测试版本/正式版本
     */
    private fun initTencentBugly(): String {
        // 第三个参数为SDK调试模式开关
        CrashReport.initCrashReport(
            mContext,
            "",
            BuildConfig.DEBUG
        )
        return "Bugly -->> init complete"
    }
}