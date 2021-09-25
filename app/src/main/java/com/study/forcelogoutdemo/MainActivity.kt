package com.study.forcelogoutdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * 发送动态注册的广播
     */
    fun sendDynamic(view: View) {
        with(Intent()) {
            action = FORCE_LOGOUT_ACTION
            sendBroadcast(this)
        }
    }

    /**
     * 发送静态注册的广播
     */
    fun sendStatic(view: View) {
        with(Intent()) {
            action = STATIC_FORCE_LOGOUT_ACTION
            /**
             * Android8.0之后要想使静态注册的广播接收者接收到广播，那么必须指明具体接收者。
             * 如果不指明具体接收者，那么则表示改广播是隐式广播，静态注册的广播接收者将无法接收该广播。
             * 不过目前还有部分系统的隐式广播仍可以被静态注册的广播接收者接收
             */
            setPackage(packageName)
            sendBroadcast(this)
        }
    }
}
