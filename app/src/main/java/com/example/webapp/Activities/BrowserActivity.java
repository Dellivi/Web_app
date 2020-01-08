package com.example.webapp.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.example.webapp.R;
import com.example.webapp.WebClient.WebClient;

public class BrowserActivity extends AppCompatActivity implements SwipeRefreshLayout.OnChildScrollUpCallback{

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private WebView webView;
    private SearchView mSearchView;
    private ProgressBar pb;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        mSwipeRefreshLayout = findViewById(R.id.swipeToRefresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.reload();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });


        pb = findViewById(R.id.pb);
        mSearchView = findViewById(R.id.search_v);


        webView = findViewById(R.id.web_view);
        webView.setWebViewClient(new WebClient());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            webView.getSettings().getSafeBrowsingEnabled();
        }
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebAppChromeClient());

        Uri urlU = getIntent().getData();

        if (urlU != null) {
            webView.loadUrl(urlU.toString());
        }


    }

    @Override
    public boolean canChildScrollUp(@NonNull SwipeRefreshLayout parent, @Nullable View child) {
        return false;
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
