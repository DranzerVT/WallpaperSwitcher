package com.sample.wallpaperswitcher.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.sample.wallpaperswitcher.R
import com.sample.wallpaperswitcher.databinding.HomeActivityBinding

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : HomeActivityBinding = DataBindingUtil.setContentView(this,
            R.layout.home_activity
        )
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HomeFragment.newInstance())
                .commitNow()
        }
    }

}
