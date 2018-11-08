package com.lbx.huiji.util;

import android.graphics.Rect;

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

public class RectUtil {

    /**
     * 判断两个矩形是否碰撞
     *
     * @param r1 矩形1
     * @param r2 矩形2
     * @return 是否碰撞
     */
    public static boolean overlap(Rect r1, Rect r2) {
        //左
        if (r1.right < r2.left) {
            return false;
        }
        //右
        if (r1.left > r2.right) {
            return false;
        }
        //上
        if (r1.bottom < r2.top) {
            return false;
        }
        //下
        if (r1.top > r2.bottom) {
            return false;
        }
        return true;
    }
}
