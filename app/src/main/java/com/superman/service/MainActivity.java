package com.superman.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 *  注：在四大基本组件中，需要注意的的是BroadcastReceiver不能作为Bound Service的Client，
 *  因为BroadcastReceiver的生命周期很短，当执行完onReceive(..)回调时，BroadcastReceiver生命周期完结。
 *  而Bound Service又与Client本身的生命周期相关，因此，Android中不允许BroadcastReceiver去bindService(..)，
 *  当有此类需求时，可以考虑通过startService(..)替代。
 */
public class MainActivity extends AppCompatActivity {
    ServiceConnection connection ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder binder) {
                MyService.MyBind myBind = (MyService.MyBind) binder;
                myBind.log();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
    }

    /**
     * 如果既调用了startService(),又调用了bindService(),销毁的时候既要调用stopService()也要调用unBindService(),没有先后顺序。
     * start启动，如果调用者退出没有调用stopService()，service不会停止，还会在后台运行。
     * bind启动，如果调用者退出，service会调用onUnbind()>onDestroy()方法退出。
     * @param view
     */
    public void start(View view) {
        switch(view.getId()){
        case R.id.start:
            startService(new Intent(this,MyService.class));
        break;
        case R.id.bind:
            bindService(new Intent(this,MyService.class),connection, BIND_AUTO_CREATE);
        break;
        case R.id.stop:
            stopService(new Intent(this,MyService.class));
                break;
            case R.id.unbind:
                unbindService(connection);
                break;
        default:
        break;}

    }
}
