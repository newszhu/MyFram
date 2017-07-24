package com.example.newszhu.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.example.newszhu.netFram.FileUploadBean;
import com.example.newszhu.netFram.HttpCallback;
import com.example.newszhu.netFram.HttpHelper;
import com.example.newszhu.netFram.ICallback;
import com.example.newszhu.utils.NetStateUtile;

import java.util.List;
import java.util.Map;

/**
 * autour: 灵羽
 * date: 2017/7/15 19:03
 * description:
 * CopyRight：2017
 */

public class BaseActivity extends Activity {

    public Context mContext;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }

    // post方式 返回obj
    public void objReturnPostHttp(String url, Map<String, Object> params, String progressMessage, final HttpCallback<?> httpCallback) {

        if (!NetStateUtile.isNetConnect(mContext)) {
            Toast.makeText(mContext, "网络未连接,请检查", Toast.LENGTH_SHORT).show();
            return;
        }

        if (progressMessage != null && progressMessage.length() > 0) {
            showProgressDialog(progressMessage);
        }

        HttpHelper.obtain().post(url, params, new ICallback() {
            @Override
            public void onSuccess(String result) {
                httpCallback.onSuccess(result);
            }

            @Override
            public void onFailure(String error) {
                httpCallback.onFailure(error.toString());
                cancleProgress();
                Toast.makeText(mContext, "网络访问失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //post方式返回 String
    public void strReturnPostHttp(String url, Map<String, Object> params, String progressMessage, final ICallback iCallback) {
        if (!NetStateUtile.isNetConnect(mContext)) {
            Toast.makeText(mContext, "网络未连接,请检查", Toast.LENGTH_SHORT).show();
            return;
        }

        if (progressMessage != null && progressMessage.length() > 0) {
            showProgressDialog(progressMessage);
        }

        HttpHelper.obtain().post(url, params, new ICallback() {
            @Override
            public void onSuccess(String result) {
                iCallback.onSuccess(result);
            }

            @Override
            public void onFailure(String error) {
                iCallback.onFailure(error.toString());
                cancleProgress();
                Toast.makeText(mContext, "网络访问失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // post方式 返回obj
    public void postFileHttp(String url, Map<String, Object> params, List<String> fileList, String progressMessage, final HttpCallback<?> httpCallback) {
        if (!NetStateUtile.isNetConnect(mContext)) {
            Toast.makeText(mContext, "网络未连接,请检查", Toast.LENGTH_SHORT).show();
            return;
        }

        if (progressMessage != null && progressMessage.length() > 0) {
            showProgressDialog(progressMessage);
        }

        HttpHelper.obtain().postFile(url, params, fileList, new ICallback() {
            @Override
            public void onSuccess(String result) {
                httpCallback.onSuccess(result);
            }

            @Override
            public void onFailure(String error) {
                httpCallback.onFailure(error.toString());
                cancleProgress();
                Toast.makeText(mContext, "网络访问失败", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void showProgressDialog(String progressMessage) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        progressDialog.setMessage(progressMessage);
        progressDialog.show();
    }

    public void setProgessMessage(String message) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.setMessage(message);
        }
    }

    public void cancleProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
