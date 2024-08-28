package com.android.template.network

import javax.inject.Inject

// Main Repository (Authenticated) with injected dependency
class Repository @Inject constructor() : BaseRepo() {
    val apiWithoutToken: RetrofitApi = RetrofitClient.getRegisterRetrofit().create(RetrofitApi::class.java)
    val apiWithToken: RetrofitApi = RetrofitClient.getUserDetails().create(RetrofitApi::class.java)
}