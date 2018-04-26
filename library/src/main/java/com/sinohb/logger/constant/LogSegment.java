package com.sinohb.logger.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.sinohb.logger.constant.LogSegment.FOUR_HOURS;
import static com.sinohb.logger.constant.LogSegment.ONE_HOUR;
import static com.sinohb.logger.constant.LogSegment.SIX_HOURS;
import static com.sinohb.logger.constant.LogSegment.THREE_HOURS;
import static com.sinohb.logger.constant.LogSegment.TWELVE_HOURS;
import static com.sinohb.logger.constant.LogSegment.TWENTY_FOUR_HOURS;
import static com.sinohb.logger.constant.LogSegment.TWO_HOURS;

/**
 * 时间切片
 */
@IntDef({ONE_HOUR,TWO_HOURS,THREE_HOURS,FOUR_HOURS,SIX_HOURS,TWELVE_HOURS,TWENTY_FOUR_HOURS})
@Retention(RetentionPolicy.SOURCE)
public @interface LogSegment {
    int ONE_HOUR = 1;
    int TWO_HOURS = 2;
    int THREE_HOURS = 3;
    int FOUR_HOURS = 4;
    int SIX_HOURS = 6;
    int TWELVE_HOURS = 12;
    int TWENTY_FOUR_HOURS = 24;
}
