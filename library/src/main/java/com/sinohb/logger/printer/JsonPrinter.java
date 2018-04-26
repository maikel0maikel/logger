package com.sinohb.logger.printer;

import android.content.Context;

import com.sinohb.logger.utils.LogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

public class JsonPrinter implements IPrinter {
    /** JSON的缩进量. */
    private static final int JSON_INDENT = 4;
    @Override
    public void printConsole(String level, String tag, String message, StackTraceElement element) {
        String json;
        try {
            if (message.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(message);
                json = jsonObject.toString(JSON_INDENT);
            } else if (message.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(message);
                json = jsonArray.toString(JSON_INDENT);
            } else {
                json = message;
            }
        } catch (JSONException e) {
            json = message;
        }
        LogUtils.printLog(level,tag,json,element);
    }

    @Override
    public void printFile(Context context,String level, String tag, String message, StackTraceElement element,
                          long offset, SimpleDateFormat timeFormat,
                          String logDir, String logPrefix, int logSegment) {

        synchronized (IPrinter.class){
            LogUtils.printFile(context,level,tag,message,element,offset,timeFormat,logDir,logPrefix,logSegment);
        }
    }
}
