package com.sample.wallpaperswitcher.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class SharedPreferenceUtil(name : String,context: Context?) {

    lateinit var name : String
    lateinit var context : Context
    private var preferences : SharedPreferences = context!!.getSharedPreferences(name, MODE_PRIVATE)

    companion object{
        val WALLPAPER_PREF_NAME = "wallpaper_prefs"

        val FOLDER_PREF = "folder_pref"
    }

    fun setPreference( prefName: String, value: Any) {

        val editor  = preferences.edit()
        when (value) {
            is Int -> editor.putInt(prefName, value)
            is String -> editor.putString(prefName, value)
            is Boolean -> editor.putBoolean(prefName, value)
        }
        editor?.apply()
    }

    fun getString(prefName: String) : String? {
        return preferences.getString(prefName,"")
    }

    fun getLong(prefName: String) : Long? {
        return preferences.getLong(prefName,0)
    }

    fun getInt(prefName: String) : Int? {
        return preferences.getInt(prefName,0)
    }
    fun getBoolean(prefName: String) : Boolean? {
        return preferences.getBoolean(prefName,false)
    }
}