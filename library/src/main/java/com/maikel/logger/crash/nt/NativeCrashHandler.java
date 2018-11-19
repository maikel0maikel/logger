package com.maikel.logger.crash.nt;

import android.content.Intent;

import com.maikel.logger.LogTools;
import com.maikel.logger.SystemApplication;

public class NativeCrashHandler {

    public static void onNativeCrashed() {
        LogTools.d("handller", "handle");
        new RuntimeException("crashed here (native trace should follow after the Java trace)").printStackTrace();
        Intent intent = new Intent(SystemApplication.getContext(),NativeCarshHandlerService.class);
        SystemApplication.getContext().startService(intent);
    }
}
