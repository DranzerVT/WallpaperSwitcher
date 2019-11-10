package com.sample.wallpaperswitcher.ui.home

import android.Manifest
import android.app.Activity
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
import pub.devrel.easypermissions.EasyPermissions
import android.content.Intent
import android.os.Environment
import android.widget.Toast
import com.sample.wallpaperswitcher.utils.SharedPreferenceUtil

import com.nononsenseapps.filepicker.FilePickerActivity
import com.nononsenseapps.filepicker.Utils

class HomeFragment : Fragment(), HomeEventHandler {
    val READ_REQUEST_CODE = 2
    override fun startFolderBrowse() {
       /* val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
        startActivityForResult(intent, READ_REQUEST_CODE)
*/
        // This always works
        val i = Intent(context, FilePickerActivity::class.java)
        // This works if you defined the intent filter
        // Intent i = new Intent(Intent.ACTION_GET_CONTENT);

        // Set these depending on your use case. These are the defaults.
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false)
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false)
        i.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE_AND_DIR)

        // Configure initial directory by specifying a String.
        // You could specify a String like "/storage/emulated/0/", but that can
        // dangerous. Always use Android's API calls to get paths to the SD-card or
        // internal memory.
        i.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().path)

        startActivityForResult(i, READ_REQUEST_CODE)
    }

    val RC_STORAGE= 1
    override fun sendWallpaperAlarm() {
        val receiver = ComponentName(context, WallpaperSwitchBroadcast::class.java)
        context?.packageManager?.setComponentEnabledSetting(
            receiver,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )

        val perms = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        if( EasyPermissions.hasPermissions(context!!, *perms)){
            WallPaperUtils.sendWallPaperChangeBroadcast(context)
        }else{
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(context as Activity, getString(R.string.read_storage_rationale),
                RC_STORAGE, *perms);
        }

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
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.mEventHandler = this
        binding.viewmodel = viewModel

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == READ_REQUEST_CODE){
            if( data?.dataString != null && data.dataString != ""){
                val filepath = Utils.getFileForUri(data.data)

                val preferenceUtil = SharedPreferenceUtil(SharedPreferenceUtil.WALLPAPER_PREF_NAME,context)
                preferenceUtil.setPreference(SharedPreferenceUtil.FOLDER_PREF,filepath.absolutePath)
            }
        }
    }
}
