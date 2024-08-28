package com.android.template.userAction.reset.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.template.R
import com.android.template.base.BaseFragment
import com.android.template.databinding.FragmentResetPasswordBinding
import com.android.template.dialogs.SuccessErrorDialog
import com.android.template.network.NetworkResult
import com.android.template.others.CustomWatcher
import com.android.template.others.MyUtils
import com.android.template.others.Toaster
import com.android.template.userAction.reset.viewmodel.ResetVM
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ResetPasswordFragment : BaseFragment() {

    @Inject
    lateinit var viewModel: ResetVM
    private val binding by lazy { FragmentResetPasswordBinding.inflate(layoutInflater) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        validateInputType()
        setClicks()
        bindObserver()
    }

    private fun bindObserver() {
        viewModel.userReset.observe(viewLifecycleOwner) {
            hideLoader()
            when (it) {
                is NetworkResult.Success -> {
                    it.data?.message?.let { msg ->
                        SuccessErrorDialog(requireContext(), msg, R.drawable.ic_success) {
                            activity?.onBackPressedDispatcher?.onBackPressed()
                        }.show()
                    }
                }
                is NetworkResult.Error -> {
                    it.message?.let { it1 ->
                        SuccessErrorDialog(requireContext(), it1, R.drawable.ic_error) {}.show()
                    }
                }
                is NetworkResult.Loading -> {
                    showLoader()
                }
            }
        }
    }

    /** for handling clicks */
    private fun setClicks() {

        /** @Send click listener */
        binding.buttonUpdate.setOnClickListener {
            viewModel.resetPassword()
        }

        /** Button @Back click listener */
        binding.buttonBack.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

    }

    /** TextWatcher that watch a input text field and can instantly update data */
    private fun validateInputType() {
        binding.etConfirmPassword.addTextChangedListener(
            CustomWatcher(binding.etConfirmPassword,
                binding.tilConfirmPassword, "Current password cannot be left blank", CustomWatcher.EditTextType.LoginPassword)
        )

        binding.etNewPassword.addTextChangedListener(
            CustomWatcher(binding.etNewPassword,
                binding.tilNewPassword, "New password cannot be left blank", CustomWatcher.EditTextType.LoginPassword)
        )

        binding.etConfirmPassword.addTextChangedListener(
            CustomWatcher(binding.etConfirmPassword,
                binding.tilConfirmPassword, "Repeat password cannot be left blank", CustomWatcher.EditTextType.LoginPassword)
        )
        binding.etNewPassword.addTextChangedListener(
            CustomWatcher(binding.etNewPassword,
                binding.tilNewPassword, "Password should be at least a minimum of 7 characters and contain a number, an uppercase letter, a lowercase letter and a special character.", CustomWatcher.EditTextType.Password)
        )
        binding.etConfirmPassword.addTextChangedListener(
            CustomWatcher(binding.etConfirmPassword,
                binding.tilConfirmPassword, "Passwords do not match", CustomWatcher.EditTextType.ConfirmPassword)
        )
    }
}