package com.sinohb.logger.utils;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.sinohb.logger.R;
import com.sinohb.logger.constant.LogLevel;
import com.sinohb.logger.constant.LogSegment;
import com.sinohb.logger.constant.ZoneOffset;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LogUtils {
    private static final String TAG = "LogUtils";
    private static final int MAX_LOG_LENGTH = 4000;//一行最大
    private static final String LOG_FILE_EXTEND = ".log";
    private static final String PRINT_CONSOLE_FORMAT = "(%1$s:line%2$d)#%3$s Thread:%4$s " + ": " + "%5$s";
    private static final String PRINT_FILE_FORMAT = "[%1$s %2$s/%3$s:line%4$d Thread: %5$s]:" + "%6$s" + "\n";

    private static final ExecutorService sExecutorService;

    static {
        sExecutorService = Executors.newSingleThreadExecutor();
    }

    private LogUtils() {
    }

    public static void printLog(@LogLevel String level, @NonNull String tag, @NonNull String message, StackTraceElement element) {
        String output = decorateMsgForConsole(message, element);
        int numL = output.length() / MAX_LOG_LENGTH;

        if (numL > 0) {
            int index = 0;
            for (int i = 0; i < numL; i++) {
                int lastIndex = index + MAX_LOG_LENGTH;
                String log = output.substring(index, lastIndex);
                print(level, tag, log);
                index = lastIndex;
            }
            print(level, tag, output.substring(index, output.length()));
        } else {
            print(level, tag, output);
        }
    }

    public static void printFile(Context context, @LogLevel String level, String tag, String message, StackTraceElement element,
                                 @ZoneOffset long zoneOffset, SimpleDateFormat timeFormat, String logDir,
                                 String logPrefix, @LogSegment int logSegment) {
        String dirPath = genDirPath(logDir);
        String fileName = genFileName(logPrefix, logSegment, zoneOffset);
        String saveContent = decorateMsgForFile(level, tag, message, element, zoneOffset, timeFormat);
        writeFile(context, dirPath, fileName, saveContent);
    }

    private static void print(@LogLevel String level, @NonNull String tag, @NonNull String log) {
        switch (level) {
            case LogLevel.VERBOSE:
                Log.v(tag, log);
                break;
            case LogLevel.DEBUG:
                Log.d(tag, log);
                break;
            case LogLevel.INFO:
                Log.i(tag, log);
                break;
            case LogLevel.WARN:
                Log.w(tag, log);
                break;
            case LogLevel.ERROR:
                Log.e(tag, log);
                break;
            case LogLevel.JSON:
                Log.i(tag, log);
                break;
            case LogLevel.PRINT:
                Log.wtf(tag, log);
                break;
            default:
                break;
        }
    }

    private static void writeFile(final Context context, final String dirPath, final String fileName, final String content) {
        sExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                String filePath = dirPath + File.separator + fileName;
                FileOutputStream fos = null;
                OutputStreamWriter osw = null;
                BufferedWriter writer = null;
                try {
                    if (FileUtils.createDir(dirPath)) {
                        String outContent = content;
                        if (!FileUtils.isExist(dirPath + File.separator + fileName) && context != null) {
                            outContent = genInfo(context) + outContent;
                        }
                        File file = new File(filePath);
                        fos = new FileOutputStream(file, true);
                        osw = new OutputStreamWriter(fos, Charset.forName("UTF-8"));
                        writer = new BufferedWriter(osw);
                        writer.write(outContent);
                        writer.flush();
                    }
                } catch (IOException e) {
                    Log.e(TAG, "写日志异常", e);
                } finally {
                    IOUtils.closeQuietly(fos);
                    IOUtils.closeQuietly(osw);
                    IOUtils.closeQuietly(writer);
                }
            }
        });
    }

    /**
     * 生成日志目录路径.
     *
     * @param logDir 日志保存的目录
     * @return 日志目录路径
     */
    public static String genDirPath(@NonNull String logDir) {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "HBSystem"
                + File.separator + logDir;
    }

    /**
     * 生成日志文件名.
     *
     * @param logPrefix  日志前缀
     * @param logSegment 日志切片
     * @param zoneOffset 时区偏移
     * @return 日志文件名
     */
    public static String genFileName(String logPrefix, @LogSegment int logSegment,
                                     @ZoneOffset long zoneOffset) {
        logPrefix = TextUtils.isEmpty(logPrefix) ? "" : logPrefix + "_";
        String curDate = TimeUtils.getCurDate(zoneOffset);
        String fileName;
        if (logSegment == LogSegment.TWENTY_FOUR_HOURS) {
            fileName = logPrefix + curDate + LOG_FILE_EXTEND;
        } else {
            fileName = logPrefix + curDate + "_" + getCurSegment(logSegment, zoneOffset) + LOG_FILE_EXTEND;
        }
        return fileName;
    }

    /**
     * 根据切片时间获取当前的时间段.
     *
     * @param logSegment 日志切片
     * @param zoneOffset 时区偏移
     * @return 比如“0001”表示00:00-01:00
     */
    public static String getCurSegment(@LogSegment int logSegment,
                                       @ZoneOffset long zoneOffset) {
        int hour = TimeUtils.getCurHour(zoneOffset);
        int start = hour - hour % logSegment;
        int end = start + logSegment;
        if (end == 24) {
            end = 0;
        }
        return getDoubleNum(start) + getDoubleNum(end);
    }

    /**
     * 对于1-9的数值进行前置补0.
     *
     * @param num 数值
     * @return num在[0, 9]时前置补0，否则返回原值
     */
    private static String getDoubleNum(int num) {
        return num < 10 ? "0" + num : String.valueOf(num);
    }

    /**
     * 装饰打印到控制台的信息.
     *
     * @param message 信息
     * @param element 对战元素
     * @return 装饰后的信息
     */
    public static String decorateMsgForConsole(@NonNull String message,
                                               @NonNull StackTraceElement element) {
        String methodName = element.getMethodName();
        int lineNumber = element.getLineNumber();
        //String fileName = element.getFileName();
        String className = element.getClassName();
        String threadName = Thread.currentThread().getName();
        return String.format(PRINT_CONSOLE_FORMAT, className, lineNumber, methodName, threadName, message);
    }

    /**
     * 装饰打印到文件的信息.
     *
     * @param level      级别
     * @param message    信息
     * @param element    堆栈元素
     * @param zoneOffset 时区偏移
     * @param timeFmt    时间格式
     * @return 装饰后的信息
     */
    public static String decorateMsgForFile(@LogLevel String level, String tag, @NonNull String message,
                                            @NonNull StackTraceElement element, @ZoneOffset long zoneOffset,
                                            @NonNull SimpleDateFormat timeFmt) {
        String time = TimeUtils.getCurTime(zoneOffset, timeFmt);
        //String fileName = element.getFileName();
        String className = element.getClassName();
        int lineNum = element.getLineNumber();
        String threadName = Thread.currentThread().getName();
        return String.format(PRINT_FILE_FORMAT, time, level, className, lineNum, threadName,
                "[" + tag + "] " + message);
    }

    /**
     * 生成系统相关的信息.
     *
     * @param context Context
     * @return 系统相关的信息
     */
    private static String genInfo(@NonNull Context context) {
        if (context == null) {
            return "[context is null]";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("[").append(context.getString(R.string.app_package_name)).append(": ").append(SysUtils.getAppPackageName(context))
                .append(", ").append(context.getString(R.string.app_version_name)).append(": ").append(SysUtils.getAppVersionName(
                context)).append(", ").append(context.getString(R.string.app_version_code)).append(": ").append(SysUtils.getAppVersionCode(
                context)).append(", ").append(context.getString(R.string.os_version_name)).append(": ").append(SysUtils.getOsVersionName())
                .append(", ").append(context.getString(R.string.os_version_code)).append(": ").append(SysUtils.getOsVersionCode())
                .append(", ").append(context.getString(R.string.os_display_name)).append(": ").append(SysUtils.getOsVersionDisplayName())
                .append(", ").append(context.getString(R.string.brand_info)).append(": ").append(SysUtils.getBrandInfo())
                .append(", ").append(context.getString(R.string.product_info)).append(": ").append(SysUtils.getProductInfo())
                .append(", ").append(context.getString(R.string.model_info)).append(": ").append(SysUtils.getModelInfo())
                .append(", ").append(context.getString(R.string.manufacturer_info)).append(": ").append(SysUtils.getManufacturerInfo())
                .append("]\n");
        String info = builder.toString();
        builder.delete(0, builder.length());
        return info;
    }
}
