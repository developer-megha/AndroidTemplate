package com.android.template.base

import android.content.SharedPreferences

class SharedPref {
    private var sharedPreferences: SharedPreferences
    private var editor: SharedPreferences.Editor
    private var PRIVATE_MODE = 0
    private var instance: SharedPref?= null

    fun get(): SharedPref? {
        if (instance == null) {
            instance = SharedPref()
        }
        return instance
    }

    private fun getEditor(): SharedPreferences.Editor {
        return sharedPreferences.edit()
    }

    fun save(key: String?, value: Any?) {
        val editor: SharedPreferences.Editor = getEditor()
        if (value is Boolean) {
            editor.putBoolean(key, (value as Boolean?)!!)
        } else if (value is Int) {
            editor.putInt(key, (value as Int?)!!)
        } else if (value is Float) {
            editor.putFloat(key, (value as Float?)!!)
        } else if (value is Long) {
            editor.putLong(key, (value as Long?)!!)
        } else if (value is String) {
            editor.putString(key, value as String?)
        } else if (value is Enum<*>) {
            editor.putString(key, value.toString())
        } else if (value != null) {
            throw RuntimeException("Attempting to save non-supported preference")
        }
        editor.commit()
    }

    fun getStringValue(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun clearAll() {
        editor.clear()
        editor.commit()
    }

    fun logOut() {
        val editor = sharedPreferences.edit()
        editor?.clear()
        editor?.apply()
    }

    companion object {
        private val PREF_NAME = App.get().packageName + ".prefs"
        private var instance: SharedPref? = null
        fun get(): SharedPref {
            if (instance == null) {
                instance = SharedPref()
            }
            return instance as SharedPref
        }
    }

    init {
        sharedPreferences = App.get().getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = sharedPreferences.edit()
        editor.apply()
    }
}