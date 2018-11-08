package com.lbx.huiji.bean;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.DrawableRes;

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
 */

public class Floating {

    /**
     * 图片  也就是物体的样子
     */
    private Bitmap mBitmap;
    /**
     * w 宽 h高
     */
    private int w, h;
    /**
     * 所占据的矩形区域
     */
    private Rect mRect;
    /**
     * 中心点位置
     */
    private Point mPoint;
    /**
     * 物体的唯一ID
     */
    private long id;
    /**
     * tag  备用
     */
    private Object tag;

    public Floating(Context context, @DrawableRes int img, int w, int h) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), img);
        this.mBitmap = XTools.BitmapUtil().zoomBmp(bitmap, w, h);
        this.w = w;
        this.h = h;
        mRect = new Rect();
        mPoint = new Point();
        id = System.currentTimeMillis();
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    /**
     * 相对于之前的中心点做偏移
     *
     * @param dx dx
     * @param dy dy
     */
    public void addCenterPosition(int dx, int dy) {
        setCenterPosition(mPoint.x + dx, mPoint.y + dy);
    }

    /**
     * 设置中心点
     *
     * @param x x
     * @param y y
     */
    public void setCenterPosition(int x, int y) {
        mPoint.x = x;
        mPoint.y = y;
        int offsetW = w / 2;
        int offsetH = h / 2;
        //设置完中心点后，重新设置所在区域的矩形
        mRect.set(x - offsetW, y - offsetH, x + offsetW, y + offsetH);
    }

    public void setBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public Point getCenterPosition() {
        return mPoint;
    }

    public Rect getRect() {
        return mRect;
    }

    /**
     * h画到屏幕上
     *
     * @param canvas canvas
     */
    public void draw(Canvas canvas) {
        canvas.drawBitmap(getBitmap(), null, getRect(), null);
    }

    @Override
    public int hashCode() {
        return String.valueOf(id).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Floating && obj.hashCode() == this.hashCode();
    }
}
