package com.sample.wallpaperswitcher.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.sample.wallpaperswitcher.WallpaperSwitchBroadcast
import java.util.*

class WallPaperUtils {

    companion object {
        val CHANGE_WALLPAPER_ACTION = "com.sample.wallpaperswitcher.CHANGE_WALLPAPER"
        val BOOT_COMPLETED_ACTION = "android.intent.action.BOOT_COMPLETED"
        @JvmStatic
        fun sendWallPaperChangeBroadcast(context : Context?){
            val broadcastIntent = Intent(context, WallpaperSwitchBroadcast::class.java)
            broadcastIntent.setAction(CHANGE_WALLPAPER_ACTION)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, broadcastIntent, 0)
            val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager?

            // Set the alarm to start at 8:30 a.m.
            val calendar: Calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                //set(Calendar.HOUR_OF_DAY, 8)
                //set(Calendar.MINUTE, 30)
            }

            alarmManager?.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                1000 * 60 * 1,
                pendingIntent
            )
        }
    }
}