package com.maikel.logger;

import android.support.annotation.NonNull;


public class LogTools {
    private static Logger logger;
    static {
        if (SystemApplication.getLogger() == null) {
            if (SystemApplication.getContext() == null) {
                logger = Logger.Builder.newBuilder("HBLog").setDebug(true)
                        .setLogDir("HBDvrVideoMonitor")
                        .setWriteToFile(true)
                        .setPackageLevel(1).build();
            } else {
                logger = Logger.Builder.newBuilder(SystemApplication.getContext(), "HBLog")
                        .setDebug(true)
                        .setLogDir(SystemApplication.getContext().getPackageName())
                        .setWriteToFile(true)
                        .setPackageLevel(1).build();
            }
        }else {
            logger = SystemApplication.getLogger();
        }
    }
    private LogTools(){}
    public static void v(@NonNull String tag, @NonNull String message) {
        logger.v(tag, message);
    }

    public static void v(@NonNull String message) {
        logger.v(message);
    }

    public static void d(@NonNull String tag, @NonNull String message) {
        logger.d(tag, message);
    }

    public static void d(@NonNull String message) {
        logger.d(message);
    }

    public static void i(@NonNull String tag, @NonNull String message) {
        logger.i(tag, message);
    }

    public static void i(@NonNull String message) {
        logger.i(message);
    }

    public static void w(@NonNull String tag, @NonNull String message) {
        logger.w(tag, message);
    }

    public static void w(@NonNull String message) {
        logger.w(message);
    }

    public static void j(@NonNull String tag, @NonNull String message) {
        logger.j(tag, message);
    }

    public static void j(@NonNull String message) {
        logger.j(message);
    }

    public static void e(@NonNull String tag, @NonNull String message) {
        logger.e(tag, message);
    }

    public static void e(@NonNull String message) {
        logger.e(message);
    }

    public static void e(@NonNull String tag, Throwable t, @NonNull String message) {
        logger.e(tag, t, message);
    }

    public static void e(Throwable t, @NonNull String message) {
        logger.e(t, message);
    }
    public static void e(@NonNull String tag,@NonNull Throwable t) {
        logger.e(tag,t);
    }
    public static void e(@NonNull Throwable t) {
        logger.e(t);
    }
    public static void p(@NonNull String tag, @NonNull String message) {
        logger.p(tag, message);
    }

    public static void p(@NonNull String message) {
        logger.p(message);
    }

    public static void p(@NonNull String tag, Throwable t, @NonNull String message) {
        logger.p(tag, t, message);
    }

    public static void p(Throwable t, @NonNull String message) {
        logger.p(t, message);
    }

    public static void p(@NonNull String tag,@NonNull Throwable t) {
        logger.p(tag,t);
    }
    public static void p(@NonNull Throwable t) {
        logger.p(t);
    }

    public static void wtf(@NonNull String tag, @NonNull String message) {
        logger.wtf(tag, message);
    }

    public static void wtf(@NonNull String message) {
        logger.wtf(message);
    }

    public static void wtf(@NonNull String tag, Throwable t, @NonNull String message) {
        logger.wtf(tag, t, message);
    }

    public static void wtf(Throwable t, @NonNull String message) {
        logger.wtf(t, message);
    }
    public static void wtf(@NonNull String tag,@NonNull Throwable t) {
        logger.wtf(tag,t);
    }
    public static void wtf(@NonNull Throwable t) {
        logger.wtf(t);
    }
}
