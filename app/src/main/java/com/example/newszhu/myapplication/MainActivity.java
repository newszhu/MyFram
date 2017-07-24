package com.example.newszhu.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newszhu.base.BaseActivity;
import com.example.newszhu.imageFram.ImageHelper;
import com.example.newszhu.netFram.HttpCallback;
import com.example.newszhu.netFram.HttpHelper;
import com.example.newszhu.netFram.ICallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 灵羽
 * @time 2017/7/14 22:10
 * @description:
 */
public class MainActivity extends BaseActivity {

    public static String urlTest = "http://c.3g.163.com/photo/api/set/0001%2F2250173.json";
    private Map<String, Object> params;
    private TextView showContent;
    private ImageView iv_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showContent = (TextView) findViewById(R.id.tv_show);
        iv_show = (ImageView) findViewById(R.id.iv_show);

        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, 0x081);
            }
        }
    }

    public void requestData(View view) {
        objReturnPostHttp(urlTest, params, "正在获取数据", new HttpCallback<WeatherBean>() {
            @Override
            public void onFailure(String error) {
                cancleProgress();
            }

            @Override
            public void onSuccess(WeatherBean weatherBean) {
                showContent.setText(weatherBean.getSetname());
                ImageHelper.obtain().setImage(weatherBean.getPhotos().get(0).getTimgurl(), 10, iv_show);
                cancleProgress();
            }
        });

    }

    public void cancleRequest(View view) {
//        HttpHelper.obtain().stop();
        Map<String, Object> map = new HashMap<>();

        map.put("style", "walkRegister");
        map.put("relationId", "37a44f371b50410ea48c5b1470f6bd8b");

        List<String> files = new ArrayList<>();
        files.add("/storage/emulated/0/tencent/QQfile_recv/列表_残疾人.txt");
        files.add("/storage/emulated/0/tencent/QQfile_recv/办事指南.txt");

        postFileHttp("http://192.168.17.106:8002/m/lxcl/attachment/addlist", map, files, "正在提交附件", new HttpCallback<MessageBean>() {
            @Override
            public void onSuccess(MessageBean messageBean) {
                Toast.makeText(mContext, messageBean.msg, Toast.LENGTH_SHORT).show();
                cancleProgress();
            }

            @Override
            public void onFailure(String error) {
            }
        });


    }
}
