package com.android.template.base

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
open class

BaseModel(@SerializedName("status") val status: Boolean = false,
          @SerializedName("message") val message: String = "",

          /**
           * Because this class is also used as Error Model
           * So retryRequire param is used to identity that
           * error can be fix with retry or not.
           *
           * the para will not set from any api
           * */
          val retryRequire: Boolean = true
)
    : Parcelable {
    override fun toString() : String  = "${javaClass.simpleName} : STATUS: $status MESSAGE: $message"

}

