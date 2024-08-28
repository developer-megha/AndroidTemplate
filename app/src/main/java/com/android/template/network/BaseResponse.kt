package com.android.template.network

data class BaseResponse(
    val status: String,
    val message: String,
    val `data`: Any
)