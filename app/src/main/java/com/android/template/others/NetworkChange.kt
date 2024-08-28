package com.android.template.others

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.android.template.dialogs.NoInternetDialog

class NetworkChange : BroadcastReceiver() {

    private var listener: NetworkConnectivityListener? = null
    private var dialog: NoInternetDialog? = null

    fun setNetworkConnectivityListener(listener: NetworkConnectivityListener) {
        this.listener = listener
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (!Common.isConnectedToInternet(context)) {
            dialog = NoInternetDialog(
                context = context,
                onClick = { onReceive(context, intent) }
            )
            dialog?.show()
        }
        else {
            dialog?.dismiss()
            listener?.onNetworkConnected()
        }
    }

    interface NetworkConnectivityListener {
        fun onNetworkConnected()
    }
}