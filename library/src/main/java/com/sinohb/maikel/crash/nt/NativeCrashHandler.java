package com.sinohb.maikel.crash.nt;

import android.content.Intent;

import com.sinohb.maikel.LogTools;
import com.sinohb.maikel.SystemApplication;

public class NativeCrashHandler {

    public static void onNativeCrashed() {
        LogTools.d("handller", "handle");
        new RuntimeException("crashed here (native trace should follow after the Java trace)").printStackTrace();
        Intent intent = new Intent(SystemApplication.getContext(),NativeCarshHandlerService.class);
        SystemApplication.getContext().startService(intent);
    }
}
