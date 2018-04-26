package com.sinohb.logger;

import android.app.Application;
import android.content.Context;

public class SystemApplication extends Application{
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
    }

    public static Context getContext(){
        return mContext;
    }
}
