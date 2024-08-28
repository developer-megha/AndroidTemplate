package com.android.template.others

import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.View.MeasureSpec
import android.view.Window
import android.widget.Toast
import androidx.annotation.IntDef
import androidx.annotation.StringRes
import com.android.template.base.App.Companion.get

object Toaster {

    private var toast: Toast? = null

    fun shortToast(@StringRes text: Int) {
        shortToast(get().getString(text))
    }

    fun shortToast(text: String) {
        show(text, Toast.LENGTH_SHORT)
    }

    fun longToast(@StringRes text: Int) {
        longToast(get().getString(text))
    }

    fun longToast(text: String) {
        show(text, Toast.LENGTH_LONG)
    }


    private fun makeToast(text: String, length: Int): Toast {
        return Toast.makeText(get(), text, length)
    }

    private fun show(text: String, length: Int) {
        toast?.cancel()
        toast = makeToast(text, length)
        toast?.show()
    }

    fun somethingWentWrong() {
        shortToast("Something went wrong.")
    }

    @IntDef(Toast.LENGTH_LONG, Toast.LENGTH_SHORT)
    private annotation class ToastLength
}