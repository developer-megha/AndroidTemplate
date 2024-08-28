package com.android.template.userAction

import android.os.Bundle
import com.android.template.R
import com.android.template.base.BaseActivity
import com.android.template.userAction.login.ui.LoginFragment
import com.android.template.others.Cons
import com.android.template.userAction.profile.ui.UpdateProfileFragment
import com.android.template.userAction.reset.ui.ResetPasswordFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        /** @LoginFragment */
        when(intent.getStringExtra(Cons.FRAG_NAME)) {
            Cons.RESET -> {
                supportFragmentManager.beginTransaction().add(R.id.container_user_activity,
                    ResetPasswordFragment()
                ).commit()
            }
            Cons.PROFILE -> {
                supportFragmentManager.beginTransaction().add(R.id.container_user_activity,
                    UpdateProfileFragment()
                ).commit()
            }
            else -> {
                /** @LoginFragment */
                supportFragmentManager.beginTransaction().add(R.id.container_user_activity,
                    LoginFragment()
                ).commit()
            }
        }
    }
}