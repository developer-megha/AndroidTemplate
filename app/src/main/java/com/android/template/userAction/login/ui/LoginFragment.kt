package com.android.template.userAction.login.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.template.R
import com.android.template.base.SharedPref
import com.android.template.userAction.login.socialLogin.SocialLoginHelper
import com.android.template.userAction.login.viewmodel.LoginVM
import com.android.template.dashboard.DashboardActivity
import com.android.template.databinding.FragmentLoginBinding
import com.android.template.dialogs.SuccessErrorDialog
import com.android.template.network.NetworkResult
import com.android.template.others.Cons
import com.android.template.others.CustomWatcher
import com.android.template.others.Toaster
import com.android.template.userAction.forgot.ui.ForgotPasswordFragment
import com.android.template.userAction.login.model.LoginBean
import com.android.template.userAction.register.ui.RegistrationFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : SocialLoginHelper() {

    @Inject
    lateinit var viewModel: LoginVM
    private val TAG =  "LoginFragment"
    private val binding by lazy { FragmentLoginBinding.inflate(layoutInflater) }
    private val sharedPref = SharedPref()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.userModel = viewModel
        onInitialization()
        validateInputType()
        setClicks()
        bindObserver()
    }

    /**
     * observe API Status
     * If response success than Save data to shared preference
     * moving to next activity
     */
    private fun bindObserver() {
        viewModel.userLogin.observe(viewLifecycleOwner) {
            hideLoader()
            when (it) {
                is NetworkResult.Success -> {
                    it.data?.data?.let { it1 -> onLoginSuccess(it1) }
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

    /** save user data to shared preference at the time of login */
    private fun onLoginSuccess(it: LoginBean.Data) {
        sharedPref.save(Cons.firstName, it.firstName)
        sharedPref.save(Cons.lastName, it.lastName)
        sharedPref.save(Cons.name, it.name)
        sharedPref.save(Cons.user_email, it.email)
        sharedPref.save(Cons.token, it.token)
        sharedPref.save(Cons.phone, it.phone)
        sharedPref.save(Cons.location, it.location)
        sharedPref.save(Cons.imagePath, it.imagePath)
        sharedPref.save(Cons.aboutUser, it.aboutUser)
        startActivity(Intent(requireContext(), DashboardActivity::class.java))
        activity?.finish()
    }

    /** for handling clicks */
    private fun setClicks() {
        binding.buttonLogin.setOnClickListener {
            viewModel.login()
        }

        binding.tvForgotPassword.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.add(R.id.container_user_activity, ForgotPasswordFragment())
                ?.addToBackStack(null)
                ?.commit()
        }

        binding.tvSignUpLogin.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.add(R.id.container_user_activity, RegistrationFragment())
                ?.addToBackStack(null)
                ?.commit()
        }

        binding.buttonGoogle.setOnClickListener {
            actionGoogleLogin()
        }

        binding.buttonFacebook.setOnClickListener {
            actionLoginToFacebook()
        }

    }

    /** TextWatcher that watch a input text field and can instantly update data */
    private fun validateInputType() {
        binding.etEmailLogin.addTextChangedListener(
            CustomWatcher(binding.etEmailLogin,
                binding.tilEmailLogin, "Enter email", CustomWatcher.EditTextType.FirstName)
        )
        binding.etPasswordLogin.addTextChangedListener(
            CustomWatcher(binding.etPasswordLogin,
                binding.tilPasswordLogin, "Enter password", CustomWatcher.EditTextType.Password)
        )
    }

    /**
     * This method is override by SocialLoginHelper class
     * We are getting user data after login is successful
     */
    override fun onSocialLoginSuccess(socialId: String, name: String, email: String) {
        Toaster.shortToast("Success")
    }

}