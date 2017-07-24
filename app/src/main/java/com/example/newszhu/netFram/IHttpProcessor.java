package com.example.newszhu.netFram;

import java.util.List;
import java.util.Map;

/**
 * autour: 灵羽
 * date: 2017/7/15 13:37
 * description: 接口，里面定义网络访问方法，第三方网络框架实现这个接口，具体实现里面的网络方法
 * CopyRight：2017
 */

public interface IHttpProcessor {

    //    网络访问 post get
    void post(String url, Map<String, Object> params, ICallback iCallback);

    void get(String url, Map<String, Object> params, ICallback iCallback);

    void postFile(String url, Map<String, Object> params, List<String> fileList, ICallback iCallback);

    void stop();
}
