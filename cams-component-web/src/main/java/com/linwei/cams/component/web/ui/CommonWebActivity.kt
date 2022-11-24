package com.linwei.cams.component.web.ui

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.KeyEvent
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.widget.LinearLayout
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import com.just.agentweb.WebChromeClient
import com.just.agentweb.WebViewClient
import com.linwei.cams.component.common.base.CommonBaseActivity
import com.linwei.cams.component.common.utils.ActivityStackManager
import com.linwei.cams.component.web.R
import com.linwei.cams.component.web.databinding.ActivityCommonWebBinding
import com.linwei.cams.component.web.weight.WebDialog
import com.linwei.cams.component.web.weight.WebLayout
import java.lang.Exception

class CommonWebActivity : CommonBaseActivity<ActivityCommonWebBinding>() {

    private val mUrl: String by lazy {
        intent.getStringExtra("url") ?: ""
    }

    private var mAgentWeb: AgentWeb? = null

    companion object {
        fun start(url: String) {
            val currentActivity: Activity? = ActivityStackManager.getCurrentActivity()
            currentActivity?.apply {
                startActivity(
                    Intent(
                        this,
                        CommonWebActivity::class.java
                    ).putExtra("url", url)
                )
            }
        }
    }

    override fun hasInjectARouter(): Boolean = false

    override fun initView() {
        val webContainerLayout = mViewBinding?.webContainerLayout
        webContainerLayout?.let {
            mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(it, LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator() // 使用默认进度条
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setWebLayout(WebLayout(this))
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK) //打开其他应用时，弹窗咨询用户是否前往其他应用
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                .createAgentWeb()
                .ready()
                .go(getUrl())
        }
    }

    private fun getUrl(): String = mUrl

    private val mWebViewClient: WebViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
        }
    }
    private val mWebChromeClient: WebChromeClient = object : WebChromeClient() {
        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
            mViewBinding?.webTitleView?.text = title
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (mAgentWeb?.handleKeyEvent(keyCode, event) == true) {
            return true
        }
        return super.onKeyDown(keyCode, event)
    }


    override fun onPause() {
        mAgentWeb?.webLifeCycle?.onPause()
        super.onPause()
    }

    override fun onResume() {
        mAgentWeb?.webLifeCycle?.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        mAgentWeb?.webLifeCycle?.onDestroy()
        super.onDestroy()
    }

    override fun initData() {

    }

    override fun initEvent() {
        mViewBinding?.webTitleMoreLayout?.setOnClickListener {
            WebDialog.newInstance(mUrl).show(supportFragmentManager, "webDialog")
        }
        mViewBinding?.webTitleCloseLayout?.setOnClickListener {
            finish()
        }
    }
}