package com.sinohb.logger;

import android.support.annotation.NonNull;


public class LogTools {
    private static Logger logger;

    static {
        if (SystemApplication.getContext() == null) {
            logger = Logger.Builder.newBuilder("HBLog").setDebug(true)
                    .setLogDir("HBApplication")
                    .setWriteToFile(true)
                    .setPackageLevel(1).build();
        } else {
            logger = Logger.Builder.newBuilder(SystemApplication.getContext(), "HBLog")
                    .setDebug(true)
                    .setLogDir("HBApplication")
                    .setWriteToFile(true)
                    .setPackageLevel(1).build();
        }
    }

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

}
