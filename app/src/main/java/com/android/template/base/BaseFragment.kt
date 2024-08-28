package com.android.template.base

import android.app.Dialog
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.view.View
import androidx.fragment.app.Fragment
import com.android.template.others.Loader
import com.android.template.others.MyUtils
import com.android.template.others.NetworkChange
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseFragment : Fragment() {

    private lateinit var dialog: Dialog
    private lateinit var progressBar: View
    private lateinit var mainView: View
    private var loader: Loader? = null
    private val neTWorkChange = NetworkChange()

    protected fun showLoader() {
        try{
            loader = null
            loader = context?.let { Loader(it) }
            if (loader != null && !loader?.isShowing!!) {
                loader?.show()
            }
        }
        catch(e :Exception){
            e.printStackTrace()
        }
    }

    protected fun hideLoader() {
        if (loader != null && loader?.isShowing!!) {
            loader?.dismiss()
        }
        loader = null
    }

    protected fun showProgressBar() {
        MyUtils.viewGone(mainView)
        MyUtils.viewVisible(progressBar)

    }

    protected fun hideProgressBar() {
        MyUtils.viewGone(progressBar)
        MyUtils.viewVisible(mainView)
    }


    override fun onDestroy() {
        super.onDestroy()
        hideLoader()
    }

    /** Check @Internet Status */
    override fun onStart() {
        activity?.registerReceiver(neTWorkChange, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        super.onStart()
    }

    /** Check @Internet Status */
    override fun onStop() {
        activity?.unregisterReceiver(neTWorkChange)
        super.onStop()
    }

}