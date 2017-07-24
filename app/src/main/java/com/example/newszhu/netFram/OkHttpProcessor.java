package com.example.newszhu.netFram;

import android.os.Handler;
import android.util.Log;

import com.example.newszhu.utils.LogUtil;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * autour: 灵羽
 * date: 2017/7/15 13:42
 * description: okHttp 网络访问执行层，封装了第三方网络访问的具体请求方法
 * CopyRight：2017
 */

public class OkHttpProcessor implements IHttpProcessor {

    private static final String TAG = "OkHttpProcessor";

    private OkHttpClient okHttpClient = null;
    private Handler myHandler;

    public OkHttpProcessor() {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        myHandler = new Handler();
    }

    @Override
    public void post(final String url, Map<String, Object> params, final ICallback iCallback) {
        RequestBody requestBody = appendBody(params);

        Request request = new Request.Builder().post(requestBody).header("User-Agent", "a").url(url).tag(url).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                myHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iCallback.onFailure(e.toString());
                        LogUtil.e("URl访问失败", url);
                        LogUtil.e("错误内容", e.toString());
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String result = response.body().string();
                    myHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            iCallback.onSuccess(result);
                            LogUtil.d(url + "返回成功", result);
                        }
                    });
                } else {
                    myHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            iCallback.onFailure(response.message().toString());
                            LogUtil.e("URl访问失败", url);
                            LogUtil.e("错误内容", response.message().toString());
                        }
                    });
                }
            }
        });
    }


    @Override
    public void postFile(final String url, Map<String, Object> params, List<String> fileList, final ICallback iCallback) {
        MultipartBody multipartBody = appendMultipartBody(params, fileList);

        Request request = new Request.Builder().post(multipartBody).url(url).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                myHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iCallback.onFailure(e.toString());
                        LogUtil.e("URl访问失败", url);
                        LogUtil.e("错误内容", e.toString());
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String result = response.body().string();
                    myHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            iCallback.onSuccess(result);
                            LogUtil.d(url + "返回成功", result);
                        }
                    });
                } else {
                    myHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            iCallback.onFailure(response.message().toString());
                            LogUtil.e("URl访问失败", url);
                            LogUtil.e("错误内容", response.message().toString());
                        }
                    });
                }
            }
        });
    }


    @Override
    public void get(final String url, Map<String, Object> params, final ICallback iCallback) {
        String newUrl = appendParams(url, params);

        Request request = new Request.Builder().get().header("User-Agent", "a").url(newUrl).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                myHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iCallback.onFailure(e.toString());
                        LogUtil.e("URl访问失败", url);
                        LogUtil.e("错误内容", e.toString());
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String result = response.body().string();
                    myHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            iCallback.onSuccess(result);
                            LogUtil.d(url + "返回成功", result);
                        }
                    });
                } else {
                    myHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            iCallback.onFailure(response.message().toString());
                            LogUtil.e("URl访问失败", url);
                            LogUtil.e("错误内容", response.message().toString());
                        }
                    });
                }
            }
        });
    }

    @Override
    public void stop() {
        okHttpClient.dispatcher().cancelAll();
        LogUtil.d("OKHttp", "网络访问取消");
    }


    private MultipartBody appendMultipartBody(Map<String, Object> params, List<String> fileList) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        if (params == null || params.isEmpty()) {
            return builder.build();
        }

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            builder.addFormDataPart(entry.getKey(), entry.getValue().toString());
        }

        for (String str : fileList) {
            File file = new File(str);

            RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
            builder.addFormDataPart("uploadify", file.getName(), fileBody);
        }
        return builder.build();

    }

    //拼接
    private RequestBody appendBody(Map<String, Object> params) {
        FormBody.Builder body = new FormBody.Builder();

        if (params == null || params.isEmpty()) {
            return body.build();
        }
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            body.add(entry.getKey(), entry.getValue().toString());
        }
        return body.build();
    }


    //拼接
    public static String appendParams(String url, Map<String, Object> mParams) {
        if (mParams == null || mParams.isEmpty()) {
            return url;
        }
        StringBuilder urlBuilder = new StringBuilder(url);

        if (urlBuilder.indexOf("?") <= 0) {
            urlBuilder.append("?");
        } else {
            if (!urlBuilder.toString().endsWith("?")) {
                urlBuilder.append("&");
            }
        }
        for (Map.Entry<String, Object> entry : mParams.entrySet()) {
            urlBuilder.append(entry.getKey()).append("=").append(encode(entry.getValue().toString()));
        }
        return urlBuilder.toString();
    }

    //URI 不允许有空格等字符，如果参数值有空格，需要此方法转换
    private static String encode(String string) {
        try {
            return URLEncoder.encode(string, "utf-8");
        } catch (UnsupportedEncodingException e) {
            Log.e("参数转码异常", e.toString());
            throw new RuntimeException();
        }
    }


//    public void initOkHttpClient() {
//  okHttpClient.
//    }
}
