package com.android.template.userAction.register.model

import com.google.gson.annotations.SerializedName

data class SignUpBean(
    @SerializedName("status")
    val status: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val `data`: Data
) {
    data class Data(
        @SerializedName("token")
        val token: String,
        @SerializedName("first_name")
        val firstName: String,
        @SerializedName("last_name")
        val lastName: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("email")
        val email: String,
        @SerializedName("active_status")
        val activeStatus: Int,
        @SerializedName("register_type")
        val registerType: String,
        @SerializedName("device_type")
        val deviceType: String,
        @SerializedName("password")
        val password: String,
        @SerializedName("image_path")
        val imagePath: String,
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("updated_at")
        val updatedAt: String,
        @SerializedName("socialLogID")
        val socialLogID: Any,
        @SerializedName("user_role")
        val userRole: Int
    )
}