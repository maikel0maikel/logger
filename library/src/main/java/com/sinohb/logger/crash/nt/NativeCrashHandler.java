package com.sinohb.logger.crash.nt;

import android.content.Intent;

import com.sinohb.logger.LogTools;
import com.sinohb.logger.SystemApplication;

public class NativeCrashHandler {

    public static void onNativeCrashed() {
        LogTools.d("handller", "handle");
        new RuntimeException("crashed here (native trace should follow after the Java trace)").printStackTrace();
        Intent intent = new Intent(SystemApplication.getContext(),NativeCarshHandlerService.class);
        SystemApplication.getContext().startService(intent);
    }
}
