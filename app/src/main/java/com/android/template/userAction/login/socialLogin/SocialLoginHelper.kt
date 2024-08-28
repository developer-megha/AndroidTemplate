package com.android.template.userAction.login.socialLogin

import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.android.template.base.BaseFragment
import com.android.template.base.SharedPref
import com.android.template.others.Cons
import com.android.template.others.Toaster
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class SocialLoginHelper : BaseFragment(), FacebookCallback<LoginResult> {

    private val TAG =  "SocialSignInFragment"
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var callbackManager: CallbackManager

    abstract fun onSocialLoginSuccess(socialId: String, name: String, email: String)

    /**
     * This method is assigning social media elements
     * call in OnCreateView or OnCreate
     */
    fun onInitialization() {
        /*FACEBOOK*/
        callbackManager = CallbackManager.Factory.create()

        /*GOOGLE*/
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        mGoogleSignInClient.signOut()
    }

    /** Google
     * ------------------------------------------------------------------------------------------ */

    fun actionGoogleLogin() {
        val signInIntent = mGoogleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)
    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val task: Task<GoogleSignInAccount?> = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)
                updateUI(account)
            }
            catch (e: ApiException) {
                Log.e(TAG, "Google SignIn Exception" + e.statusCode + " -- " + e.localizedMessage)
                updateUI(null)
            }
        }
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        if (account != null) {
            account.id?.let {
                account.email?.let { it1 ->
                    account.displayName?.let { it2 ->
                        onSocialLoginSuccess(it, it2, it1)
                    }
                }
            }

        } else {
            Log.e(TAG, "UPDATE ACCOUNT NULL")
        }
    }

    /** Facebook
     * ------------------------------------------------------------------------------------------ */

    open fun actionLoginToFacebook() {
        SharedPref().save(Cons.loginType, Cons.FACEBOOK_FB)
        LoginManager.getInstance().logOut()
        LoginManager.getInstance().logIn(this, listOf("public_profile, email"))
        LoginManager.getInstance().registerCallback(callbackManager, this@SocialLoginHelper)
    }

    private fun onLoginSuccessGetUserDetails(vAccessToken: AccessToken) {
        val request = GraphRequest.newMeRequest(vAccessToken) { js, response ->
            Log.d(TAG, "onLoginSuccessGetUserDetails $js -- $response")
            Log.d("Email", "Email" + js?.optString("email"))
            onSocialLoginSuccess(js!!.optString("id"), js.optString("name"), js.optString("email"))
        }
        val bundle = Bundle()
        bundle.putString("fields", "id,name,email")
        request.parameters = bundle
        request.executeAsync()
    }

    override fun onSuccess(result: LoginResult) {
        val vAccessToken = result.accessToken
        SharedPref().save(Cons.loginType, Cons.FACEBOOK)
        onLoginSuccessGetUserDetails(vAccessToken)
    }

    override fun onCancel() {
        Toaster.shortToast("Login canceled")
        Log.e(TAG, "cancel")
    }

    override fun onError(error: FacebookException) {
        error.message?.let { Toaster.shortToast(it) }
        Log.d(TAG, error.toString())
    }

}