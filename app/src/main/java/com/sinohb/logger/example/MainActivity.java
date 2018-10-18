package com.sinohb.logger.example;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sinohb.logger.LogTools;
import com.sinohb.logger.example.test.TestService;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},10);
        }
        setContentView(R.layout.activity_main);
        startService(new Intent(this, TestService.class));
    }
    public void log(View view){

        switch (view.getId()){
            case R.id.v:
                LogTools.v(TAG,"这是测试日志v");
                break;
            case R.id.d:
                LogTools.d(TAG,"这是测试日志d");
                break;
            case R.id.i:
                LogTools.i(TAG,"这是测试日志i");
                break;
            case R.id.w:
                LogTools.w(TAG,"这是测试日志w");
                break;
            case R.id.e:
                LogTools.e(TAG,"这是测试日志e");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        LogTools.e(TAG,"这是线程里测试日志e");
                    }
                }).start();
                break;
            case R.id.wtf:
                LogTools.wtf(TAG,"这是测试日志wtf");
                break;
            case R.id.json:
                LogTools.j(TAG,  "{\"location\":{\"id\":\"C23NB62W20TF\",\"name\":\"西雅图\",\"country\":\"US\","
                        + "\"path\":\"西雅图,华盛顿州,美国\",\"timezone\":\"America/Los_Angeles\","
                        + "\"timezone_offset\":\"-08:00\"}}");
                break;
            case R.id.appCrash:
                int i = 10;
                int y = 0;
                int result = i/y;
                LogTools.d("result="+result);
                break;
        }

    }
}
