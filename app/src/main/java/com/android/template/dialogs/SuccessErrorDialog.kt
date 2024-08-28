package com.android.template.dialogs

import android.content.Context
import android.os.Bundle
import com.android.template.base.BaseDialog
import com.android.template.databinding.DialogSuccessErrorBinding

class SuccessErrorDialog(context: Context, private val message: String, private val icon: Int, private val onYes: () -> Unit
) : BaseDialog(context) {

    private val binding by lazy { DialogSuccessErrorBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setDimBlur(window)
        setCancelable(false)
        binding.ivIcon.setImageResource(icon)
        binding.tvMsg.text = message
        binding.buttonOk.setOnClickListener {
            onYes()
            dismiss()
        }
    }
}