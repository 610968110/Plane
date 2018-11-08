package com.lbx.huiji.bean;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.lbx.huiji.R;

import lbx.xtoollib.XTools;

/**
 * .  ┏┓　　　┏┓
 * .┏┛┻━━━┛┻┓
 * .┃　　　　　　　┃
 * .┃　　　━　　　┃
 * .┃　┳┛　┗┳　┃
 * .┃　　　　　　　┃
 * .┃　　　┻　　　┃
 * .┃　　　　　　　┃
 * .┗━┓　　　┏━┛
 * .    ┃　　　┃        神兽保佑
 * .    ┃　　　┃          代码无BUG!
 * .    ┃　　　┗━━━┓
 * .    ┃　　　　　　　┣┓
 * .    ┃　　　　　　　┏┛
 * .    ┗┓┓┏━┳┓┏┛
 * .      ┃┫┫　┃┫┫
 * .      ┗┻┛　┗┻┛
 *
 * @author lbx
 * @date 2018/11/7.
 * 敌机(没有热狗的王老板)
 */

public class FoePlane extends Floating {

    /**
     * 是否被子弹销毁
     */
    private boolean isDestroy;
    /**
     * 销毁时时间
     */
    private long destroyTime;
    /**
     * 销毁后在屏幕中停留的时间
     */
    private static final int DESTROY_TIME = 1000;
    private Context mContext;

    public FoePlane(Context context, int size) {
        super(context, R.drawable.fb_n, size, size);
        mContext = context;
    }

    public boolean isDestroy() {
        return isDestroy;
    }

    /**
     * @param destroy 是否销毁
     */
    public void setDestroy(boolean destroy) {
        //如果之前没有被销毁，并且现在设置成销毁
        if (destroy && !isDestroy) {
            destroyTime = getTime();
            Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.fb);
            //设置销毁后的图片，即王老板吃到了热狗
            setBitmap(XTools.BitmapUtil().zoomBmp(bitmap, getW()));
        }
        isDestroy = destroy;
    }

    /**
     * 判断是否销毁并可以移除屏幕
     *
     * @return 是否销毁并可以移除屏幕
     */
    public boolean canDestroyAndRemove() {
        return isDestroy() && getTime() - destroyTime >= DESTROY_TIME;
    }

    private long getTime() {
        return System.currentTimeMillis();
    }
}
