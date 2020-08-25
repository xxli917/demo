package com.lixiaoxue.demolxx.base;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.lixiaoxue.demolxx.MainActivity;
import com.lixiaoxue.demolxx.R;
import com.lixiaoxue.demolxx.widget.StatusBar;
import com.nucarf.base.utils.LogUtils;
import com.nucarf.base.utils.SharePreUtils;

import java.security.Key;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public  class BaseWebActivity extends BaseActivityWithTitle {


    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private String type;
    private String url;

    public static void lauch(Context context, String title, String url, String type) {
        Intent intent = new Intent(context, BaseWebActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        LogUtils.e("url="+url);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentLayoutId());
        progressBar = new ProgressBar(this);
        ButterKnife.bind(this);
//        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).titleBar(titleBar).init();

    }

    public  int getContentLayoutId(){
        return R.layout.activity_web_view;
    }

    @Override
    protected void initData() {
        url = getIntent().getStringExtra("url");
        type = getIntent().getStringExtra("type");
        //处理广告
        dealAd();
        String title = getIntent().getStringExtra("title");
        titleBar.setTitle(title);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);//开启硬件加速

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setBlockNetworkImage(false);//解决图片不显示
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
        webView.getSettings().setAppCacheMaxSize(Long.MAX_VALUE);
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        webView.getSettings().setAppCachePath(appCachePath);
        webView.getSettings().setAppCachePath(this.getDir("appcache", 0).getPath());
        webView.getSettings().setDatabasePath(this.getDir("databases", 0).getPath());
        webView.getSettings().setGeolocationDatabasePath(this.getDir("geolocation", 0)
                .getPath());
        webView.getSettings().setGeolocationEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        //支持缩放
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        //不展示缩放按钮
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setSupportMultipleWindows(false);
        // 修改ua使得web端正确判断
        String ua = webView.getSettings().getUserAgentString();
        webView.getSettings().setUserAgentString(ua + ";isApp");
        // 设置允许JS弹窗
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        }
        // android 5.0以上默认不支持Mixed Content
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        synCookies(this, url);
        // url = "file:///android_asset/my.html";
        loadH5(url);

    }

    private void dealAd() {
        if(!TextUtils.isEmpty(type) && type.equals("ad")){
            final StatusBar statusBar = getTitleBar();
            statusBar.setImageOnClick(new StatusBar.ImageOnClickListener() {
                @Override
                public void leftOnClick(View v) {
                    gotoMainActivity();
                }

                @Override
                public void rightOnClick(View v) {

                }
            });
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(!TextUtils.isEmpty(type) && type.equals("ad")) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                gotoMainActivity();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void gotoMainActivity() {
        Intent intent = new Intent(BaseWebActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 同步一下cookie
     */
    public void synCookies(Context context, String url) {
        if (url.contains("nucarf")) {
            CookieSyncManager.createInstance(context);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.removeSessionCookie();//移除
            LogUtils.e("n-token=" + SharePreUtils.getToken());
            cookieManager.setCookie(url, "n-token=" + SharePreUtils.getToken());
            CookieSyncManager.getInstance().sync();
        }

    }

    private void hideBarProgress() {
        progressBar.setVisibility(View.GONE);
    }

    private void showBarProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void loadH5(final String url) {
        webView.addJavascriptInterface(new SettingInterface(), "app");
        webView.loadUrl(url);
        webView.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {

                // url 你要访问的下载链接
                // userAgent 是HTTP请求头部用来标识客户端信息的字符串
                // contentDisposition 为保存文件提供一个默认的文件名
                // mimetype 该资源的媒体类型
                // contentLength 该资源的大小
                // 这几个参数都是可以通过抓包获取的

                // 用手机默认浏览器打开链接
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        // 与js交互
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                LogUtils.e("onPageStarted");
                showBarProgress();
                super.onPageStarted(view, url, favicon);
            }

            //webview加载https链接错误或无响应
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                if (handler != null) {
                    handler.proceed();//忽略证书的错误继续加载页面内容，不会变成空白页面
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                LogUtils.e("onPageFinished");
                //webView.loadUrl("javascript:window.localStorage.setItem('" + key + "','" + value + "');
                if (url.contains("nucarf")) {
                    String method = "function sendEventToNative(name,object){window.app.onReceiveEventFromHtml5(name, object);}";
                    webView.loadUrl("javascript:" + method);
                    webView.loadUrl("javascript:onNucarfLoaded()");
                }
                hideBarProgress();
                CookieManager cookieManager = CookieManager.getInstance();
                String cookieStr = cookieManager.getCookie(url);
                LogUtils.e("cookieStr" + cookieStr);
                super.onPageFinished(view, url);
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
    }

    @Override
    public void onBackPressed() {

        if (webView.canGoBack()) {
            webView.goBack();
        } else {

            finish();
        }
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()， 再 destory()
            ViewParent parent = webView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(webView);
            }
            webView.stopLoading(); // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            webView.clearHistory();
            webView.clearView();
            webView.removeAllViews();
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }

    class SettingInterface {
        public SettingInterface() {

        }

        @JavascriptInterface
        public void onReceiveEventFromHtml5(String name, String object) {
            LogUtils.e("----" + name + "--" + (String) object);
            if ("closeWebView".equals(name)) {
                finish();
            }
        }

        @JavascriptInterface
        public void onReceiveEventFromHtml5(String name, Object object) {
            LogUtils.e("----" + name + "--" + "object="+object);
            if ("closeWebView".equals(name)) {
                finish();
            }
        }

        @JavascriptInterface
        public String onReceiveEventFromHtml5() {
            return "lixx";
        }
    }


}
