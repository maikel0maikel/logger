package com.sinohb.maikel.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.sinohb.maikel.constant.ZoneOffset.N0000;
import static com.sinohb.maikel.constant.ZoneOffset.N0100;
import static com.sinohb.maikel.constant.ZoneOffset.N0200;
import static com.sinohb.maikel.constant.ZoneOffset.N0300;
import static com.sinohb.maikel.constant.ZoneOffset.N0330;
import static com.sinohb.maikel.constant.ZoneOffset.N0400;
import static com.sinohb.maikel.constant.ZoneOffset.N0430;
import static com.sinohb.maikel.constant.ZoneOffset.N0500;
import static com.sinohb.maikel.constant.ZoneOffset.N0600;
import static com.sinohb.maikel.constant.ZoneOffset.N0700;
import static com.sinohb.maikel.constant.ZoneOffset.N0800;
import static com.sinohb.maikel.constant.ZoneOffset.N0900;
import static com.sinohb.maikel.constant.ZoneOffset.N1000;
import static com.sinohb.maikel.constant.ZoneOffset.N1100;
import static com.sinohb.maikel.constant.ZoneOffset.N1200;
import static com.sinohb.maikel.constant.ZoneOffset.P0100;
import static com.sinohb.maikel.constant.ZoneOffset.P0200;
import static com.sinohb.maikel.constant.ZoneOffset.P0300;
import static com.sinohb.maikel.constant.ZoneOffset.P0330;
import static com.sinohb.maikel.constant.ZoneOffset.P0400;
import static com.sinohb.maikel.constant.ZoneOffset.P0430;
import static com.sinohb.maikel.constant.ZoneOffset.P0500;
import static com.sinohb.maikel.constant.ZoneOffset.P0530;
import static com.sinohb.maikel.constant.ZoneOffset.P0600;
import static com.sinohb.maikel.constant.ZoneOffset.P0630;
import static com.sinohb.maikel.constant.ZoneOffset.P0700;
import static com.sinohb.maikel.constant.ZoneOffset.P0800;
import static com.sinohb.maikel.constant.ZoneOffset.P0830;
import static com.sinohb.maikel.constant.ZoneOffset.P0900;
import static com.sinohb.maikel.constant.ZoneOffset.P0930;
import static com.sinohb.maikel.constant.ZoneOffset.P1000;
import static com.sinohb.maikel.constant.ZoneOffset.P1100;
import static com.sinohb.maikel.constant.ZoneOffset.P1200;
import static com.sinohb.maikel.constant.ZoneOffset.P1300;
import static com.sinohb.maikel.constant.ZoneOffset.P1400;

/**
 * 时区偏移，N表示负，P表示正，数字表示时区，例如东八区使用P0800表示.
 */
@IntDef({N1200, N1100, N1000, N0900, N0800, N0700, N0600, N0500, N0430, N0400, N0330, N0300,
        N0200, N0100, N0000, P0100, P0200, P0300, P0330, P0400, P0430, P0500, P0530, P0600,
        P0630, P0700, P0800, P0830, P0900, P0930, P1000, P1100, P1200, P1300, P1400})
@Retention(RetentionPolicy.SOURCE)
public @interface ZoneOffset {
    /** GMT-12:00. */
    long N1200 = -43200000L;
    /** GMT-11:00. */
    long N1100 = -39600000L;
    /** GMT-10:00. */
    long N1000 = -36000000L;
    /** GMT-09:00. */
    long N0900 = -32400000L;
    /** GMT-08:00. */
    long N0800 = -28800000L;
    /** GMT-07:00. */
    long N0700 = -25200000L;
    /** GMT-08:00. */
    long N0600 = -21600000L;
    /** GMT-05:00. */
    long N0500 = -18000000L;
    /** GMT-04:30. */
    long N0430 = -16200000L;
    /** GMT-04:00. */
    long N0400 = -14400000L;
    /** GMT-03:30. */
    long N0330 = -12600000L;
    /** GMT-03:00. */
    long N0300 = -10800000L;
    /** GMT-02:00. */
    long N0200 = -7200000L;
    /** GMT-01:00. */
    long N0100 = -3600000L;
    /** GMT-00:30. */
    long N0000 = 0L;
    /** GMT+01:00. */
    long P0100 = 3600000L;
    /** GMT+02:00. */
    long P0200 = 7200000L;
    /** GMT+03:00. */
    long P0300 = 10800000L;
    /** GMT+03:30. */
    long P0330 = 12600000L;
    /** GMT+04:00. */
    long P0400 = 14400000L;
    /** GMT+04:30. */
    long P0430 = 16200000L;
    /** GMT+05:00. */
    long P0500 = 18000000L;
    /** GMT+05:30. */
    long P0530 = 19800000L;
    /** GMT+06:00. */
    long P0600 = 21600000L;
    /** GMT+06:30. */
    long P0630 = 23400000L;
    /** GMT+07:00. */
    long P0700 = 25200000L;
    /** GMT+08:00. */
    long P0800 = 28800000L;
    /** GMT+08:30. */
    long P0830 = 30600000L;
    /** GMT+09:00. */
    long P0900 = 32400000L;
    /** GMT+09:30. */
    long P0930 = 34200000L;
    /** GMT+10:00. */
    long P1000 = 36000000L;
    /** GMT+11:00. */
    long P1100 = 39600000L;
    /** GMT+12:00. */
    long P1200 = 43200000L;
    /** GMT+13:00. */
    long P1300 = 46800000L;
    /** GMT+14:00. */
    long P1400 = 50400000L;
}
