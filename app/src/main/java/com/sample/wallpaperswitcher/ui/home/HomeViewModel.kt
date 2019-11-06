package com.sample.wallpaperswitcher.ui.home

import androidx.lifecycle.ViewModel


class HomeViewModel : ViewModel() {

    lateinit var mEventHandler : HomeEventHandler

    fun startWallpaperAlarm(){

        mEventHandler.sendWallpaperAlarm()

    }
}
