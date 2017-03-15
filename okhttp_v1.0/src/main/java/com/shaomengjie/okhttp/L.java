package com.shaomengjie.okhttp;

import android.util.Log;

/**
 * Created by shaomengjie on 2017/3/15.
 */

public class L {
    public static final String TAG = "HTTP";
    public static boolean DEBUG = true;

    public L() {
    }

    public static void d(String msg) {
        if(DEBUG && msg != null) {
            Log.e("HTTP", msg);
        }

    }
}
