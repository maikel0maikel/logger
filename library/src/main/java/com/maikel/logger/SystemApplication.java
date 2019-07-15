package com.maikel.logger;

import android.app.Application;
import android.content.Context;

import com.maikel.logger.constant.LogLevel;
import com.maikel.logger.constant.LogSegment;
import com.maikel.logger.crash.app.AppCrashHandler;

public class SystemApplication extends Application {
    private static Context mContext;
    private static Logger sLogger;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
        sLogger = Logger.Builder.newBuilder(getApplicationContext(), mContext.getPackageName())
                .setDebug(true)//为true 会打印日志到控制台 否则不会
                .setWriteToFile(true)//是否写入文件 为true将写入文件 默认为true
                .setLogDir(mContext.getFilesDir().getAbsolutePath())//日志记录目录 可以使用应用的名称作为日志目录名
                .setLogSegment(LogSegment.TWENTY_FOUR_HOURS)//时间切片 默认24小时
                .addWriteLevel(LogLevel.VERBOSE)//比如v级别 可以添加多个 默认写E与P级别
                .setPackageLevel(1)//日志栈层级 0-10
                .setAudoDelete(true)//配置是否自动删除日志
                .setStoreDays(5)//配置日志存储时间单位天，默认为7天（需同时设置setAudoDelete为true否则无效）
                .build();
        AppCrashHandler.getInstance().init();
    }

    public static Context getContext(){
        return mContext;
    }
    public static Logger getLogger() {
        return sLogger;
    }
}
