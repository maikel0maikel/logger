package com.sinohb.maikel.constant;


import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.sinohb.maikel.constant.LogLevel.DEBUG;
import static com.sinohb.maikel.constant.LogLevel.ERROR;
import static com.sinohb.maikel.constant.LogLevel.INFO;
import static com.sinohb.maikel.constant.LogLevel.JSON;
import static com.sinohb.maikel.constant.LogLevel.PRINT;
import static com.sinohb.maikel.constant.LogLevel.VERBOSE;
import static com.sinohb.maikel.constant.LogLevel.WARN;
import static com.sinohb.maikel.constant.LogLevel.WTF;

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
