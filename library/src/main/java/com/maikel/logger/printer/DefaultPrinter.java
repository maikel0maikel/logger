package com.maikel.logger.printer;

import android.content.Context;

import com.maikel.logger.constant.LogLevel;
import com.maikel.logger.utils.LogUtils;

import java.text.SimpleDateFormat;

public class DefaultPrinter implements IPrinter {
    @Override
    public void printConsole(@LogLevel String level, String tag, String message, StackTraceElement element) {
        LogUtils.printLog(level,tag,message,element);
    }

    @Override
    public void printFile(Context context, @LogLevel String level, String tag, String message, StackTraceElement element,
                          long offset, SimpleDateFormat timeFormat,String parentDir, String logDir,
                          String logPrefix, int logSegment) {
        synchronized (IPrinter.class){
            LogUtils.printFile(context,level,tag,message,element,offset,timeFormat,parentDir,logDir,logPrefix,logSegment);
        }
    }
}
