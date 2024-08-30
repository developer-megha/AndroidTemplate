package com.android.template.userAction.profile.viewmodel

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
import com.android.template.userAction.profile.model.GetUserDataBean
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class ProfileVM @Inject constructor(private val repository : Repository) : ViewModel() {

    private val _getUserData = MutableLiveData<NetworkResult<GetUserDataBean>>()
    val getUserData: LiveData<NetworkResult<GetUserDataBean>> get() = _getUserData

    private val _userUpdate = MutableLiveData<NetworkResult<GetUserDataBean>>()
    val userUpdate: LiveData<NetworkResult<GetUserDataBean>> get() = _userUpdate

    /** Initialize Text Field. */
    var firstName = ObservableField<String>()
    val lastName = ObservableField<String>()
    var email = ObservableField<String>()
    var phone = ObservableField<String>()
    var location = ObservableField<String>()
    var about = ObservableField<String>()
    var fil: File? = null

    /** Initialize Validate Fields. */
    var isValidFName = ObservableField(BaseModel(true))
    var isValidLName = ObservableField(BaseModel(true))
    var isValidLocation = ObservableField(BaseModel(true))

    private fun checkValidation(): Boolean {
        var valid = false
        when {
            MyUtils.isEmptyString(firstName.get()) -> {
                isValidFName.set(BaseModel(message = "First name cannot be left blank"))
            }
            MyUtils.isEmptyString(lastName.get()) -> {
                isValidLName.set(BaseModel(message = "Last name cannot be left blank"))
            }
            MyUtils.isEmptyString(location.get()) -> {
                isValidLocation.set(BaseModel(message = "Location cannot be left blank"))
            }
            else -> {
                valid = true
            }
        }
        return valid
    }

    fun getUserDetails() = viewModelScope.launch {
        _getUserData.value = NetworkResult.Loading()
        _getUserData.value = repository.safeApiCall { repository.apiWithToken.getUserDetails() }
    }

    fun updateProfile() = viewModelScope.launch {
        if (checkValidation()) {
            val builder = MultipartBody.Builder()
            builder.setType(MultipartBody.FORM)
            builder.addFormDataPart(Cons.firstName, MyUtils.returnCamelCaseWord(firstName.get().toString().trim()))
            builder.addFormDataPart(Cons.lastName, MyUtils.returnCamelCaseWord(lastName.get().toString().trim()))
            builder.addFormDataPart(Cons.phone, location.get().toString().trim())
            builder.addFormDataPart(Cons.location, phone.get().toString().trim())
            builder.addFormDataPart(Cons.aboutUser, about.get().toString().trim())
            if(fil != null) {
                builder.addFormDataPart(Cons.image, fil!!.name,
                    fil!!.asRequestBody("multipart/form-data".toMediaTypeOrNull()))
            }
            val requestBody = builder.build()
            _userUpdate.value = NetworkResult.Loading()
            _userUpdate.value = repository.safeApiCall { repository.apiWithToken.updateProfile(requestBody) }
        }
    }
}