package com.jangjh123.shallwegoforawalk.ui.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.jangjh123.shallwegoforawalk.R
import com.jangjh123.shallwegoforawalk.databinding.DialogConfirmBinding

class ConfirmDialog(
    private val title: String,
    private val body: String,
    private val cancelButtonText: String,
    private val confirmButtonText: String,
    private val onClickCancel: () -> Unit,
    private val onClickConfirm: () -> Unit
) : DialogFragment() {
    private lateinit var binding: DialogConfirmBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_confirm, container, false)
        isCancelable = false

        initView()

        return binding.root
    }

    private fun initView() {
        with(binding) {
            textviewConfirmTitle.text = title
            textviewConfirmBody.text = body
            buttonConfirmCancel.apply {
                text = cancelButtonText
                setOnClickListener {
                    onClickCancel
                }
            }
            buttonConfirmConfirm.apply {
                text = confirmButtonText
                setOnClickListener {
                    onClickConfirm()
                }
            }
        }
    }
}