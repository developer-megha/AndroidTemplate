package com.android.template.userAction.register.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.template.R
import com.android.template.base.BaseFragment
import com.android.template.base.SharedPref
import com.android.template.databinding.FragmentRegistrationBinding
import com.android.template.dialogs.SuccessErrorDialog
import com.android.template.network.NetworkResult
import com.android.template.others.Cons
import com.android.template.others.CustomWatcher
import com.android.template.others.NameTextWatcher
import com.android.template.userAction.register.model.SignUpBean
import com.android.template.userAction.register.viewmodel.RegisterVM
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegistrationFragment : BaseFragment() {

    @Inject
    lateinit var viewModel: RegisterVM
    private val binding by lazy { FragmentRegistrationBinding.inflate(layoutInflater) }
    private val sharedPref = SharedPref()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.userModel = viewModel
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
        viewModel.userRegister.observe(viewLifecycleOwner) {
            hideLoader()
            when (it) {
                is NetworkResult.Success -> {
                    it.data?.data?.let { it1 -> onRegisterSuccess(it1) }
                }
                is NetworkResult.Error -> {
                    it.message?.let { msg ->
                        SuccessErrorDialog(requireContext(), msg, R.drawable.ic_error) {}.show()
                    }
                }
                is NetworkResult.Loading -> {
                    showLoader()
                }
            }
        }
    }

    /** save user data to shared preference at the time of SignUp */
    private fun onRegisterSuccess(it: SignUpBean.Data) {
        sharedPref.save(Cons.firstName, it.firstName)
        sharedPref.save(Cons.lastName, it.lastName)
        SharedPref.get().save(Cons.USER_ID, it.id.toString())
        sharedPref.save(Cons.name, it.name)
        sharedPref.save(Cons.user_email, it.email)
        sharedPref.save(Cons.token, it.token)
        sharedPref.save(Cons.imagePath, it.imagePath)
    }

    /** for handling clicks */
    private fun setClicks() {

        /** @Signup click listener */
        binding.buttonSignUp.setOnClickListener {
            viewModel.userSignUp()
        }

        /** @LogIn click listener */
        binding.tvSignUpLogin.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

    }

    /** TextWatcher that watch a input text field and can instantly update data */
    private fun validateInputType() {
        binding.etFirstName.addTextChangedListener(NameTextWatcher(binding.etFirstName))
        binding.etLastName.addTextChangedListener(NameTextWatcher(binding.etLastName))

        binding.etFirstName.addTextChangedListener(
            CustomWatcher(binding.etFirstName,
                binding.tilFirstName, "First name cannot be left blank", CustomWatcher.EditTextType.FirstName)
        )
        binding.etEmail.addTextChangedListener(
            CustomWatcher(binding.etEmail,
                binding.tilEmail, "Email cannot be left blank", CustomWatcher.EditTextType.Email)
        )
        binding.etEmail.addTextChangedListener(
            CustomWatcher(binding.etEmail,
                binding.tilEmail, "Please enter valid email", CustomWatcher.EditTextType.EmailValid)
        )
        binding.etPassword.addTextChangedListener(
            CustomWatcher(binding.etPassword,
                binding.tilPassword, "Password cannot be left blank", CustomWatcher.EditTextType.LoginPassword)
        )
        binding.etPassword.addTextChangedListener(
            CustomWatcher(binding.etPassword,
                binding.tilPassword, "Password should be at least a minimum of 7 characters and contain a number, an uppercase letter, a lowercase letter and a special character.", CustomWatcher.EditTextType.Password)
        )
        binding.etConfirmPassword.addTextChangedListener(
            CustomWatcher(binding.etConfirmPassword,
                binding.tilConfirmPassword, "Confirm password cannot be left blank", CustomWatcher.EditTextType.LoginPassword)
        )
        binding.etConfirmPassword.addTextChangedListener(
            CustomWatcher(binding.etConfirmPassword,
                binding.tilConfirmPassword, "Passwords do not match", CustomWatcher.EditTextType.ConfirmPassword)
        )
    }

}