package com.android.template.dialogs

import android.content.Context
import android.os.Bundle
import com.android.template.base.BaseDialog
import com.android.template.databinding.DialogNoInternetBinding

class NoInternetDialog(context: Context, private val onClick: () -> Unit) : BaseDialog(context) {

    private val binding by lazy { DialogNoInternetBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setDimBlur(window)
        setCancelable(false)
        binding.btnRetry.setOnClickListener {
            onClick()
            dismiss()
        }
    }
}