package com.sinohb.maikel.crash.nt;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.Callable;

public class NativeCarshHandlerService extends IntentService{
    public NativeCarshHandlerService() {
        super("NativeCarshHandlerService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

    static class NativeCrashRunnable implements Callable{

        @Override
        public Object call() throws Exception {
            try {
                Log.e("crash", "doInBackground begin");
                Process process = Runtime.getRuntime().exec(new String[]{"logcat", "-d", "-t", "500", "-v", "threadtime"});
                //logText = Thread.UncaughtExceptionHandler.readFromLogcat(process.getInputStream());
                Log.e("crash", "doInBackground end");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


}
