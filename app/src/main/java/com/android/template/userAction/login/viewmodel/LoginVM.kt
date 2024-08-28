package com.android.template.userAction.login.viewmodel

import android.util.Patterns
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
import com.android.template.userAction.login.model.LoginBean
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginVM @Inject constructor(private val repository : Repository) :  ViewModel(){

    private val _userLogin = MutableLiveData<NetworkResult<LoginBean>>()
    val userLogin: LiveData<NetworkResult<LoginBean>> get() = _userLogin

    /** Initialize Text Field. */
    var email = ObservableField<String>()
    var password = ObservableField<String>()

    /** Initialize Validate Fields. */
    val isValidEmail = ObservableField(BaseModel(true))
    val isValidPassword = ObservableField(BaseModel(true))

    /** Check Validation
     * If all fields valid then return true
     * */
    private fun checkValidation(): Boolean {
        var valid = false
        when {
            MyUtils.isEmptyString(email.get()) -> {
                isValidEmail.set(BaseModel(message = "Email cannot be left blank"))
            }
            !Patterns.EMAIL_ADDRESS.matcher(email.get().toString().trim()).matches() -> {
                isValidEmail.set(BaseModel(message = "Please enter valid email"))
            }
            MyUtils.isEmptyString(password.get()) -> {
                isValidPassword.set(BaseModel(message = "Password cannot be left blank"))
            }
            else -> {
                valid = true
            }
        }
        return valid
    }

    /** Login User API call*/
    fun login() = viewModelScope.launch {
        if (checkValidation()) {
            val map: HashMap<String, Any> = HashMap()
            map[Cons.EMAIL] = email.get().toString().trim()
            map[Cons.PASSWORD] = password.get().toString().trim()
            _userLogin.value = NetworkResult.Loading()
            _userLogin.value = repository.safeApiCall { repository.apiWithoutToken.loginWithEmail(map) }
        }
    }

}