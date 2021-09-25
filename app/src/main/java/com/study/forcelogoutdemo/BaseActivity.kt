package com.study.forcelogoutdemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


open class BaseActivity : AppCompatActivity() {

    private lateinit var forceReceiver: ForceReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCollector.addActivity(this)
    }

    /**
     * 在此处注册广播
     */
    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter()
        intentFilter.addAction(FORCE_LOGOUT_ACTION)
        forceReceiver = ForceReceiver()
        registerReceiver(forceReceiver, intentFilter)
    }

    /**
     * 在此处取消注册
     */
    override fun onPause() {
        super.onPause()
        if (::forceReceiver.isInitialized) {
            unregisterReceiver(forceReceiver)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }

    inner class ForceReceiver : BroadcastReceiver() {
        /**
         * com.study.forcelogoutdemo.MainActivity@e772fc3，因为最终会在MainActivity显示对话框
         */
        override fun onReceive(context: Context, intent: Intent) {
            Log.e(javaClass.simpleName, "$context")
            AlertDialog.Builder(context).apply {
                setMessage("检测到您的登录状态异常，请重新登录")
                setCancelable(false)
                setPositiveButton("退出登录") { dialog, _ ->
                    dialog.dismiss()
                    ActivityCollector.clearAllActivity()
                    Intent(context, LoginActivity::class.java).run {
                        context.startActivity(this)
                    }
                }
                show()
            }
        }
    }
}