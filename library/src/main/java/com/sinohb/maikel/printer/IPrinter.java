package com.sinohb.maikel.printer;

import android.content.Context;
import android.support.annotation.NonNull;

import com.sinohb.maikel.constant.LogLevel;
import com.sinohb.maikel.constant.LogSegment;
import com.sinohb.maikel.constant.ZoneOffset;
import com.sinohb.maikel.utils.SysUtils;

import java.text.SimpleDateFormat;

public interface IPrinter {
    String LINE_SEPARATOR = SysUtils.getLineSeparator();

    void printConsole(@LogLevel String level, String tag, String message, StackTraceElement element);

    void printFile(@NonNull Context context, @LogLevel String level, String tag, String message, StackTraceElement element,
                   @ZoneOffset long offset, SimpleDateFormat timeFormat, String logDir, String logPrefix, @LogSegment int logSegment);

}
