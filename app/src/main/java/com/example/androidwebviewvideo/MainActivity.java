package com.example.androidwebviewvideo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private FrameLayout mWebFullTargetView;
    private FrameLayout mContentWebView;
    private WebChromeClient.CustomViewCallback mCustomViewCallback;
    private View mCustomView;
    private MyChromeClient mClient;

    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWebview();
    }

    private void initWebview() {
        webview = findViewById(R.id.webview_media);
        mWebFullTargetView = findViewById(R.id.web_full_target_view);
        mContentWebView = findViewById(R.id.main_content_webview);
        String url = "https://s3.eu-central-1.amazonaws.com/pipe.public.content/short.mp4";
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setAppCacheEnabled(true);
        webview.getSettings().setAllowFileAccess(true);

        mClient = new MyChromeClient();
        webview.setWebChromeClient(mClient);
        webview.loadUrl(url);
    }


    class MyChromeClient extends WebChromeClient {

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            mCustomViewCallback = callback;
            mWebFullTargetView.addView(view);
            mCustomView = view;
            mContentWebView.setVisibility(View.GONE);
            mWebFullTargetView.setVisibility(View.VISIBLE);
            mWebFullTargetView.bringToFront();
        }

        @Override
        public void onHideCustomView() {
            if (mCustomView == null)
                return;
            //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            mCustomView.setVisibility(View.GONE);
            mWebFullTargetView.removeView(mCustomView);
            mCustomView = null;
            mWebFullTargetView.setVisibility(View.GONE);
            mCustomViewCallback.onCustomViewHidden();
            mContentWebView.setVisibility(View.VISIBLE);

        }
    }

}
