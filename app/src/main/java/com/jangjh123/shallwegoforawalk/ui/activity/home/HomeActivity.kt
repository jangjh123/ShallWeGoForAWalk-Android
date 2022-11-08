package com.jangjh123.shallwegoforawalk.ui.activity.home

import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import com.google.android.gms.location.LocationServices
import com.jangjh123.shallwegoforawalk.R
import com.jangjh123.shallwegoforawalk.databinding.ActivityHomeBinding
import com.jangjh123.shallwegoforawalk.ui.activity.register.RegisterActivity
import com.jangjh123.shallwegoforawalk.ui.base.BaseActivity
import com.jangjh123.shallwegoforawalk.ui.component.CautionDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {
    private val viewModel: HomeViewModel by viewModels()

    override fun initViewDataBinding() {
        super.initViewDataBinding()
        binding.activity = this@HomeActivity
    }

    override fun startProcess() {
        getLocation()
    }

    private fun getLocation() {
        LocationServices.getFusedLocationProviderClient(this@HomeActivity)
            .lastLocation.addOnSuccessListener {
                println("Current location ${it.latitude} ${it.longitude}")
            }.addOnFailureListener {
                println(it)
                /*
                check is there a history.
                if history does not exist, then set with seoul location.
                */
            }.addOnCompleteListener {

            }
    }


    private fun initViewPager() {
//        binding.viewPager.adapter = ViewPagerAdapter()
    }

    fun showAddDogScreen(view: View) {
    }

    fun showMainScreen() {
    }

    fun showCaution(view: View) {
        CautionDialog().show(supportFragmentManager, "caution")
    }

    fun addNewDog() {
        startActivity(
            Intent(this@HomeActivity, RegisterActivity::class.java)
        )
    }
}
