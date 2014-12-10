package com.example.mozan;

import android.content.Context;
import android.util.Log;
import android.webkit.WebView;

/**
 * Created by User on 10.12.2014.
 */
public class Global {

    public static String phone = "";
    public static String token = "";
    public static String userAgent = "";
    public static String code = "";

    public static void prepareUserAgent(Context context) {
        Global.userAgent = new WebView(context).getSettings().getUserAgentString();
        Log.i("ApiHelper", Global.userAgent);
    }
}
