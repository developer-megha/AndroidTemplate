package com.android.template.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.core.view.GravityCompat
import com.android.template.base.BaseActivity
import com.google.android.material.navigation.NavigationView
import com.android.template.base.SharedPref
import com.android.template.databinding.ActivityDashboardBinding
import com.android.template.dashboard.home.adapter.DrawerListAdapter
import com.android.template.others.Cons
import com.android.template.others.ImageUtils
import com.android.template.others.Toaster
import com.android.template.userAction.UserActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : BaseActivity() {

    private val binding by lazy { ActivityDashboardBinding.inflate(layoutInflater) }
    private val adapter = DrawerListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setDrawerData()
        setSupportActionBar(binding.drawer.toolbar)
        setupDrawerContent(binding.navView)
        setClicks()
    }

    private fun setClicks() {
        binding.drawer.toolbar.setNavigationOnClickListener {
            setNavUserDetails()
            toggleDrawer()
        }

        binding.drawer.icNotification.setOnClickListener {
            Toaster.shortToast("No Notifications")
        }

        binding.header.layoutEdit.setOnClickListener {
            val i = Intent(this, UserActivity::class.java)
            i.putExtra(Cons.FRAG_NAME, Cons.PROFILE)
            startActivity(i)
            toggleDrawer()
        }
    }

    private fun setDrawerData() {
        binding.expandableLV.setAdapter(adapter)
        binding.expandableLV.setOnGroupClickListener { _, _, i, _ ->
            when (i) {
                1 -> {
                    val intent = Intent(this, UserActivity::class.java)
                    intent.putExtra(Cons.FRAG_NAME, Cons.RESET)
                    startActivity(intent)
                }
                2 -> {
                    SharedPref.get().clearAll()
                    this.startActivity(Intent(this, UserActivity::class.java))
                    this.finishAffinity()
                }
            }
            toggleDrawer()
            false
        }
    }

    private fun setNavUserDetails() {
        binding.header.tvNameNavDrawer.text = SharedPref.get().getStringValue(Cons.name)
        binding.header.tvAboutNavDrawer.text = SharedPref.get().getStringValue(Cons.aboutUser)
        ImageUtils.loadImage(binding.header.profileImageNavDrawer, SharedPref.get().getStringValue(
            Cons.imagePath))
    }

    private fun toggleDrawer() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    private fun setupDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            binding.drawerLayout.closeDrawers()
            true
        }
    }

}