package com.ronak.viral.adda.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ronak.viral.adda.MainActivityTwo;
import com.ronak.viral.adda.R;
import com.ronak.viral.adda.helper.FunctionHelper;

/**
 * Created by anish on 19-03-2018.
 */

public class WebViewFragmentThree extends Fragment {
    private LinearLayout mContentView;
    private FrameLayout mCustomViewContainer;
    private WebChromeClient.CustomViewCallback mCustomViewCallback;
    FrameLayout.LayoutParams COVER_SCREEN_GRAVITY_CENTER = new FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
    private FrameLayout fullscreencustomcontent;
    private WebView webView;
    private LinearLayout linearlayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview_three, container, false);
        this.linearlayout = view.findViewById(R.id.linearlayout);
        this.webView = view.findViewById(R.id.webView);
        this.fullscreencustomcontent = view.findViewById(R.id.fullscreen_custom_content);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init() {

        WebSettings webSettings = webView.getSettings();
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        if (!FunctionHelper.isConnectedToInternet(getActivity())) {
            Toast.makeText(getActivity(), "Please connect to internet!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (getArguments() == null) {
            return;
        }
        String[] webUrls = getArguments().getStringArray(MainActivityTwo.FRAGMENT_DATA);


        if (webUrls != null && webUrls.length != 0) {
            webView.loadUrl(webUrls[0]);
            webView.setWebViewClient(new MyWebViewClient());
        }


    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView webview, String url) {
            webview.setWebChromeClient(new WebChromeClient() {

                private View mCustomView;

                @Override
                public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback) {
                    // if a view already exists then immediately terminate the new one
                    if (mCustomView != null) {
                        callback.onCustomViewHidden();
                        return;
                    }

                    // Add the custom view to its container.
                    mCustomViewContainer.addView(view, COVER_SCREEN_GRAVITY_CENTER);
                    mCustomView = view;
                    mCustomViewCallback = callback;

                    // hide main browser view
                    mContentView.setVisibility(View.GONE);

                    // Finally show the custom view container.
                    mCustomViewContainer.setVisibility(View.VISIBLE);
                    mCustomViewContainer.bringToFront();
                }

            });

            webview.loadUrl(url);

            return true;
        }
    }

}
