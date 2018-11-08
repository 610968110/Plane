package com.lbx.huiji.bean;

import android.content.Context;

import com.lbx.huiji.R;

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
 * 我方飞机(大热狗)
 */

public class MyPlane extends Floating {

    private int w;
    private int h;

    public MyPlane(Context context, int w, int h) {
        super(context, R.drawable.bullet, w, h);
        this.w = w;
        this.h = h;
    }

    @Override
    public int getW() {
        return w;
    }

    @Override
    public int getH() {
        return h;
    }
}
