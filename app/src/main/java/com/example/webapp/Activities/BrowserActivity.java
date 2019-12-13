package com.example.webapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.example.webapp.R;
import com.example.webapp.WebClient.WebClient;

public class BrowserActivity extends AppCompatActivity  {

    private WebView webView;
    private SearchView mSearchView;
    private ProgressBar pb;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        pb = findViewById(R.id.pb);
        mSearchView = findViewById(R.id.search_v);
        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);

        webView = findViewById(R.id.web_view);
        webView.setWebViewClient(new WebClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebAppChromeClient());

        Uri urlU = getIntent().getData();

        if (urlU != null) {
            webView.loadUrl(urlU.toString());
        }
    }


    class WebAppChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {

            pb.setProgress(newProgress);
            if(newProgress==100){
                pb.setVisibility(View.GONE);
            }else pb.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

}
