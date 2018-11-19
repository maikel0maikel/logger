package com.sinohb.maikel;



import com.sinohb.maikel.task.DeleteService;

import java.util.HashMap;
import java.util.Map;

public class SparryLogger {

    private static Map<String, Logger> mLoggers = new HashMap();
    private static boolean isOpenTask = false;

    private SparryLogger() {
    }

    public static void addLogger(String name, Logger logger) {
        mLoggers.put(name, logger);
        if (!isOpenTask) {
            isOpenTask = startDeleteTask();
            logger.i("addLogger isOpenTask=" + isOpenTask);
        }
    }

    public static Map<String, Logger> getLoggers() {
        return mLoggers;
    }

    private static boolean startDeleteTask() {
//        if (context == null) {
//            return false;
//        }
//        Intent intent = new Intent(context, DeleteService.class);
//        context.startService(intent);
        DeleteService.onHandleIntent();
        return true;
    }
}
