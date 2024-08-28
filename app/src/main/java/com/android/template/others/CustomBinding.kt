package com.android.template.others

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import com.android.template.base.BaseModel

/**
 * Custom Binding with Extension Functions
 * */

@BindingAdapter("setError")
fun TextInputLayout.setError(errorModel: BaseModel?) {
    errorModel?.let {
        if (!it.status)
            error = it.message
    }
}

