package com.example.littlelemon.models

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface SharedPrefsRepo {

    fun putString(key: String, value: String)
    fun getString(key: String, defaultValue: String = ""): String
    fun contains(key: String): Boolean
    fun deleteAll()

}

class SharedPrefs @Inject constructor(@ApplicationContext private val context: Context) : SharedPrefsRepo{
    private val prefs = context.getSharedPreferences("littlelemon",Context.MODE_PRIVATE)
    override fun contains(key: String): Boolean {
        return prefs.contains(key)
    }

    override fun deleteAll() {
        prefs.edit().clear().apply()
    }

    override fun getString(key: String, defaultValue: String): String {
        return prefs.getString(key,defaultValue).orEmpty()
    }

    override fun putString(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }
}