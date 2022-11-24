package com.linwei.cams.component.web.weight

import android.app.Activity
import android.view.LayoutInflater
import android.webkit.WebView
import com.just.agentweb.IWebLayout
import com.linwei.cams.component.web.R

class WebLayout(val activity: Activity) : IWebLayout<WebView, WebView> {
    private val mWebView: WebView =
        LayoutInflater.from(activity).inflate(R.layout.view_layout_web, null) as WebView

    override fun getLayout(): WebView = mWebView

    override fun getWebView(): WebView = mWebView
}