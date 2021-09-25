package com.study.forcelogoutdemo

import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.view.WindowManager
import android.widget.Toast


/**
 * androidx.appcompat.app.AlertDialog在显示对话框时会报如下错误
 * You need to use a Theme.AppCompat theme (or descendant) with this activity.
 *  意思是：使用这个对话框必须是在使用Theme.AppCompat或其子类主题的activity才可以。
 *  所以在静态注册的广播中是无法使用这个包名下面的AlertDialog类
 *
 *  不过可以使用 这个android.app.AlertDialog这个类提供的AlertDialog的组件
 *
 */
class StaticForceReceiver : BroadcastReceiver() {

    /**
     *  android.app.ReceiverRestrictedContext@8cd96ed
     */
    override fun onReceive(context: Context, intent: Intent) {
        Log.e(javaClass.simpleName, context.toString())
        if (Build.VERSION.SDK_INT >= 23) {
            if (Settings.canDrawOverlays(context)) {
                //Android6.0以上
                showDialogTip(context)
            } else {
                //去设置页面打开该权限
                Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + context.packageName)
                ).apply {
                    addFlags(FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(this)
                }
                Toast.makeText(context, "请去设置中打开应用悬浮窗的权限", Toast.LENGTH_SHORT).show()
            }
        } else {
            //Android6.0以上
            showDialogTip(context)
        }


    }

    private fun showDialogTip(context: Context) {
        val dialog = AlertDialog.Builder(context).run {
            setMessage("静态注册的广播检测到您的登录状态异常，请重新登录")
            setCancelable(false)
            setPositiveButton("退出登录") { dialog, _ ->
                dialog.dismiss()
                ActivityCollector.clearAllActivity()
                Intent(context, LoginActivity::class.java).run {
                    addFlags(FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(this)
                }
            }
            create()
        }
        if (Build.VERSION.SDK_INT >= 26) {
            dialog.window!!.setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY)
        } else { //兼容低版本
            dialog.window!!.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT)
        }
        dialog.show()
    }
}
