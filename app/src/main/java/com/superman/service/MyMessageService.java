package com.superman.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * 作者 Superman
 * 日期 2018/12/25 17:42.
 * 文件 ServiceTest
 * 描述
 */

public class MyMessageService extends Service{
    private static final String TAG ="MyMessageService";

    public static final int MSG_FROM_CLIENT_TO_SERVER = 1;
    public static final int MSG_FROM_SERVER_TO_CLIENT = 2;
    private Messenger mClientMessenger;//客户端信使
    private Messenger mServerMessenger=new Messenger(new ServiceHandler());//服务端信使
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mServerMessenger.getBinder();//传过去，客户端获取。
    }

    class ServiceHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {//来自客户端发过来的msg
            Log.w(TAG, "Thread Name:  "+Thread.currentThread().getName() );
            switch(msg.what){
            case MSG_FROM_CLIENT_TO_SERVER:
                Log.i(TAG, "Message from client "+msg.obj );//获取来自客户端的信息并打印出来。

                //回复消息给客户端：
                Message reply = Message.obtain();//获取一个空闲消息对象
                reply.what = MSG_FROM_SERVER_TO_CLIENT;
                reply.obj = "收到您的消息，请回复";

                //回信操作 msg.replyto是Messenger对象 ，谁给他发的，就是对应的信使对象，这里是客户端的。
                try {
                    msg.replyTo.send(reply);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                mClientMessenger=msg.replyTo;

                break;
            default:
            break;}
        }
    }
}
