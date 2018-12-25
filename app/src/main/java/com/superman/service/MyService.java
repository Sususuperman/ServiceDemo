package com.superman.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * 作者 Superman
 * 日期 2018/12/25 15:48.
 * 文件 ServiceTest
 * 描述 Service实例测试。
 */

public class MyService extends Service {
    private static final String TAG ="MyService";
    public MyService(){};
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: ");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.i(TAG, "onStart: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind: ");
        return new MyBind();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "onUnbind: ");
        return super.onUnbind(intent);
    }

     class MyBind extends Binder{
        public void log(){
            Log.i(TAG, "log: 测试");
        }
    }
}
