package com.lbx.huiji.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.lbx.huiji.bean.Bullet;
import com.lbx.huiji.bean.Floating;
import com.lbx.huiji.bean.FoePlane;
import com.lbx.huiji.bean.MyPlane;
import com.lbx.huiji.util.RectUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

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
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder mHolder;
    /**
     * 线程池
     */
    private ScheduledExecutorService mThread;
    /**
     * 我方飞机(大热狗)
     */
    private MyPlane myPlane;
    /**
     * GameView的宽高
     */
    private int w, h;
    private Canvas mCanvas;
    /**
     * 出现在屏幕上的敌机(没有热狗的王老板)最大数量
     */
    private static final int MAX_FOE_COUNT = 10;
    /**
     * 屏幕刷新间隔 毫秒
     */
    private static final int REFRESH_TIME = 10;
    /**
     * 敌机(没有热狗的王老板)刷新间隔 毫秒
     */
    private static final int FOE_REFRESH_TIME = 100;
    /**
     * 子弹(热狗)大小：宽度  这里默认长度是宽度的3倍
     */
    private static final int BULLET_SIZE = 35;
    /**
     * 子弹(热狗)速度，每刷新一次屏幕，子弹移动的像素数量
     */
    private static final int BULLET_SPEED = 10;
    /**
     * 发射子弹(热狗)的时间间隔
     */
    private static final int BULLET_ADD_SPEED = 500;
    /**
     * 敌机(没有热狗的王老板)速度，每刷新一次屏幕，敌机移动的像素数量
     */
    private static final int FOE_PLANE_SPEED = 3;
    /**
     * 敌机(没有热狗的王老板)集合，只有在集合里的敌机才会显示出来
     */
    private List<FoePlane> mFoeList;
    /**
     * 子弹(热狗)集合，只有在集合里的子弹才会显示出来
     */
    private List<Bullet> mBulletList;
    /**
     * 敌机(没有热狗的王老板)大小
     */
    private int mFoeSize;
    /**
     * 是否游戏结束
     */
    private boolean gameOver;
    /**
     * 随机帮助类
     */
    private Random r;

    public GameView(@NonNull Context context) {
        this(context, null);
    }

    public GameView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GameView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        r = new Random();
        // 设置可获得焦点
        setFocusable(true);
        setFocusableInTouchMode(true);
        // 设置常亮
        this.setKeepScreenOn(true);
        // 设置画布 背景透明
        setZOrderOnTop(true);
        mHolder = getHolder();
        mHolder.setFormat(PixelFormat.TRANSLUCENT);
        mHolder.addCallback(this);
        mFoeList = new ArrayList<>();
        mBulletList = new ArrayList<>();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        this.h = h;
        int ph = w / 5;
        int pw = ph / 3;
        //根据窗口大小设置出自己的飞机大小
        myPlane = new MyPlane(getContext(), pw, ph);
        //根据窗口大小设置出敌机大小
        mFoeSize = w / 6;
    }

    private int x, y;
    /**
     * 是否点击在自己的飞机上
     */
    private boolean touchOnPlant;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = (int) event.getRawX();
                y = (int) event.getRawY();
                //如果点击的点在我们的飞机内
                if (myPlane.getRect().contains(x, y)) {
                    //可以移动飞机
                    touchOnPlant = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //如果可以移动飞机
                if (touchOnPlant) {
                    int dx = (int) (event.getRawX() - x);
                    int dy = (int) (event.getRawY() - y);
                    //设置飞机的偏移量
                    myPlane.addCenterPosition(dx, dy);
                    x = (int) event.getRawX();
                    y = (int) event.getRawY();
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                //不可以移动飞机
                touchOnPlant = false;
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (mThread == null) {
            //手动创建线程池，进行绘制
            mThread = new ScheduledThreadPoolExecutor(1, (ThreadFactory) Thread::new);
            mThread.scheduleWithFixedDelay(GameView.this::draw, 0, REFRESH_TIME, TimeUnit.MILLISECONDS);
            mThread.scheduleWithFixedDelay(GameView.this::addFoe, 0, FOE_REFRESH_TIME, TimeUnit.MILLISECONDS);
            mThread.scheduleWithFixedDelay(GameView.this::addBullet, 0, BULLET_ADD_SPEED, TimeUnit.MILLISECONDS);
            //初始化游戏
            again();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //页面销毁，停止并回收线程池
        mThread.shutdown();
        mThread = null;
    }

    /**
     * 添加敌机
     */
    private void addFoe() {
        //如果集合数量小于设置的最大敌机量
        if (mFoeList.size() < MAX_FOE_COUNT) {
            FoePlane foePlane = new FoePlane(getContext(), mFoeSize);
            //随机敌机出现的x点坐标
            int pos = r.nextInt(w);
            int min = mFoeSize;
            int max = w - mFoeSize;
            //为了防止敌机只在屏幕中出现一半，所以做了下面两个判断
            if (pos < min) {
                pos = min;
            }
            if (pos > max) {
                pos = max;
            }
            //设置敌机的出现坐标
            foePlane.setCenterPosition(pos, 0);
            mFoeList.add(foePlane);
        }
    }

    /**
     * 添加子弹
     */
    private void addBullet() {
        Bullet bullet = new Bullet(getContext(), BULLET_SIZE, BULLET_SIZE * 3);
        Rect rect = myPlane.getRect();
        //设置子弹出现的坐标 从飞机的中间上方出现
        bullet.setCenterPosition(rect.centerX(), rect.top);
        mBulletList.add(bullet);
    }

    /**
     * 最重要的画
     */
    private void draw() {
        try {
            mCanvas = mHolder.lockCanvas();
            if (mCanvas != null) {
                if (!gameOver) {
                    //画背景
                    drawBg();
                    //画我的飞机
                    drawMyPlane();
                    //画敌机
                    drawFoePlane();
                    //画子弹
                    drawBullet();
                    //判断是否子弹击中敌机
                    checkHit();
                    //判断是否游戏结束
                    checkGameOver();
                } else {
                    drawGameOver();
                }
            }
        } catch (Exception ignored) {
        } finally {
            if (mCanvas != null) {
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    /**
     * 画背景
     */
    private void drawBg() {
        mCanvas.drawColor(Color.WHITE);
    }

    /**
     * 画我的飞机
     */
    private void drawMyPlane() {
        myPlane.draw(mCanvas);
    }

    /**
     * 画敌机
     */
    private void drawFoePlane() {
        if (mFoeList != null) {
            Iterator<FoePlane> iterator = mFoeList.iterator();
            //遍历数组，这里之所以不用for循环，是因为遍历数组的时候不能remove元素，否则会抛异常
            while (iterator.hasNext()) {
                FoePlane foePlane = iterator.next();
                //画飞机
                foePlane.draw(mCanvas);
                //如果飞机没有被子弹打中销毁
                if (!foePlane.isDestroy()) {
                    //让飞机向下飞一段距离
                    foePlane.addCenterPosition(0, FOE_PLANE_SPEED);
                }
                //如果飞机的上边大于窗口下方，则飞机飞出了屏幕，那么移除就好了
                if (foePlane.getRect().top > h) {
                    iterator.remove();
                }
            }
        }
    }

    /**
     * 画子弹
     */
    private void drawBullet() {
        if (mBulletList != null) {
            Iterator<Bullet> iterator = mBulletList.iterator();
            //遍历数组，这里之所以不用for循环，是因为遍历数组的时候不能remove元素，否则会抛异常
            while (iterator.hasNext()) {
                Bullet bullet = iterator.next();
                //画子弹
                bullet.draw(mCanvas);
                //让子弹向下飞一段距离
                bullet.addCenterPosition(0, -BULLET_SPEED);
                //如果子弹的下边大于窗口上方，则子弹飞出了屏幕，那么移除就好了
                if (bullet.getRect().bottom < 0) {
                    iterator.remove();
                }
            }
        }
    }

    /**
     * 判断是否子弹击中敌机
     */
    private void checkHit() {
        Iterator<FoePlane> foeIterator = mFoeList.iterator();
        //遍历数组，这里之所以不用for循环，是因为遍历数组的时候不能remove元素，否则会抛异常
        //遍历敌机
        while (foeIterator.hasNext()) {
            FoePlane foePlane = foeIterator.next();
            //获取敌机所在位置的矩形
            Rect foePlaneRect = foePlane.getRect();
            //如果敌机没有被子弹击中销毁,则进行下方判断子弹是否击中敌机的逻辑
            if (!foePlane.isDestroy()) {
                Iterator<Bullet> bulletIterator = mBulletList.iterator();
                //遍历子弹
                while (bulletIterator.hasNext()) {
                    Bullet bullet = bulletIterator.next();
                    //获取子弹所在位置的矩形
                    Rect bulletRect = bullet.getRect();
                    //判断子弹和敌机所在的矩形是否发生碰撞
                    if (RectUtil.overlap(foePlaneRect, bulletRect)) {
                        //如果子弹击中了敌机，设置敌机为销毁状态
                        foePlane.setDestroy(true);
                        //在屏幕中移除击中敌机的子弹
                        bulletIterator.remove();
                    }
                }
            }
            //判断敌机是否销毁并可以移除屏幕
            if (foePlane.canDestroyAndRemove()) {
                foeIterator.remove();
            }
        }
    }

    /**
     * 游戏结束后的画面
     */
    private void drawGameOver() {
        mCanvas.drawColor(Color.BLACK);
    }

    /**
     * 判断是否游戏结束
     */
    private void checkGameOver() {
        //遍历敌机
        for (Floating p : mFoeList) {
            //如果我方飞机和敌机所在矩形发生碰撞
            if (RectUtil.overlap(myPlane.getRect(), p.getRect())) {
                //设置游戏结束
                gameOver = true;
                if (mOnGameListener != null) {
                    //在主线程回调
                    XTools.UiUtil().runOnUIThread(() -> mOnGameListener.onGameOver());
                }
                break;
            }
        }
    }

    /**
     * 再来一次
     */
    public void again() {
        mFoeList.clear();
        //设置初始位置
        myPlane.setCenterPosition(w / 2, h - myPlane.getH());
        gameOver = false;
        if (mOnGameListener != null) {
            //在主线程回调
            XTools.UiUtil().runOnUIThread(() -> mOnGameListener.onStart());
        }
    }

    private OnGameListener mOnGameListener;

    public interface OnGameListener {
        /**
         * 游戏开始
         */
        void onStart();

        /**
         * 游戏失败
         */
        void onGameOver();
    }

    public void setOnGameListener(OnGameListener onGameListener) {
        mOnGameListener = onGameListener;
    }
}
