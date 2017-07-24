package com.example;

import android.app.Application;

import com.example.newszhu.imageFram.GlideImageProcessor;
import com.example.newszhu.imageFram.ImageHelper;
import com.example.newszhu.netFram.HttpHelper;
import com.example.newszhu.netFram.OkHttpProcessor;

/**
 * autour: 灵羽
 * date: 2017/7/15 17:46
 * description:
 * CopyRight：2017
 */

public class MyApplicaiotn extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        HttpHelper.init(new OkHttpProcessor());
        ImageHelper.init(new GlideImageProcessor(this));
    }
}
