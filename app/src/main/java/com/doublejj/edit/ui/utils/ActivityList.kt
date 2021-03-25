package com.doublejj.edit.ui.utils

import android.app.Activity

class ActivityList {
    var actList: ArrayList<Activity> = ArrayList()

    fun actFinish() {
        for (index in 0 until actList.size) {
            this.actList[index].finish()
        }
    }

    fun add(element: Activity) {
        this.actList.add(element)
    }

    fun remove(element: Activity) {
        this.actList.remove(element)
    }
}