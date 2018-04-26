package com.sinohb.logger.printer;

import android.content.Context;
import android.support.annotation.NonNull;

import com.sinohb.logger.constant.LogLevel;
import com.sinohb.logger.constant.LogSegment;
import com.sinohb.logger.constant.ZoneOffset;
import com.sinohb.logger.utils.SysUtils;

import java.text.SimpleDateFormat;

public interface IPrinter {
    String LINE_SEPARATOR = SysUtils.getLineSeparator();

    void printConsole(@LogLevel String level, String tag, String message, StackTraceElement element);

    void printFile(@NonNull Context context, @LogLevel String level, String tag, String message, StackTraceElement element,
                   @ZoneOffset long offset, SimpleDateFormat timeFormat, String logDir, String logPrefix, @LogSegment int logSegment);

}
