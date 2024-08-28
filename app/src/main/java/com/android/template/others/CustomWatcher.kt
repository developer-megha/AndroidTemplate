package com.android.template.others

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class CustomWatcher(e: EditText?, fieldName: TextInputLayout?, msg: String?, val ed: EditTextType?) : TextWatcher {
    private var mEditText: EditText? = e
    private var textInputLayout: TextInputLayout? = fieldName
    private var message: String? = msg

    companion object {
        private var password: String? = null

    }

    enum class EditTextType {
        FirstName, LastName, Email, EmailValid, LoginPassword, Password, ConfirmPassword, Address1, Address2, State,City, Zip, PhoneMin
    }

    private val TAG = "CustomWatcher"

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    override fun afterTextChanged(s: Editable) {
        try {
            when (ed) {

                EditTextType.FirstName -> if (s.toString()
                        .isEmpty() && mEditText?.isFocused == true
                ) {
                    setErrorView(textInputLayout, message)
                } else {
                    setNonErrorView(textInputLayout)
                    //  textInputLayout?.isErrorEnabled = false
                }

                EditTextType.LastName -> if (s.toString()
                        .isEmpty() && mEditText?.isFocused == true
                ) {
                    setErrorView(textInputLayout, message)
                } else {
                    setNonErrorView(textInputLayout)
                    // textInputLayout?.isErrorEnabled = false
                }

                EditTextType.Email -> if (s.toString().isEmpty() && mEditText?.isFocused == true) {
                    setErrorView(textInputLayout, message)
                } else {
                    setNonErrorView(textInputLayout)
                }

                EditTextType.EmailValid -> if ((!MyUtils.isValidEmail(s.toString()))) {
                    setErrorView(textInputLayout, message)
                } else {
                    setNonErrorView(textInputLayout)
                }

                EditTextType.LoginPassword -> if (s.toString().trim().isEmpty() && mEditText?.isFocused == true) {
                    setErrorView(textInputLayout, message)
                } else {
                    setNonErrorView(textInputLayout)
                    //textInputLayout?.isErrorEnabled = false
                }

                EditTextType.Password -> if ((!MyUtils.isValidPassword(s.toString())) && mEditText?.isFocused == true) {
                    password = s.toString()
                    setErrorView(textInputLayout, message)
                } else {
                    password = s.toString()

                    setNonErrorView(textInputLayout)
                    //   textInputLayout?.isErrorEnabled = false
                }



                EditTextType.ConfirmPassword -> if (s.toString()
                        .isEmpty() && mEditText?.isFocused == true
                ) {
                    setErrorView(textInputLayout, message)
                } else if (s.toString() != password && mEditText?.isFocused == true
                ) {
                    setErrorView(textInputLayout, "Passwords do not match")

                } else {
                    setNonErrorView(textInputLayout)
                    //   textInputLayout?.isErrorEnabled = false
                }

                EditTextType.Address1 -> if (s.toString()
                        .isEmpty() && mEditText?.isFocused == true
                ) {
                    setErrorView(textInputLayout, message)
                } else {
                    setNonErrorView(textInputLayout)
                    //    textInputLayout?.isErrorEnabled = false
                }


                EditTextType.Address2 -> if (s.toString()
                        .isEmpty() && mEditText?.isFocused == true
                ) {
                    setErrorView(textInputLayout, message)
                } else {
                    setNonErrorView(textInputLayout)
                    //    textInputLayout?.isErrorEnabled = false
                }


                EditTextType.State -> if (s.toString()
                        .isEmpty() && mEditText?.isFocused == true
                ) {
                    setErrorView(textInputLayout, message)
                } else {
                    setNonErrorView(textInputLayout)
                    //  textInputLayout?.isErrorEnabled = false
                }
                EditTextType.City -> if (s.toString()
                        .isEmpty() && mEditText?.isFocused == true
                ) {
                    setErrorView(textInputLayout, message)
                } else {
                    setNonErrorView(textInputLayout)
                    //    textInputLayout?.isErrorEnabled = false
                }


                EditTextType.Zip -> if (s.toString()
                        .isEmpty() && mEditText?.isFocused == true
                ) {
                    setErrorView(textInputLayout, message)
                } else {
                    setNonErrorView(textInputLayout)
                    //    textInputLayout?.isErrorEnabled = false
                }

                EditTextType.PhoneMin -> if (s.toString().length < 4 && mEditText?.isFocused == true) {
                    setErrorView(textInputLayout, message)
                } else {
                    setNonErrorView(textInputLayout)
                    //  textInputLayout?.isErrorEnabled = false
                }

                else -> {
                    if (s.toString().isEmpty() && mEditText?.isFocused == true) {
                        setErrorView(textInputLayout, message)
                    } else {
                        Log.e(TAG, "password: ${s.toString()}")
                        password = s.toString()
                        setNonErrorView(textInputLayout)
                        //  textInputLayout?.isErrorEnabled = false
                    }
                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setErrorView(textInputLayout: TextInputLayout?, error: String?) {
        try {
            textInputLayout?.error = error
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setNonErrorView(textInputLayout: TextInputLayout?) {
        try {
            textInputLayout?.error = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}