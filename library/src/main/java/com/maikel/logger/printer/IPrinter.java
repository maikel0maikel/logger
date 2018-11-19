package com.maikel.logger.printer;

import android.content.Context;
import android.support.annotation.NonNull;

import com.maikel.logger.constant.LogLevel;
import com.maikel.logger.constant.LogSegment;
import com.maikel.logger.constant.ZoneOffset;
import com.maikel.logger.utils.SysUtils;

import java.text.SimpleDateFormat;

public interface IPrinter {
    String LINE_SEPARATOR = SysUtils.getLineSeparator();

    void printConsole(@LogLevel String level, String tag, String message, StackTraceElement element);

    void printFile(@NonNull Context context, @LogLevel String level, String tag, String message, StackTraceElement element,
                   @ZoneOffset long offset, SimpleDateFormat timeFormat, String logDir, String logPrefix, @LogSegment int logSegment);

}
