package com.jangjh123.shallwegoforawalk.ui.activity.home

import android.content.Intent
import android.view.View
import androidx.activity.viewModels
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
        viewModel.getDogs()
    }

    override fun startProcess() {
        initViewPager()
    }

    private fun initViewPager() {

//        binding.viewPager.adapter = ViewPagerAdapter() // 강아지 정보와 날씨를 가져와 파라미터로 넘겨 줌
    }

    fun showAddDogScreen(view: View) {
//        navController.navigate(R.id.action_main_to_dog_list)
//        binding.imageviewHomeAddDog.visibility = View.GONE
//        binding.imageviewHomeCaution.visibility = View.GONE
    }

    fun showMainScreen() {
//        navController.navigate(R.id.action_dog_list_to_main)
//        binding.imageviewHomeAddDog.visibility = View.VISIBLE
//        binding.imageviewHomeCaution.visibility = View.VISIBLE
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