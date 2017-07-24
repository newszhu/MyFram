package com.example.newszhu.imageFram;

import android.widget.ImageView;

/**
 * autour: 灵羽
 * date: 2017/7/16 15:52
 * description: 图片加载规范化接口
 * CopyRight：2017
 */

public interface IImageProccessor {

    void setImage(String url, int corner, ImageView imageView);

}
