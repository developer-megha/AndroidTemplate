package com.android.template.others

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class Common {

    companion object {
        fun isConnectedToInternet(context: Context): Boolean {
            val connectivityManager = context.getSystemService(ConnectivityManager::class.java)
            return connectivityManager?.let {
                val network = it.activeNetwork ?: return false
                val capabilities = it.getNetworkCapabilities(network) ?: return false
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
            } ?: false
        }
    }
}




