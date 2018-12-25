package com.superman.service;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
/**
 * 作者 Superman
 * 日期 2018/12/25 15:48.
 * 文件 Service2MessageActivity
 * 描述 通过Messenger和Service来实现Client - Service之间的双向通信。
 *
 */

public class Service2MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service2_message);
    }
}
