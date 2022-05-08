package com.jangjh123.shallwegoforawalk.ui.component

import com.jangjh123.shallwegoforawalk.R
import com.jangjh123.shallwegoforawalk.databinding.DialogConfirmBinding
import com.jangjh123.shallwegoforawalk.ui.base.BaseDialogFragment

class ConfirmDialog(
    private val title: String,
    private val body: String,
    private val cancelButtonText: String,
    private val confirmButtonText: String,
    private val onClickCancel: () -> Unit,
    private val onClickConfirm: () -> Unit
) : BaseDialogFragment<DialogConfirmBinding>(R.layout.dialog_confirm) {

    override fun startProcess() {
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
