package com.example.webapp.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.webapp.R;
import com.example.webapp.WebClient.WebClient;
import com.example.webapp.WebConnect.BrowserConnect;

public class BrowserActivity extends AppCompatActivity implements SwipeRefreshLayout.OnChildScrollUpCallback{

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private WebView webView;
    private EditText mEditText;
    private ProgressBar pb;
    private BrowserConnect browserConnect = new BrowserConnect();

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        init();
        onPressSearchB();
        if(!browserConnect.isOnline(this)){
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void init(){

        mEditText = findViewById(R.id.search_v);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);

        mSwipeRefreshLayout = findViewById(R.id.swipeToRefresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.reload();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        pb = findViewById(R.id.pb);

        webView = findViewById(R.id.web_view);
        webView.setWebViewClient(new WebClient());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            webView.getSettings().getSafeBrowsingEnabled();
        }
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebAppChromeClient());

        webView.loadUrl("http://www.google.com");

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

    public void onPressSearchB(){
        mEditText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
                        || (actionId == EditorInfo.IME_ACTION_SEARCH)) {

                    if(!browserConnect.isOnline(BrowserActivity.this)){
                        Toast.makeText(BrowserActivity.this, "No internet connection", Toast.LENGTH_LONG).show();
                    }

                    String url = mEditText.getText().toString();

                    if(!url.startsWith("http://www.")){
                        url = "http://www." + url;
                    }
                    webView.loadUrl(url);

                    InputMethodManager inputMethodManager =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (inputMethodManager != null) {
                        inputMethodManager.hideSoftInputFromWindow(webView.getWindowToken(),0);
                    }
                }
                return false;
            }
        });
    }
}
