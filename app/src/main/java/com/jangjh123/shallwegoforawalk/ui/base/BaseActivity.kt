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
import com.jangjh123.shallwegoforawalk.R
import com.jangjh123.shallwegoforawalk.ui.component.ConfirmDialog
import kotlin.system.exitProcess


abstract class BaseActivity<VB : ViewDataBinding>(private val layoutId: Int) : AppCompatActivity() {
    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    protected open fun init() {
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

    override fun onBackPressed() {
        ConfirmDialog(
            title = getString(R.string.dialog_app_quit_title),
            body = getString(R.string.dialog_app_quit_body),
            cancelButtonText = getString(R.string.dialog_cancel),
            confirmButtonText = getString(R.string.dialog_quit),
            onClickCancel = {

            },
            onClickConfirm = {
                moveTaskToBack(true)
                finishAndRemoveTask()
                exitProcess(0)
            }).show(supportFragmentManager, "dialog_quit")
    }
}