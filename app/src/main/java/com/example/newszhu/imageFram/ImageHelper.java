package com.example.newszhu.imageFram;

import android.widget.ImageView;

/**
 * autour: 灵羽
 * date: 2017/7/16 15:54
 * description:图片加载代理类
 * CopyRight：2017
 */

public class ImageHelper implements IImageProccessor {

    private static ImageHelper _instance;
    private static IImageProccessor iImageProccessor = null;


    private ImageHelper() {

    }

    public static ImageHelper obtain() {
        synchronized (ImageHelper.class) {
            if (_instance == null) {
                _instance = new ImageHelper();
            }
            return _instance;
        }
    }

    public static void init(IImageProccessor imageProccessor) {
        iImageProccessor = imageProccessor;
    }

    @Override
    public void setImage(String url, int corner,ImageView imageView) {
        iImageProccessor.setImage(url,corner ,imageView);
    }

}
