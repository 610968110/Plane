package com.lbx.huiji;

import android.app.Application;

import lbx.xtoollib.XTools;

import static lbx.xtoollib.phone.xLogUtil.LEVEL_VERBOSE;

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

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化一些工具类  这个框架不是必须的  只是为了方便打印log什么的
        XTools xTools = new XTools.Builder()
                .log(/*是否打印log*/true)
                .logTag(/*设置log的tag*/"xys")
                .logLevel(/*设置显示log的级别*/ LEVEL_VERBOSE)
                .errLogFilePath(/*设置crashLog的文件存储路径*/"JwPortal")
                .errLogFileName(/*设置crashLog的文件存储名*/"ERR")
                .errLogFile(
                        /*是否打印到文件*/true,
                        /*是否打印到log*/false)
                .logPrintFile(
                        /*是否打印log到文件*/true,
                        /*打印log文件在sd卡下的路径*/"HuiJi/log",
                        /*打印到file的log是否加密(des+base64对称加密), null为不加密*/ null)
                .build(this);
        xTools.init();
    }
}
