package com.sample.wallpaperswitcher.ui.home

import android.content.ComponentName
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sample.wallpaperswitcher.R
import com.sample.wallpaperswitcher.WallpaperSwitchBroadcast
import com.sample.wallpaperswitcher.databinding.HomeFragmentBinding
import com.sample.wallpaperswitcher.utils.WallPaperUtils


class HomeFragment : Fragment(), HomeEventHandler {
    override fun sendWallpaperAlarm() {
        val receiver = ComponentName(context, WallpaperSwitchBroadcast::class.java)
        context?.packageManager?.setComponentEnabledSetting(
            receiver,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
        WallPaperUtils.sendWallPaperChangeBroadcast(context);
    }

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: HomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.home_fragment, container, false
        )
        return binding.getRoot()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.mEventHandler = this
        binding.viewmodel = viewModel

    }


}
