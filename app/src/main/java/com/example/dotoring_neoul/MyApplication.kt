package com.example.dotoring_neoul


import android.app.Application
import com.example.dotoring_neoul.ui.login.data.PreferenceUtil


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