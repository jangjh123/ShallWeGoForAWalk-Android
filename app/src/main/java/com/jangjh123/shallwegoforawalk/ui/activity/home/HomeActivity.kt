package com.jangjh123.shallwegoforawalk.ui.activity.home

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.jangjh123.shallwegoforawalk.R
import com.jangjh123.shallwegoforawalk.databinding.ActivityHomeBinding
import com.jangjh123.shallwegoforawalk.ui.base.BaseActivity
import com.jangjh123.shallwegoforawalk.ui.component.CautionDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {
    private lateinit var navController: NavController

    override fun initViewDataBinding() {
        super.initViewDataBinding()
        binding.activity = this@HomeActivity
    }

    override fun startProcess() {
        navController = findNavController(R.id.navigation)
    }

    fun showAddDogScreen(view: View) {
        navController.navigate(R.id.action_main_to_dog_list)
        binding.imageviewHomeAddDog.visibility = View.GONE
        binding.imageviewHomeCaution.visibility = View.GONE
    }

    fun showMainScreen() {
        navController.navigate(R.id.action_dog_list_to_main)
        binding.imageviewHomeAddDog.visibility = View.VISIBLE
        binding.imageviewHomeCaution.visibility = View.VISIBLE
    }

    fun showCaution(view: View) {
        CautionDialog().show(supportFragmentManager, "caution")
    }
}