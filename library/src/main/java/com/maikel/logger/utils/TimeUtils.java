package com.maikel.logger.utils;

import android.support.annotation.NonNull;
import android.text.format.DateFormat;

import com.maikel.logger.constant.ZoneOffset;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeUtils {
    private TimeUtils() {
    }

    /**
     * 把时间戳转换成对应的Calendar对象.
     *
     * @param millis 时间戳
     * @return Calendar对象
     */
    public static Calendar getCalendar(final long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar;
    }

    /**
     * 当前的UTC时间.
     *
     * @return 时间戳
     */
    public static long getCurUtcMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 把用户时区的时间戳转成UTC时间戳.
     *
     * @param millis 用户时区的时间戳
     * @return UTC时间戳
     */
    public static long getUtcMillis(final long millis) {
        Calendar calendar = getCalendar(millis);
        long offset = calendar.get(Calendar.ZONE_OFFSET);
        return millis - offset;
    }

    /**
     * 把用户时区的时间戳转成目标时区的时间戳.
     *
     * @param millis     用户时区的时间戳
     * @param zoneOffset 时区偏移
     * @return 目标时区的时间戳
     */
    public static long getMillis(final long millis, @ZoneOffset long zoneOffset) {
        return getUtcMillis(millis) + zoneOffset;
    }

    /**
     * 获取目标时区的当前时间戳.
     *
     * @return 目标时区的当前时间戳
     */
    public static long getCurMillis(@ZoneOffset long zoneOffset) {
        return getMillis(System.currentTimeMillis(), zoneOffset);
    }

    /**
     * 获取目标时区的当前日期（yyyy-MM-dd）.
     *
     * @param zoneOffset 时区偏移
     * @return 目标时区的当前日期
     */
    public static String getCurDate(@ZoneOffset long zoneOffset) {
        return format(getCurMillis(zoneOffset), "yyyy-MM-dd");
    }

    /**
     * 格式化时间戳.
     *
     * @param millis 时间戳
     * @param fmt    时间格式
     * @return 格式化后的时间文本
     */
    public static String format(long millis, @NonNull SimpleDateFormat fmt) {

        return fmt.format(millis);
    }
    /**
     * 格式化时间戳.
     *
     * @param millis 时间戳
     * @param fmt    时间格式
     * @return 格式化后的时间文本
     */
    public static String format(long millis, @NonNull String fmt) {
        return DateFormat.format(fmt, millis).toString();
    }
    /**
     * 获取目标时区的当前时间.
     *
     * @param zoneOffset 时区偏移
     * @param fmt        时间格式
     * @return 目标时区的当前时间
     */
    public static String getCurTime(@ZoneOffset long zoneOffset, @NonNull SimpleDateFormat fmt) {
        return format(getCurMillis(zoneOffset), fmt);
    }

    /**
     * 获取当前小时（0-23）.
     *
     * @param zoneOffset 时区偏移
     * @return 当前小时
     */
    public static int getCurHour(@ZoneOffset long zoneOffset) {
        Calendar calendar = getCalendar(getCurMillis(zoneOffset));
        return calendar.get(Calendar.HOUR_OF_DAY);
    }
}
