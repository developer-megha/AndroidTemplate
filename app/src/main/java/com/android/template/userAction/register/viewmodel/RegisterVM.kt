package com.android.template.userAction.register.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.template.base.BaseModel
import com.android.template.network.NetworkResult
import com.android.template.network.Repository
import com.android.template.others.Cons
import com.android.template.others.MyUtils
import com.android.template.userAction.register.model.SignUpBean
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegisterVM @Inject constructor(private val repository : Repository) : ViewModel() {

    private val _userRegister = MutableLiveData<NetworkResult<SignUpBean>>()
    val userRegister: LiveData<NetworkResult<SignUpBean>> get() = _userRegister

    /** Initialize Text Field. */
    var fName = ObservableField<String>()
    var lName = ObservableField<String>()
    var email = ObservableField<String>()
    var password = ObservableField<String>()
    var confirmPassword = ObservableField<String>()

    /** Initialize Validate Fields. */
    val isValidFName = ObservableField(BaseModel(true))
    val isValidLName = ObservableField(BaseModel(true))
    val isValidEmail = ObservableField(BaseModel(true))
    val isValidPassword = ObservableField(BaseModel(true))
    val isValidCPassword = ObservableField(BaseModel(true))

    /** Check Validation
     * If all fields valid then return true
     * */
    private fun checkValidation(): Boolean {
        var valid = false
        when {
            MyUtils.isEmptyString(fName.get()) -> {
                isValidFName.set(BaseModel(message = "First name cannot be left blank"))
            }
            MyUtils.isEmptyString(email.get()) -> {
                isValidEmail.set(BaseModel(message ="Email cannot be left blank"))
            }
            !MyUtils.isValidEmail(email.get().toString().trim()) -> {
                isValidEmail.set(BaseModel(message ="Please enter valid email"))
            }
            MyUtils.isEmptyString(password.get()) -> {
                isValidPassword.set(BaseModel(message ="Password cannot be left blank"))
            }
            !MyUtils.isValidPassword(password.get().toString().trim()) -> {
                isValidPassword.set(BaseModel(message ="Password must be more than 7 characters long and contain at least 1 Uppercase, 1 Lowercase, 1 Numeric and 1 special character"))
            }
            MyUtils.isEmptyString(password.get()) -> {
                isValidPassword.set(BaseModel(message ="Confirm password cannot be left blank"))
            }
            password.get() != confirmPassword.get() -> {
                isValidCPassword.set(BaseModel(message ="Passwords do not match"))
            }
            else -> {
                valid = true
            }
        }
        return valid
    }

    /** Register User */
    fun userSignUp() = viewModelScope.launch {
        if (checkValidation()) {
            val map: HashMap<String, Any> = HashMap()
            map[Cons.F_NAME] = MyUtils.returnCamelCaseWord(fName.get().toString().trim())
            map[Cons.L_NAME] = MyUtils.returnCamelCaseWord(lName.get().toString().trim())
            map[Cons.EMAIL] = email.get().toString().trim()
            map[Cons.PASSWORD] = password.get().toString().trim()
            map[Cons.REGISTER_TYPE] = "Native"
            map[Cons.DEVICE_TYPE] = "android"
            map[Cons.ROLE_TYPE] = "family"
            _userRegister.value = NetworkResult.Loading()
            _userRegister.value = repository.safeApiCall { repository.apiWithoutToken.registerWithEmail(map) }
        }
    }
}