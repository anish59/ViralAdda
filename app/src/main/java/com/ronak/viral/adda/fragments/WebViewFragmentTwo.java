package com.ronak.viral.adda.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ronak.viral.adda.MainActivityTwo;
import com.ronak.viral.adda.R;
import com.ronak.viral.adda.helper.FunctionHelper;
import com.ronak.viral.adda.viralModels.Tab;

/**
 * Created by anish on 09-03-2018.
 */

public class WebViewFragmentTwo extends Fragment {
    private WebView webView;
    private android.support.v4.widget.SwipeRefreshLayout refreshlayout;
    private String[] webUrls;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview, container, false);
        this.refreshlayout = view.findViewById(R.id.refreshlayout);
        this.webView = view.findViewById(R.id.webView);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!FunctionHelper.isConnectedToInternet(getActivity())) {
            Toast.makeText(getActivity(), "Please connect to internet!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (getArguments() == null) {
            return;
        }
        webUrls = getArguments().getStringArray(MainActivityTwo.FRAGMENT_DATA);
        Tab tab = (Tab) getArguments().getSerializable(MainActivityTwo.TAB_DATA);
        Log.e("TAB_DATA:", new Gson().toJson(tab));


        // Force links and redirects to open in the WebView instead of in a browser
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressDialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressDialog.dismiss();
            }
        });
        webView.setWebChromeClient(new WebChromeClient());
        if (webUrls != null && webUrls.length != 0) {
            webView.loadUrl("https://www.facebook.com/movietrailersnow1/");
        }

        refreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (webUrls != null && webUrls.length != 0) {
                    webView.loadUrl(webUrls[0]);
                }
            }
        });

    }

}
