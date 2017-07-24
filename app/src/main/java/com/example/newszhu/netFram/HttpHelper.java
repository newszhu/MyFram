package com.example.newszhu.netFram;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.example.newszhu.utils.NetStateUtile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * autour: 灵羽
 * date: 2017/7/15 13:41
 * description: 代理类,负责吊起网络访问方法
 * CopyRight：2017
 */

public class HttpHelper implements IHttpProcessor {

    private static IHttpProcessor iHttpProcessor = null;

    private Map<String, Object> mParams;
    private static HttpHelper _instance;//单例模式

    private HttpHelper() {
        mParams = new HashMap<>();
    }

    public static HttpHelper obtain() {//单例模式，外部调用接口
        synchronized (HttpHelper.class) {
            if (_instance == null) {
                _instance = new HttpHelper();
            }
        }
        return _instance;
    }

    public static void init(IHttpProcessor httpProcessor) {
        iHttpProcessor = httpProcessor;
    }


    @Override
    public void post(String url, Map<String, Object> params, ICallback iCallback) {//交给代理类去处理
        iHttpProcessor.post(url, params, iCallback);
    }

    @Override
    public void postFile(String url, Map<String, Object> params, List<String> fileList, ICallback iCallback) {
        iHttpProcessor.postFile(url, params, fileList, iCallback);
    }

    @Override
    public void get(String url, Map<String, Object> params, ICallback iCallback) {
        iHttpProcessor.get(url, params, iCallback);
    }

    @Override
    public void stop() {
        iHttpProcessor.stop();
    }

}
