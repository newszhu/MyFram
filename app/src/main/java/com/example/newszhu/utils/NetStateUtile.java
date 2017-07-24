package com.example.newszhu.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by admin on 2017/7/17.
 */

public class NetStateUtile {

    /**
     * 检测网络是否连接方法
     *
     * @return
     */
    public static boolean isNetConnect(Context context) {
        boolean avilable;
        ConnectivityManager connectManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);//获取系统的网络连接
        NetworkInfo networkinfo = connectManager.getActiveNetworkInfo();//获取网络的连接情况。如果网络可用，则networkinfo不为空，且networkinfo.isAvailable()=true;反之网络不可用，networkinfo为空。
        if (networkinfo != null)
            avilable = networkinfo.isAvailable();
        else
            avilable = false;
        return avilable;
    }

}
