package com.study.forcelogoutdemo

import android.app.Activity

object ActivityCollector {

    private val activityList = ArrayList<Activity>()

    /**
     * 添加Activity
     */
    fun addActivity(activity: Activity) {
        activityList.add(activity)
    }

    /**
     * 移除当前Activity
     */
    fun removeActivity(activity: Activity) {
        activityList.remove(activity)
    }

    /**
     * 清空所有的Activity
     */
    fun clearAllActivity() {
        for (activity in activityList) {
            if (!activity.isFinishing) {
                activity.finish()
            }
        }
        activityList.clear()
    }
}