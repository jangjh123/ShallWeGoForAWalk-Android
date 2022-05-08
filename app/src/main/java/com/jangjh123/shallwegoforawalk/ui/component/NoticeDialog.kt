package com.jangjh123.shallwegoforawalk.ui.component

import com.jangjh123.shallwegoforawalk.R
import com.jangjh123.shallwegoforawalk.databinding.DialogNoticeBinding
import com.jangjh123.shallwegoforawalk.ui.base.BaseDialogFragment

class NoticeDialog(
    private val title: String,
    private val body: String,
    private val buttonText: String,
    private val onClickButton: () -> Unit
) : BaseDialogFragment<DialogNoticeBinding>(R.layout.dialog_notice) {

    override fun startProcess() {
        with(binding) {
            textviewNoticeTitle.text = title
            textviewNoticeBody.text = body
            buttonNoticeConfirm.apply {
                text = buttonText
                setOnClickListener {
                    onClickButton()
                }
            }
        }
    }
}