package com.superman.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 作者 Superman
 * 日期 2018/12/25 15:48.
 * 文件 Service2MessageActivity
 * 描述 通过Messenger和Service来实现Client - Service之间的双向通信。
 * Messenger，在此可以理解成”信使“，通过Messenger方式返回Binder对象可以不用考虑Clinet - Service是否属于同一个进程的问题，
 * 并且，可以实现Client - Service之间的双向通信。极大方便了此类业务需求的实现。
 *局限：不支持严格意义上的多线程并发处理，实际上是以队列去处理
 *
 */

public class Service2MessageActivity extends AppCompatActivity {
    private Button btn;
    private EditText content;
    private TextView tv;
    private static final String TAG ="Service2MessageActivity";
    private Messenger mClientMessenger = new Messenger(new ClientHandler());
    private Messenger mServerMessenger ;
    private ServiceConnection messengerConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mServerMessenger = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mServerMessenger = null;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service2_message);
        btn = findViewById(R.id.btn);
        content = findViewById(R.id.edit);
        tv = findViewById(R.id.tv);
        bind();
    }

    private void bind() {
        bindService(new Intent(this,MyMessageService.class),messengerConnection, Service.BIND_AUTO_CREATE);
    }

    public void send(View view) {
        String str = content.getText().toString();
        Message msg = Message.obtain();
        msg.what = MyMessageService.MSG_FROM_SERVER_TO_CLIENT;
        msg.obj = str==""?"空消息":str;

        msg.replyTo = mClientMessenger;//指定回信人是客户端定义的

        try {
            mServerMessenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }

    class ClientHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            //msg从服务端发过来的
            switch(msg.what){
            case MyMessageService.MSG_FROM_SERVER_TO_CLIENT:
                Log.w(TAG, "Thread Name:  "+Thread.currentThread().getName() );
                String content = msg.obj.toString();
                Log.i(TAG, "Message from client :"+content);
                tv.setText(content);
            break;
            default:
            break;}
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(messengerConnection);
    }
}
