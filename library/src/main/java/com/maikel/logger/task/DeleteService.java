package com.maikel.logger.task;


import com.maikel.logger.LogTools;
import com.maikel.logger.Logger;
import com.maikel.logger.SparryLogger;
import com.maikel.logger.utils.LogUtils;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class DeleteService {

    private static final int CORE_SIZE = 0;
    private static final int THREAD_SIZE = 5;

    private static final ThreadFactory TF = new ThreadFactory() {
        private AtomicInteger atomicInteger = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "DeleteService" + atomicInteger.get());
        }
    };
    private static ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(CORE_SIZE, THREAD_SIZE, 0L, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(), TF);


    public static void onHandleIntent() {
        Map<String, Logger> mLoggers = SparryLogger.getLoggers();
        Set<Map.Entry<String, Logger>> loggerSet = mLoggers.entrySet();
        for (Map.Entry<String, Logger> entry : loggerSet) {
            Logger logger = entry.getValue();
            if (logger.isAutoDelete()) {
                EXECUTOR.execute(new DeleteTask(logger.getmLogDir(), logger.getStoreDays()));
            }
        }
    }

    static class DeleteTask implements Runnable {
        private String mDir;
        //private static final long T = 7 * 24 * 60 * 60 * 1000L;
        private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        private static final String TAG = "DeleteTask";
        private int storeDays;

        DeleteTask(String logDir, int storeDays) {
            this.mDir = logDir;
            this.storeDays = storeDays;
        }

        @Override
        public void run() {
            String logDirPath = LogUtils.genDirPath(mDir);
            File logDirFile = new File(logDirPath);
            if (logDirFile.exists()) {
                File[] files = logDirFile.listFiles();
                if (files.length > 0) {
                    long storeTime = storeDays * 24 * 60 * 60 * 1000L;
//                    FileUtils.sortByModifyDateDesc(files);
                    //String oldFileDate = files[0].getName().replace(".log", "");
                    long todayTime = System.currentTimeMillis();
                    //String lastFileDate = files[files.length - 1].getName().replace(".log", "");
                    //int deleteIndex = todayTime.equals(lastFileDate) ? files.length - 2 : files.length - 1;
                    //try {
                    //long oldTime = simpleDateFormat.parse(oldFileDate).getTime();
                    //long lastTime = simpleDateFormat.parse(lastFileDate).getTime();
                    //if (lastTime - oldTime >= T) {
                    for (int i = 0; i < files.length; i++) {
                        File f = files[i];
                        String oldFileDate = f.getName().replace(".log", "");
                        try {
                            long oldTime = simpleDateFormat.parse(oldFileDate).getTime();
                            if (todayTime - oldTime >= storeTime) {
                                boolean de = f.delete();
                                LogTools.e(TAG, f.getName() + " is delete " + de);
                            } else {
                                LogTools.e(TAG, f.getName() + " is delete false [todayTime=" + todayTime + ",oldTime=" + oldTime + ",storeTime=" + storeTime + ",storeDays=" + storeDays + "]");
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                            LogTools.e(TAG, f.getName() + " is delete false because " + e.getMessage());
                        }
                    }
                    //}
                    //} catch (ParseException e) {
                    //e.printStackTrace();
                    //}
                }

            }
        }
    }

}
