package com.sample.wallpaperswitcher

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

import com.sample.wallpaperswitcher.utils.WallPaperUtils

class WallpaperSwitchBroadcast : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when {
            intent?.action.equals(WallPaperUtils.CHANGE_WALLPAPER_ACTION) -> {
                Toast.makeText(context, "Wallpaper Changed", Toast.LENGTH_LONG).show()
                WallPaperUtils.changeWallpaper(context)
            }
            intent?.action.equals(WallPaperUtils.BOOT_COMPLETED_ACTION) -> {
                Log.e("WallpaperSwitch","Boot Complete")
                Toast.makeText(context, "Boot  Complete", Toast.LENGTH_LONG).show()
                WallPaperUtils.sendWallPaperChangeBroadcast(context)
            }
        }

    }
}