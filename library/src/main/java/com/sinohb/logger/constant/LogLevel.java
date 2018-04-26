package com.sinohb.logger.constant;


import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.sinohb.logger.constant.LogLevel.DEBUG;
import static com.sinohb.logger.constant.LogLevel.ERROR;
import static com.sinohb.logger.constant.LogLevel.INFO;
import static com.sinohb.logger.constant.LogLevel.JSON;
import static com.sinohb.logger.constant.LogLevel.PRINT;
import static com.sinohb.logger.constant.LogLevel.VERBOSE;
import static com.sinohb.logger.constant.LogLevel.WARN;

@StringDef({VERBOSE,DEBUG,INFO,WARN,ERROR,PRINT,JSON})
@Retention(RetentionPolicy.SOURCE)
public @interface LogLevel {
    String VERBOSE = "V";
    String DEBUG = "D";
    String INFO = "I";
    String WARN = "W";
    String ERROR = "E";
    String PRINT = "P";
    String JSON = "J";
}
