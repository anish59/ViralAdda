package com.ronak.viral.adda.fragments;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.cpiz.android.bubbleview.BubbleTextView;
import com.ronak.viral.adda.MainActivityTwo;
import com.ronak.viral.adda.R;
import com.ronak.viral.adda.helper.FunctionHelper;

/**
 * Created by anish on 09-03-2018.
 */

public class WebViewFragmentTwo extends Fragment {
    private WebView webView;
    private android.support.v4.widget.SwipeRefreshLayout refreshlayout;
    private String[] webUrls;
    String browseUrl = "";
    private com.cpiz.android.bubbleview.BubbleTextView hintTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview, container, false);
        this.hintTextView = (BubbleTextView) view.findViewById(R.id.hintTextView);
        this.refreshlayout = view.findViewById(R.id.refreshlayout);
        this.webView = view.findViewById(R.id.webView);
        setHasOptionsMenu(true);
        return view;
    }

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


        if (webUrls != null && webUrls.length != 0) {
            webView.loadUrl(webUrls[0]);
        }

        // Enable Javascript
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Force links and redirects to open in the WebView instead of in a browser
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                refreshlayout.setRefreshing(true);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                refreshlayout.setRefreshing(false);

            }
        });

        refreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (webUrls != null && webUrls.length != 0) {
                    webView.loadUrl(webUrls[0]);
                }
            }
        });


        browseUrl = getArguments().getString(MainActivityTwo.INTENT_BROWSER_URL);


        if (browseUrl != null && !browseUrl.isEmpty()) {
            hintTextView.setVisibility(View.VISIBLE);


        } else {
            hintTextView.setVisibility(View.GONE);

        }

        hintTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(getActivity(), Uri.parse(browseUrl));
            }
        });

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        browseUrl = getArguments().getString(MainActivityTwo.INTENT_BROWSER_URL);
        if (browseUrl != null && !browseUrl.isEmpty()) {
            inflater.inflate(R.menu.menu_website, menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.actionOpenWeb:
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(getActivity(), Uri.parse(browseUrl));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
