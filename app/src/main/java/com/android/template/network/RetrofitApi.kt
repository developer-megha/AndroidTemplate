package com.android.template.network

import com.google.gson.JsonObject
import com.android.template.userAction.login.model.LoginBean
import com.android.template.userAction.profile.model.ProfileBean
import com.android.template.userAction.register.model.SignUpBean
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface RetrofitApi {

    /** @LogIn API */
    @FormUrlEncoded
    @POST("login")
    suspend fun loginWithEmail(@FieldMap login: HashMap<String, Any>): Response<LoginBean>

    /** @SignUp API */
    @FormUrlEncoded
    @POST("register")
    suspend fun registerWithEmail(@FieldMap signup: HashMap<String, Any>): Response<SignUpBean>

    @FormUrlEncoded
    @POST("social_register")
    suspend fun socialRegister(@FieldMap map: HashMap<String, Any>): Call<JsonObject>

    /** @ForgotPassword API */
    @FormUrlEncoded
    @POST("create")
    suspend fun forgotPassword(@FieldMap forgot: HashMap<String, Any>): Response<BaseResponse>

    /** @ChangePassword API */ // now changed to reset Password also suspend
    @FormUrlEncoded
    @POST("changePassword")
    suspend fun changePassword(@FieldMap change: HashMap<String, Any>): Response<BaseResponse>

    /** @UpdateProfile API */
    @POST("updateProfile")
    suspend fun updateProfile(@Body requestBody: RequestBody): Response<ProfileBean>

    /** @UserDetails API */
    @GET("get_user_details")
    fun getUserDetails(): Call<JsonObject>

}
