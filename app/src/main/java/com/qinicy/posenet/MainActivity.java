package com.qinicy.posenet;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;
import com.qinicy.posenet.model.Pose;

public class MainActivity extends AppCompatActivity {

    private static final String CAMERA_HTML_URL = "file:///android_asset/tensorflow/camera.html";
    private static final String MINI_TENSORFLOW = "mini://tensorflow";
    //    private static final String CAMERA_HTML_URL = "https://storage.googleapis.com/tfjs-models/demos/posenet/camera.html";
    private WebView mWebView;
    private MyWebViewClient mWebViewClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = findViewById(R.id.webview);
        mWebViewClient = new MyWebViewClient();
        mWebView.setWebViewClient(mWebViewClient);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {


                Log.i("console", "[" + consoleMessage.messageLevel() + "] " + consoleMessage.message() + "(" + consoleMessage.sourceId() + ":" + consoleMessage.lineNumber() + ")");

                return super.onConsoleMessage(consoleMessage);

            }

            @Override
            public void onPermissionRequest(PermissionRequest request) {
//                super.onPermissionRequest(request);
                Log.i("onPermissionRequest", "request:" + request.getResources()[0]);
                request.grant(request.getResources());
            }

        });
        WebSettings setting = mWebView.getSettings();
        setting.setJavaScriptEnabled(true);
        setting.setSupportZoom(false);
        setting.setUseWideViewPort(true);
        setting.setLoadWithOverviewMode(true);
        setting.setDomStorageEnabled(true);
        setting.setDefaultTextEncodingName("UTF-8");
        setting.setAllowContentAccess(true); // 是否可访问Content Provider的资源，默认值 true
        setting.setAllowFileAccess(true);    // 是否可访问本地文件，默认值 true
        // 是否允许通过file url加载的Javascript读取本地文件，默认值 false
        setting.setAllowFileAccessFromFileURLs(true);
        // 是否允许通过file url加载的Javascript读取全部资源(包括文件,http,https)，默认值 false
        setting.setAllowUniversalAccessFromFileURLs(true);
        //开启JavaScript支持
        setting.setJavaScriptEnabled(true);

        // 支持缩放
        mWebView.loadUrl(CAMERA_HTML_URL);

    }

    private void onPersonPoseDetect(Pose json) {
        Log.i("MainActivity", "onPersonPoseDetect:" + json.toString());
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith(MINI_TENSORFLOW)) {
                String[] args = url.split("\\|");
                if (args != null && args.length == 2) {
                    Pose pose = new Gson().fromJson(args[1], Pose.class);
                    onPersonPoseDetect(pose);
                    return true;
                }
            }
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

}
