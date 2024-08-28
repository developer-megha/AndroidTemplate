package com.android.template.base

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.template.others.Loader
import com.android.template.others.MyUtils
import com.android.template.others.NetworkChange
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("Registered")
@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    private var loader: Loader? = null
    private val neTWorkChange = NetworkChange()

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // restrict the font size to increase as per OS size
    override fun attachBaseContext(newBase: Context?) {
        val newOverride = Configuration(newBase?.resources?.configuration)
        newOverride.fontScale = 1.0f
        applyOverrideConfiguration(newOverride)
        super.attachBaseContext(newBase)
    }

    protected fun showLoader() {
        loader = null
        loader = Loader(this)
        if (loader != null && loader?.isShowing!!) {
            loader?.show()
        }
    }

    protected fun hideLoader() {
        if (loader != null && loader?.isShowing!!) {
            loader?.dismiss()
        }
        loader = null
    }

    /** Check @Internet Status */
    override fun onStart() {
        registerReceiver(neTWorkChange, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        super.onStart()
    }

    /** Check @Internet Status */
    override fun onStop() {
        unregisterReceiver(neTWorkChange);
        super.onStop()
    }
}