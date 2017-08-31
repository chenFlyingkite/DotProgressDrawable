package com.flyingkite.util;

import android.util.Log;

public class Say {
    private static final String TAG = "Hi";

    public static void Log(String msg) {
        Log.e(TAG, msg);
    }

    public static void Log(String format, Object... params) {
        Log(String.format(format, params));
    }

    public static void LogI(String msg) {
        Log.i(TAG, msg);
    }

    public static void LogI(String format, Object... params) {
        LogI(String.format(format, params));
    }
}
