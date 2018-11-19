package com.sinohb.maikel.printer;

import android.content.Context;

import com.sinohb.maikel.constant.LogLevel;
import com.sinohb.maikel.utils.LogUtils;

import java.text.SimpleDateFormat;

public class DefaultPrinter implements IPrinter {
    @Override
    public void printConsole(@LogLevel String level, String tag, String message, StackTraceElement element) {
        LogUtils.printLog(level,tag,message,element);
    }

    @Override
    public void printFile(Context context, @LogLevel String level, String tag, String message, StackTraceElement element,
                          long offset, SimpleDateFormat timeFormat, String logDir,
                          String logPrefix, int logSegment) {
        synchronized (IPrinter.class){
            LogUtils.printFile(context,level,tag,message,element,offset,timeFormat,logDir,logPrefix,logSegment);
        }
    }
}
