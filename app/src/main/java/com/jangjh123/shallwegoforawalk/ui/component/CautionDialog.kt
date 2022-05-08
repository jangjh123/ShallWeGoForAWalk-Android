package com.jangjh123.shallwegoforawalk.ui.component

import android.view.View
import com.jangjh123.shallwegoforawalk.R
import com.jangjh123.shallwegoforawalk.databinding.DialogCautionBinding
import com.jangjh123.shallwegoforawalk.ui.base.BaseDialogFragment

class CautionDialog: BaseDialogFragment<DialogCautionBinding>(R.layout.dialog_caution) {
    override fun startProcess() {
        binding.dialog = this@CautionDialog
    }

    fun quit(view: View) {
        dismiss()
    }
}