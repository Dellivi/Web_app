package com.example.webapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.textview.MaterialAutoCompleteTextView;

public class BrowserActivity extends AppCompatActivity implements TextView.OnEditorActionListener {
    private WebView webView;
    private ProgressBar pb;
    private MaterialAutoCompleteTextView mACTV;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        pb = findViewById(R.id.pb);
        mACTV = findViewById(R.id.mACV);



        webView = findViewById(R.id.web_view);
        webView.setWebViewClient(new WebClient());
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                pb.setProgress(newProgress);
                if(newProgress==100){
                    pb.setVisibility(View.GONE);
                }else pb.setVisibility(View.VISIBLE);
            }
        });

        Uri urlU = getIntent().getData();

        if (urlU != null) {
            webView.loadUrl(urlU.toString());
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

    //TODO: Change mACTV.
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId == EditorInfo.IME_ACTION_SEARCH){
            webView.loadUrl(mACTV.toString());
            return true;
        }
        return false;
    }
}
