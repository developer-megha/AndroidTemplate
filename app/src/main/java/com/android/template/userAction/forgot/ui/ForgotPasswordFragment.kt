package com.android.template.userAction.forgot.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.template.R
import com.android.template.base.BaseFragment
import com.android.template.databinding.FragmentForgotPasswordBinding
import com.android.template.dialogs.SuccessErrorDialog
import com.android.template.network.NetworkResult
import com.android.template.others.CustomWatcher
import com.android.template.userAction.forgot.viewmodel.ForgotVM
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ForgotPasswordFragment : BaseFragment() {

    @Inject
    lateinit var viewModel: ForgotVM
    private val TAG = "ForgotPasswordFragment"
    private val binding by lazy { FragmentForgotPasswordBinding.inflate(layoutInflater) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.userModel = viewModel
        validateInputType()
        setClicks()
        bindObserver()
    }

    private fun bindObserver() {
        viewModel.userForgot.observe(viewLifecycleOwner) {
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
                else -> {
                    Log.d(TAG, "Unknown")
                }
            }
        }
    }

    /** for handling clicks */
    private fun setClicks() {

        /** @Send click listener */
        binding.buttonSend.setOnClickListener {
            viewModel.forgotPassword()
        }

        /** Button @Back click listener */
        binding.buttonBack.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

    }

    /** TextWatcher that watch a input text field and can instantly update data */
    private fun validateInputType() {
        binding.etEmail.addTextChangedListener(
            CustomWatcher(binding.etEmail,
                binding.tilEmail, "Email cannot be left blank", CustomWatcher.EditTextType.FirstName)
        )
    }
}