package com.example.dotoring


import android.app.Application
import com.example.dotoring.ui.login.data.PreferenceUtil


/**
 * prefs를 시스템 변수로 설정
 */
class MyApplication : Application() {
    companion object {
        lateinit var prefs: PreferenceUtil
    }

    override fun onCreate() {
        super.onCreate()
        prefs = PreferenceUtil(applicationContext)
    }
}