package com.maikel.logger.example;

import com.maikel.logger.SystemApplication;

public class ExampleApplication extends SystemApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        getLogger().setDebug(true);
        getLogger().setWriteToFile(true);
    }
}
