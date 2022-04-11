package com.jangjh123.shallwegoforawalk.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<VB : ViewDataBinding>(private val layoutId: Int) : AppCompatActivity() {
    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    protected open fun init() {
        initViewDataBinding()
        startProcess()
    }

    protected open fun initViewDataBinding() {
        binding = DataBindingUtil.setContentView(this, layoutId)
    }

    protected open fun startProcess() {

    }

//    open fun showProgress() {
//        if (!progressFragment.isVisible) {
//            progressFragment.show(supportFragmentManager, "progress")
//        }
//    }
//
//    open fun dismissProgress() {
//        try {
//            progressFragment.dismiss()
//        } catch (exception: Exception) {
//            exception.printStackTrace()
//        }
//    }

    protected open fun getMessages() {

    }

    override fun onDestroy() {
        super.onDestroy()
//        dismissProgress()
    }
}