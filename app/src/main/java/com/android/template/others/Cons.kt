package com.android.template.others

class Cons {
    companion object {

        const val TAG = "MyApplication"

        /* Base Urls */
        const val BASE_URL = "http://stfuture.kitlabs.us/"
        const val NETWORK_BASE_URL = "${BASE_URL}api/"

        const val id = "id"

        /* User Login */
        const val EMAIL = "email"
        const val PASSWORD = "password"

        /* User Register */
        const val F_NAME = "first_name"
        const val L_NAME = "last_name"
        const val REGISTER_TYPE = "register_type"
        const val DEVICE_TYPE = "device_type"
        const val ROLE_TYPE = "role_type"
        const val USER_ID = "user_id"

        /* Save User Data to SharedPref */
        const val firstName = "firstName"
        const val lastName = "lastName"
        const val name = "userName"
        const val user_email = "user_email"
        const val token = "token"
        const val phone = "phone"
        const val location = "location"
        const val aboutUser = "about_user"
        const val imagePath = "image_path"
        const val image = "image"

        /* Change Password */
        const val currentPassword = "oldPassword"
        const val newPassword = "password"
        const val confirmPassword = "c_password"

        /* Social Login */
        const val loginType = "logintype"
        const val FACEBOOK_FB = "Facebook"
        const val GMAIL = "gmail"
        const val GOOGLE_SIGN_IN = 1001
        const val FACEBOOK = "facebook"

        /* YouTube link matcher*/
        const val YOUTUBE_LINK_MATCHER = "https://www.youtube.com/watch?v=";
        const val VIMEO_LINK_MATCHER = "vimeo.com";
        const val SHORT_YOUTUBE_LINK_MATCHER = "https://youtu.be/";
        const val GOOGLE_DRIVE_VIDEO = "drive.google.com";

        /* Membership */
        const val ANDROID: String = "android"
        const val GOOGLE_IN_APP: String = "google-in_app"

        /* Navigation */
        const val FRAG_NAME = "frag_name"
        const val RESET = "reset"
        const val PROFILE = "profile"
    }
}