package com.jangjh123.shallwegoforawalk.ui.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.jangjh123.shallwegoforawalk.R
import com.jangjh123.shallwegoforawalk.databinding.DialogNoticeBinding

class NoticeDialog(
    private val title: String,
    private val body: String,
    private val buttonText: String,
    private val onClickButton: () -> Unit
) : DialogFragment() {
    private lateinit var binding: DialogNoticeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_notice, container, false)
        isCancelable = false

        initView()

        return binding.root
    }

    private fun initView() {
        with(binding) {
            tvDialogNoticeTitle.text = title
            tvDialogNoticeBody.text = body
            btnDialogNotice.apply {
                text = buttonText
                setOnClickListener {
                    onClickButton()
                }
            }
        }
    }
}