package com.sinohb.maikel.example.test;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.sinohb.maikel.LogTools;
import com.sinohb.maikel.crash.app.AppCrashHandler;

public class TestService extends Service implements AppCrashHandler.UncaughtExceptionListener{

    @Override
    public void onCreate() {
        super.onCreate();
        LogTools.p("TestService","onCreate");
        AppCrashHandler.getInstance().setUncaughtExceptionListener(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogTools.p("TestService","onStartCommand");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogTools.p("TestService","onDestroy");
    }

    @Override
    public void onException() {
        startService(new Intent(this,TestService.class));
        LogTools.p("TestService","onException");
    }
}
