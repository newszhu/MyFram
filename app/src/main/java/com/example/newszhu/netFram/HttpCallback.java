package com.example.newszhu.netFram;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * autour: 灵羽
 * date: 2017/7/15 13:23
 * description: 带数据转换的处理层，传入泛型，返回对应的实体类
 * CopyRight：2017
 */

public abstract class HttpCallback<Result> implements ICallback {

    @Override
    public void onSuccess(String result) {
        Gson gson = new Gson();

        Class<?> clz = analysisClassInfo(this);

        Result objResult = (Result) gson.fromJson(result, clz);

        onSuccess(objResult);
    }

    public abstract void onSuccess(Result result);

    public static Class<?> analysisClassInfo(Object object) {
        Type genType = object.getClass().getGenericSuperclass();//获取类型

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        return (Class<?>) params[0];
    }

}
