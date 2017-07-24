package com.example.newszhu.netFram;

/**
 * autour: 灵羽
 * date: 2017/7/15 13:19
 * description:直接接触服务器返回数据的接口层
 * CopyRight：2017
 */

public interface ICallback {

    void onSuccess(String result);

    void onFailure(String error);

}
