package com.example.newszhu.utils;

import android.util.Log;

/**
 * autour: 灵羽
 * date: 2017/7/15 18:51
 * description:
 * CopyRight：2017
 */

public class LogUtil {
    private static boolean show = true;


    public static void e(String tag, String content) {
        if (show) {
            Log.e(tag, content);
        }
    }

    public static void d(String tag, String content) {
        if (show) {
            Log.d(tag, content);
        }
    }

}
