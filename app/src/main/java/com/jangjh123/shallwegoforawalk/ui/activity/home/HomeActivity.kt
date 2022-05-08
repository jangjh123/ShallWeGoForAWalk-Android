package com.jangjh123.shallwegoforawalk.ui.activity.home

import android.view.View
import com.jangjh123.shallwegoforawalk.R
import com.jangjh123.shallwegoforawalk.databinding.ActivityHomeBinding
import com.jangjh123.shallwegoforawalk.ui.base.BaseActivity
import com.jangjh123.shallwegoforawalk.ui.component.CautionDialog

class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {
    override fun initViewDataBinding() {
        super.initViewDataBinding()
        binding.activity = this@HomeActivity
    }

    override fun startProcess() {

    }

    fun showAddDogScreen(view: View) {

    }

    fun showCaution(view: View) {
        CautionDialog().show(supportFragmentManager, "caution")
    }
}