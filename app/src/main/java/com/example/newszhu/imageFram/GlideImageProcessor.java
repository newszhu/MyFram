package com.example.newszhu.imageFram;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.newszhu.myapplication.R;

/**
 * autour: 灵羽
 * date: 2017/7/16 15:55
 * description: 图片加载实现类
 * CopyRight：2017
 */

public class GlideImageProcessor implements IImageProccessor {
    private Context context;

    public GlideImageProcessor(Context context) {
        this.context = context;
    }

    @Override
    public void setImage(String url, int corner, ImageView imageView) {

        if (corner < -1) {
            try {
                throw new Exception("corner的值必须大于-1");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (corner == 0) {//直角
            Glide.with(context).load(url).error(R.mipmap.ic_launcher).into(imageView);
        } else if (corner == -1) {//圆形
            Glide.with(context)
                    .load(url)
                    .error(R.mipmap.ic_launcher)
                    .transform(new GlideCircleTransform(context))
                    .into(imageView);
        } else {//圆角
            Glide.with(context)
                    .load(url)
                    .error(R.mipmap.ic_launcher)
                    .transform(new GlideRoundTransform(context, corner))
                    .into(imageView);
        }

    }
}
