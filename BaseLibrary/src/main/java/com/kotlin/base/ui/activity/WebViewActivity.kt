package com.kotlin.base.ui.activity

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import android.widget.FrameLayout
import android.widget.ProgressBar
import com.kotlin.base.R
import com.kotlin.base.utils.LogUtils
import com.kotlin.base.widgets.HeaderBar

class WebViewActivity : BaseTitleActivity() {

    var webView: WebView? = null
    var pb: ProgressBar? = null
    private var url: String? = null

    override fun initTitle(baseTitleHeaderBar: HeaderBar) {

    }

    override fun initView(baseTitleContentView: FrameLayout) {

        url = intent.getStringExtra(KET_URL)
        if (TextUtils.isEmpty(url)) {
            return
        }

        val view = View.inflate(this, R.layout.activity_webview, null)
        pb = view.findViewById(R.id.cooperation_web_pb)
        webView = view.findViewById(R.id.cooperation_web)
        baseTitleContentView.addView(view)


        // http://api.map.baidu.com/place/detail?uid=cfb1305e8ef132caa1d230f0&output=html&source=placeapi_v2
        //支持Js
        webView?.settings?.javaScriptEnabled = true
        //设置默认编码
        webView?.settings?.defaultTextEncodingName = "UTF-8"
        //设置在WebView内部是否允许访问文件，默认允许访问。
        webView?.settings?.allowFileAccess = true
        //设置WebView是否使用viewport，当该属性被设置为false时，加载页面的宽度总是适应WebView控件宽度；
        //当被设置为true，当前页面包含viewport属性标签，在标签中指定宽度值生效，如果页面不包含viewport标签，
        //无法提供一个宽度值，这个时候该方法将被使用。
        webView?.settings?.useWideViewPort = true
        webView?.settings?.cacheMode = WebSettings.LOAD_NO_CACHE
        webView?.settings?.domStorageEnabled = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView?.settings?.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        //必须设置setWebviewclient  自定义的继承于webviewclient的类就是用来拦截url处理一些与H5交互的时候的逻辑
        webView?.webViewClient = MyWebViewClient()
        webView?.webChromeClient = MyWebChromeClient()
        webView?.loadUrl(url)
    }


    //自定义的webviewclient，用来拦截url处理一些与H5交互的时候的逻辑等
    inner class MyWebViewClient : WebViewClient() {

        //此方法可以使H5在webview中显示，而不是手机浏览器
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            return if (url.startsWith("https://") || url.startsWith("http://")) {
                view.loadUrl(url)
                false
            } else {
                try {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(view.url))
                    startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                true
            }
        }


        //访问url结束时候触发
        override fun onPageFinished(view: WebView, url: String) {
            if (pb != null)
                pb!!.visibility = View.GONE
        }

        override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
            //            super.onReceivedSslError(view, handler, error);
            handler.proceed()// 接受所有网站的证书
        }
    }

    internal inner class MyWebChromeClient : WebChromeClient() {
        override fun onReceivedTitle(view: WebView, title: String) {
            super.onReceivedTitle(view, title)
            LogUtils.e("web", "title=$title")
        }

        override fun onProgressChanged(view: WebView, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            if (newProgress <= 100) {
                if (pb != null)
                    pb?.progress = newProgress
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showFinish()
            return true
        } else {
            return super.onKeyDown(keyCode, event)
        }
    }

    private fun showFinish() {
        if (webView?.canGoBack() == true) {
            webView?.goBack()
        } else {
            finish()
        }
    }

    companion object {
        val KET_URL = "url"
    }

}