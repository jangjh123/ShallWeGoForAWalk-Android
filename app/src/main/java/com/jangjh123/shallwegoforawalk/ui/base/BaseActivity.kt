package com.jangjh123.shallwegoforawalk.ui.base

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.jangjh123.shallwegoforawalk.ui.component.ProgressDialog


abstract class BaseActivity<VB : ViewDataBinding>(private val layoutId: Int) : AppCompatActivity() {
    lateinit var binding: VB
    private val progressDialog = ProgressDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewDataBinding()
        startProcess()
        setObserver()
    }

    protected open fun initViewDataBinding() {
        binding = DataBindingUtil.setContentView(this, layoutId)
    }

    protected open fun startProcess() {
    }

    protected fun playAnimation(view: View, anim: Int) {
        view.startAnimation(AnimationUtils.loadAnimation(this, anim))
    }

    protected fun playColorAnimation(view: View, startColor: Int, endColor: Int) {
        ValueAnimator.ofObject(ArgbEvaluator(), startColor, endColor).apply {
            duration = 250
            addUpdateListener { animator -> view.setBackgroundColor(animator.animatedValue as Int) }
        }.start()
    }

    protected fun color(color: Int) =
        ContextCompat.getColor(this, color)

    protected fun drawable(drawable: Int) =
        AppCompatResources.getDrawable(this, drawable)

    protected open fun setObserver() {
    }

    protected fun showProgress() {
        try {
            progressDialog.showNow(supportFragmentManager, "progress_dialog")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    protected fun dismissProgress() {
        try {
            progressDialog.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        dismissProgress()
        super.onDestroy()
    }
}