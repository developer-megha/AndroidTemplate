package com.android.template

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.template.base.SharedPref
import com.android.template.dashboard.DashboardActivity
import com.android.template.others.Cons
import com.android.template.others.MyUtils
import com.android.template.userAction.UserActivity

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/

        val token = SharedPref().getStringValue(Cons.token)
        // we used the postDelayed(Runnable, time) method to send a user to login page after delayed time.
        Handler(Looper.getMainLooper()).postDelayed({
            when {
                MyUtils.isEmptyString(token) -> {
                    startActivity(Intent(this, UserActivity::class.java))
                    this.finish()
                }
                else -> {
                    startActivity(Intent(this, DashboardActivity::class.java))
                    this.finish()
                }
            }
        }, 3000) // 3000 is the delayed time in milliseconds.
    }
}