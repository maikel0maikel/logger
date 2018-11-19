package com.maikel.logger.constant;


import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.maikel.logger.constant.LogLevel.DEBUG;
import static com.maikel.logger.constant.LogLevel.ERROR;
import static com.maikel.logger.constant.LogLevel.INFO;
import static com.maikel.logger.constant.LogLevel.JSON;
import static com.maikel.logger.constant.LogLevel.PRINT;
import static com.maikel.logger.constant.LogLevel.VERBOSE;
import static com.maikel.logger.constant.LogLevel.WARN;
import static com.maikel.logger.constant.LogLevel.WTF;

@StringDef({VERBOSE,DEBUG,INFO,WARN,ERROR,PRINT,JSON,WTF})
@Retention(RetentionPolicy.SOURCE)
public @interface LogLevel {
    String VERBOSE = "V";
    String DEBUG = "D";
    String INFO = "I";
    String WARN = "W";
    String ERROR = "E";
    String PRINT = "P";
    String JSON = "J";
    String WTF = "WTF";
}
