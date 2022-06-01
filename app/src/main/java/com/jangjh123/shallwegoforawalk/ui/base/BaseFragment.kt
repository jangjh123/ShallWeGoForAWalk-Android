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
import com.jangjh123.shallwegoforawalk.ui.component.ProgressDialog

abstract class BaseFragment<VB : ViewDataBinding>(private val layoutId: Int) : Fragment() {
    lateinit var binding: VB

    companion object {
        private val progressDialog = ProgressDialog()
    }

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

    protected open fun showProgress() {
        progressDialog.show(childFragmentManager, "progress_dialog")
    }

    protected open fun hideProgress() {
        progressDialog.dismiss()
    }

    protected open fun isProgressShowing(): Boolean {
        return progressDialog.isVisible
    }

    protected open fun initViewDataBinding() {

    }

    protected fun color(color: Int) =
        ContextCompat.getColor(requireContext(), color)

    protected fun drawable(drawable: Int) =
        AppCompatResources.getDrawable(requireContext(), drawable)

    protected open fun startProcess() {

    }

    override fun onDestroy() {
        super.onDestroy()
        if (isProgressShowing()) {
            progressDialog.dismiss()
        }
    }
}