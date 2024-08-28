package com.android.template.userAction.forgot.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.template.base.BaseModel
import com.android.template.network.BaseResponse
import com.android.template.network.NetworkResult
import com.android.template.network.Repository
import com.android.template.others.Cons
import com.android.template.others.MyUtils
import kotlinx.coroutines.launch
import javax.inject.Inject

class ForgotVM @Inject constructor(private val repository : Repository) : ViewModel() {

    private val _userForgot = MutableLiveData<NetworkResult<BaseResponse>>()
    val userForgot: LiveData<NetworkResult<BaseResponse>> get() = _userForgot

    /** Initialize Text Field. */
    var email = ObservableField<String>()

    /** Initialize Validate Fields. */
    val isValidEmail = ObservableField(BaseModel(true))

    /** Check Validation
     * If all fields valid then return true
     * */
    private fun checkValidation(): Boolean {
        var valid = false
        when {
            MyUtils.isEmptyString(email.get()) -> {
                isValidEmail.set(BaseModel(message = "Email cannot be left blank"))
            }
            !MyUtils.isValidEmail(email.get().toString().trim()) -> {
                isValidEmail.set(BaseModel(message = "Please enter valid email"))
            }
            else -> {
                valid = true
            }
        }
        return valid
    }

    /** Reset User Password*/
    fun forgotPassword() = viewModelScope.launch {
        if (checkValidation()) {
            val map: HashMap<String, Any> = HashMap()
            map[Cons.EMAIL] = email.get().toString().trim()
            _userForgot.value = NetworkResult.Loading()
            _userForgot.value = repository.safeApiCall { repository.apiWithoutToken.forgotPassword(map) }
        }
    }

}