package com.example.dotoring_neoul.ui.login.data

import android.content.Context
import android.content.SharedPreferences

/**
 * 받아온 token을 시스템 변수로 설정하거나
 * 시스템 변수인 token을 받아오는 기능을 구현
 */
class PreferenceUtil(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)

    fun getString(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }

    fun setString(key: String, str: String?) {
        prefs.edit().putString(key, str).apply()
    }
    fun getRefresh(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }

    fun setRefresh(key: String, str: String?) {
        prefs.edit().putString(key, str).apply()
    }

    fun getBoolean(key: String, defValue: Boolean): Boolean {
        return prefs.getBoolean(key, defValue)
    }

    fun setBoolean(key: String, bool: Boolean) {
        prefs.edit().putBoolean(key, bool).apply()
    }
}