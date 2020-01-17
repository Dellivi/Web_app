package com.example.webapp.WebConnect;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class BrowserConnect {
    public boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public boolean isEndWith(String url) {
        if (url.endsWith(".com") || url.endsWith(".ru") || url.endsWith(".info")) {
            return true;
        } else return false;
    }
}
