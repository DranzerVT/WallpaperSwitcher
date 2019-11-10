package com.sample.wallpaperswitcher.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.widget.Toast
import com.sample.wallpaperswitcher.WallpaperSwitchBroadcast
import java.io.File
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

        fun changeWallpaper(context: Context?){

            val preferenceUtil = SharedPreferenceUtil(SharedPreferenceUtil.WALLPAPER_PREF_NAME,context)
            var folderPath = preferenceUtil.getString(SharedPreferenceUtil.FOLDER_PREF)
            if(folderPath.equals("")){
                Toast.makeText(context, "Please specify folder path", Toast.LENGTH_LONG).show()
                return
            }
            val fileDir = File(folderPath)

            val files = fileDir.listFiles()

            if(files == null || files.isEmpty()){
                Toast.makeText(context, "No images in directory", Toast.LENGTH_LONG).show()
                return
            }

            val index = (files.indices).random()

            val filePath = files[index].path
            val bitmap = BitmapFactory.decodeFile(filePath)

            val wallpaperManager = WallpaperManager.getInstance(context)
            wallpaperManager.setBitmap(bitmap)
        }
    }
}