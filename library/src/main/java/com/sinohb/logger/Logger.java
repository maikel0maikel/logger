package com.sinohb.logger;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.util.Log;

import com.sinohb.logger.constant.LogLevel;
import com.sinohb.logger.constant.LogSegment;
import com.sinohb.logger.constant.ZoneOffset;
import com.sinohb.logger.printer.DefaultPrinter;
import com.sinohb.logger.printer.IPrinter;
import com.sinohb.logger.printer.JsonPrinter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Logger {
    private static final String LOG_CLASS_NAME = Logger.class.getName();
    private static final String LOG_PRINT_METHOD_NAME = "printLog";
    private static final String TAG = "Logger";
    private IPrinter mDefaultPrinter;
    private IPrinter mJsonPrinter;

    private Context mContext;
    @LogSegment
    private int mLogSegment;
    private String mName;
    private String mLogDir;
    private boolean isWriteToFile;
    private boolean mDebug;
    @ZoneOffset
    private long mZoneOffset;
    //private String mTimeFormat;
    private SimpleDateFormat mTimeFormat;
    @IntRange(from = 0, to = 10)
    private int mPackageLevel;
    private String mLogPrefix;
    private List<String> mWriteFileLevels;

    private Logger(Builder builder) {
        this.mContext = builder.mContext;
        this.mName = builder.mName;
        this.mLogDir = builder.mLogDir;
        this.isWriteToFile = builder.isWriteToFile;
        this.mDebug = builder.mDebug;
        this.mZoneOffset = builder.mZoneOffset;
        this.mTimeFormat = builder.mTimeFormat;
        this.mPackageLevel = builder.mPackageLevel;
        this.mLogPrefix = builder.mLogPrefix;
        this.mLogSegment = builder.mLogSegment;
        this.mWriteFileLevels = builder.mWriteFileLevels;

        mDefaultPrinter = new DefaultPrinter();
        mJsonPrinter = new JsonPrinter();

    }

    public void v(@NonNull String tag, @NonNull String message) {
        printLog(LogLevel.VERBOSE, tag, null, message);
    }

    public void v(@NonNull String message) {
        printLog(LogLevel.VERBOSE, TAG, null, message);
    }

    public void d(@NonNull String tag, @NonNull String message) {
        printLog(LogLevel.DEBUG, tag, null, message);
    }

    public void d(@NonNull String message) {
        printLog(LogLevel.DEBUG, TAG, null, message);
    }

    public void i(@NonNull String tag, @NonNull String message) {
        printLog(LogLevel.INFO, tag, null, message);
    }

    public void i(@NonNull String message) {
        printLog(LogLevel.INFO, TAG, null, message);
    }

    public void w(@NonNull String tag, @NonNull String message) {
        printLog(LogLevel.WARN, tag, null, message);
    }

    public void w(@NonNull String message) {
        printLog(LogLevel.WARN, TAG, null, message);
    }

    public void j(@NonNull String tag, @NonNull String message) {
        printLog(LogLevel.JSON, tag, null, message);
    }

    public void j(@NonNull String message) {
        printLog(LogLevel.JSON, TAG, null, message);
    }

    public void e(@NonNull String tag, @NonNull String message) {
        printLog(LogLevel.ERROR, tag, null, message);
    }

    public void e(@NonNull String message) {
        printLog(LogLevel.ERROR, TAG, null, message);
    }

    public void e(@NonNull String tag, Throwable t, @NonNull String message) {
        printLog(LogLevel.ERROR, tag, t, message);
    }

    public void e(Throwable t, @NonNull String message) {
        printLog(LogLevel.ERROR, TAG, t, message);
    }

    public void wtf(@NonNull String tag, @NonNull String message) {
        printLog(LogLevel.PRINT, tag, null, message);
    }

    public void wtf(@NonNull String message) {
        printLog(LogLevel.PRINT, TAG, null, message);
    }

    public void wtf(@NonNull String tag, Throwable t, @NonNull String message) {
        printLog(LogLevel.PRINT, tag, t, message);
    }

    public void wtf(Throwable t, @NonNull String message) {
        printLog(LogLevel.PRINT, TAG, t, message);
    }

    private void printLog(@LogLevel String level, String tag, Throwable t, String message) {
        String logMsg = message;
        if (logMsg == null || logMsg.length() == 0) {
            if (t == null) {//不打印或记录空消息和没有异常的日志
                return;
            }
            logMsg = Log.getStackTraceString(t);
        } else {
            if (t != null) {
                logMsg += Log.getStackTraceString(t) + getStackTrace(t);
            }
        }
//        StackTraceElement[] elements = new Throwable().getStackTrace();
        StackTraceElement elements[] = Thread.currentThread().getStackTrace();
        int index = getStackIndex(elements);
        if (index == -1) {
            return;
        }

        //StackTraceElement element = elements[5];//elements[index];
        StackTraceElement element = elements[index];
        if (tag == null || tag.length() == 0) {
            tag = getTag(element);
        }
        switch (level) {
            case LogLevel.VERBOSE:
            case LogLevel.DEBUG:
            case LogLevel.INFO:
            case LogLevel.WARN:
                if (mDebug) {
                    mDefaultPrinter.printConsole(level, tag, logMsg, element);
                }
                break;
            case LogLevel.ERROR:
            case LogLevel.PRINT:
                if (mDebug) {
                    mDefaultPrinter.printConsole(level, tag, logMsg, element);
                }
                if (isWriteToFile && mWriteFileLevels.contains(level)) {
                    mDefaultPrinter.printFile(mContext, level, tag, logMsg, element, mZoneOffset,
                            mTimeFormat, mLogDir, mLogPrefix, mLogSegment);
                }
                break;
            case LogLevel.JSON:
                if (mDebug) {
                    mJsonPrinter.printConsole(level, tag, logMsg, element);
                }
                if (isWriteToFile && mWriteFileLevels.contains(level)) {
                    mJsonPrinter.printFile(mContext, level, tag, logMsg, element, mZoneOffset,
                            mTimeFormat, mLogDir, mLogPrefix, mLogSegment);
                }
                break;
            default:
                break;
        }
    }

    private String getStackTrace(@NonNull Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }

    /**
     * 获取调用日志类输出方法的堆栈元素索引.
     *
     * @param elements 堆栈元素
     * @return 索引位置，-1 - 不可用
     */
    private int getStackIndex(@NonNull StackTraceElement[] elements) {
        boolean isChecked = false;
        StackTraceElement element;
        for (int i = 0; i < elements.length; i++) {
            element = elements[i];
            if (LOG_CLASS_NAME.equals(element.getClassName())
                    && LOG_PRINT_METHOD_NAME.equals(element.getMethodName())) {
                isChecked = true;
            }
            if (isChecked) {
                int index = i + 2 + mPackageLevel;
                if (index < elements.length) {
                    return index;
                }
            }
        }
        return -1;
    }

    private String getTag(StackTraceElement element) {

        int lastIndex = element.getClassName().lastIndexOf('.');
        int index = lastIndex + 1;
        if (lastIndex > 0 && index < element.getClassName().length()) {
            return element.getClassName().substring(index);
        }
        return element.getClassName();
    }

    public static class Builder {
        private Context mContext;
        @LogSegment
        private int mLogSegment;
        private String mName;
        private String mLogDir;
        private boolean isWriteToFile;
        private boolean mDebug;
        @ZoneOffset
        private long mZoneOffset;
        //private String mTimeFormat;
        @IntRange(from = 0, to = 10)
        private int mPackageLevel;
        private String mLogPrefix;
        private SimpleDateFormat mTimeFormat;
        private List<String> mWriteFileLevels;

        private Builder(Context context, String name) {
            mContext = context;
            mLogSegment = LogSegment.TWENTY_FOUR_HOURS;
            mName = name;
            mDebug = true;
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                mLogDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/HBSystem";
            } else {
                mLogDir = mContext.getFilesDir().getAbsolutePath() + "/HBSystem";
            }
            isWriteToFile = true;
            mZoneOffset = ZoneOffset.P0800;
            mTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            mPackageLevel = 0;
            mLogPrefix = "";
            mWriteFileLevels = new ArrayList<>();
            mWriteFileLevels.add(LogLevel.ERROR);
            mWriteFileLevels.add(LogLevel.PRINT);
        }

        public static Builder newBuilder(Context context, String name) {
            return new Builder(context, name);
        }

        public static Builder newBuilder(String name) {
            return new Builder(null, name);
        }

        public Builder setLogSegment(@LogSegment int segment) {
            this.mLogSegment = segment;
            return this;
        }

        public Builder setLogDir(String dir) {
            this.mLogDir = dir;
            return this;
        }

        public Builder setWriteToFile(boolean isWriteToFile) {
            this.isWriteToFile = isWriteToFile;
            return this;
        }

        public Builder setDebug(boolean debug) {
            this.mDebug = debug;
            return this;
        }

        public Builder setZoneOffset(@ZoneOffset long zoneOffset) {
            this.mZoneOffset = zoneOffset;
            return this;
        }

        public Builder setTimeFormat(String timeFormat) {
            this.mTimeFormat = new SimpleDateFormat(timeFormat);
            return this;
        }

        public Builder setPackageLevel(@IntRange(from = 0, to = 10) int packageLevel) {
            this.mPackageLevel = packageLevel;
            return this;
        }

        public Builder setLogPrefix(String logPrefix) {
            this.mLogPrefix = logPrefix;
            return this;
        }

        public Builder addWriteLevel(@LogLevel String logLevel) {
            if (mWriteFileLevels != null && (!mWriteFileLevels.contains(logLevel))) {
                mWriteFileLevels.add(logLevel);
            }
            return this;
        }

        public Logger build() {
            Logger logger = new Logger(this);
            return logger;
        }
    }


}