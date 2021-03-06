package com.maikel.logger.crash.app;

import android.os.Looper;
import android.widget.Toast;

import com.maikel.logger.LogTools;
import com.maikel.logger.SystemApplication;

public class AppCrashHandler implements Thread.UncaughtExceptionHandler {

    private Thread.UncaughtExceptionHandler mDefaultUncaughtHandler;

    private static AppCrashHandler mCrashHandler = new AppCrashHandler();
    private UncaughtExceptionListener listener;

    private AppCrashHandler() {

    }

    public static AppCrashHandler getInstance() {
        return mCrashHandler;
    }

    public void init() {
        mDefaultUncaughtHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }


    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex)) {//沒有处理异常则交给系统去处理
            if (mDefaultUncaughtHandler != null) {
                LogTools.e("uncaughtException", "handleException is false and mDefaultUncaughtHandler not null");
                mDefaultUncaughtHandler.uncaughtException(thread, ex);
            } else {
                // 退出程序
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                LogTools.e(e, "InterruptedException");
            }
            // 退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }

    }

    private boolean handleException(Throwable ex) {
        LogTools.wtf(ex, "程序崩溃了---");
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(SystemApplication.getContext(), "哎呦喂，程序出错了，我要跟主人说再见啦~", Toast.LENGTH_LONG)
                        .show();
                Looper.loop();
            }
        }.start();
        if (listener != null) listener.onException(ex.getMessage());
        return true;
    }

    public interface UncaughtExceptionListener {
        void onException(String exception);
    }

    public void setUncaughtExceptionListener(UncaughtExceptionListener listener) {
        this.listener = listener;
    }
}
