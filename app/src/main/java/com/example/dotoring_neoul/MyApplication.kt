package com.example.dotoring_neoul


import android.app.Application
import com.example.dotoring_neoul.ui.login.data.PreferenceUtil


class MyApplication : Application() {
    companion object {
        lateinit var prefs: PreferenceUtil
    }

    override fun onCreate() {
        super.onCreate()
        prefs = PreferenceUtil(applicationContext)
    }
}