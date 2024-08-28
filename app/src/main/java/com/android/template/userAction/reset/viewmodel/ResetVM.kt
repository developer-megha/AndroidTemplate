package com.android.template.userAction.reset.viewmodel

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

class ResetVM @Inject constructor(private val repository : Repository) : ViewModel() {

    private val _userReset = MutableLiveData<NetworkResult<BaseResponse>>()
    val userReset: LiveData<NetworkResult<BaseResponse>> get() = _userReset

    /** Initialize Text Field. */
    var currentPassword = ObservableField<String>()
    var newPassword = ObservableField<String>()
    var confirmPassword = ObservableField<String>()

    /** Initialize Validate Fields. */
    var isValidCurrentPassword = ObservableField(BaseModel(true))
    var isValidNewPassword = ObservableField(BaseModel(true))
    var isValidConfirmPassword = ObservableField(BaseModel(true))

    private fun checkValidation() : Boolean {
        var valid = false
        when {
            MyUtils.isEmptyString(currentPassword.get()) -> {
                isValidCurrentPassword.set(BaseModel(message = "Current password cannot be left blank"))
            }
            MyUtils.isEmptyString(newPassword.get()) -> {
                isValidNewPassword.set(BaseModel(message = "New password cannot be left blank"))
            }
            MyUtils.isEmptyString(confirmPassword.get()) -> {
                isValidConfirmPassword.set(BaseModel(message = "Repeat password cannot be left blank"))
            }
            !MyUtils.isValidPassword(newPassword.get().toString().trim()) -> {
                isValidNewPassword.set(BaseModel(message = "Password should be at least a minimum of 7 characters and contain a number, an uppercase letter, a lowercase letter and a special character."))
            }
            newPassword.get() != confirmPassword.get() -> {
                isValidConfirmPassword.set(BaseModel(message = "Passwords do not match"))
            }
            (newPassword.get().toString().trim().length > 18) -> {
                isValidNewPassword.set(BaseModel(message = "Password should be maximum of 18 characters"))
            }
            else -> {
                valid = true
            }
        }
        return valid
    }

    fun resetPassword() = viewModelScope.launch {
        if (checkValidation()) {
            val map: HashMap<String, Any> = HashMap()
            map[Cons.currentPassword] = currentPassword.get().toString().trim()
            map[Cons.newPassword] = newPassword.get().toString().trim()
            map[Cons.confirmPassword] = confirmPassword.get().toString().trim()
            _userReset.value = NetworkResult.Loading()
            _userReset.value = repository.safeApiCall { repository.apiWithToken.changePassword(map) }
        }
    }
}