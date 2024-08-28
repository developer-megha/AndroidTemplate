package com.android.template.dialogs

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import com.android.template.R
import com.android.template.base.BaseDialog
import com.android.template.databinding.DialogRequestPermissionBinding

class ReqPermissionDialog(context: Context, private val rationale: String) : BaseDialog(context) {

    private val binding by lazy { DialogRequestPermissionBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setDimBlur(window)
        binding.tvTitleReq.text = context.getString(R.string.title_permission)
        binding.tvDescriptionReq.text = rationale
        binding.buttonCancelReq.setOnClickListener {
            dismiss()
        }
        binding.buttonOkReq.setOnClickListener {
            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            val uri = Uri.fromParts("package", context.packageName, null)
            intent.data = uri
            context.startActivity(intent)
            dismiss()
        }
    }
}