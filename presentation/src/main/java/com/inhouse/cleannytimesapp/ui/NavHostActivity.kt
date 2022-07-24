package com.inhouse.cleannytimesapp.ui

import android.os.Bundle
import com.inhouse.cleannytimesapp.R
import com.inhouse.cleannytimesapp.base.BaseActivity
import com.inhouse.cleannytimesapp.base.extension.navigateSafe
import com.inhouse.cleannytimesapp.databinding.ActivityNavHostBinding
import com.inhouse.cleannytimesapp.navigation.NavManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NavHostActivity : BaseActivity() {

    private lateinit var binding: ActivityNavHostBinding

    @Inject
    lateinit var navManager: NavManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavHostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavManager()
    }

    private fun initNavManager() {
        navManager.setOnNavEvent {
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
            val currentFragment = navHostFragment?.childFragmentManager?.fragments?.get(0)

            currentFragment?.navigateSafe(it)
        }
    }
}
