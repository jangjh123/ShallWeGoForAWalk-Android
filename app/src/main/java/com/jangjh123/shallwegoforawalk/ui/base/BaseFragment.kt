package com.jangjh123.shallwegoforawalk.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<VB : ViewDataBinding>(private val layoutId: Int) : Fragment() {
    lateinit var binding: VB

//    companion object {
//        private val progressFragment = ProgressFragment()
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        startProcess()
        initViewDataBinding()
    }

//    protected open fun showProgress() {
//        progressFragment.show(childFragmentManager, "progress")
//    }
//
//    protected open fun isProgressShowing(): Boolean {
//        return progressFragment.isVisible
//    }
//
//    protected open fun getMessages() {
//    }

    protected open fun initViewDataBinding() {

    }

    protected fun color(color: Int) =
        ContextCompat.getColor(requireContext(), color)

    protected fun drawable(drawable: Int) =
        AppCompatResources.getDrawable(requireContext(), drawable)

    protected open fun startProcess() {

    }

//    override fun onDestroy() {
//        super.onDestroy()
//    }
}