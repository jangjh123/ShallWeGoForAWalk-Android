package com.jangjh123.shallwegoforawalk.ui.component

import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import com.jangjh123.shallwegoforawalk.R
import com.jangjh123.shallwegoforawalk.databinding.DialogProgressBinding
import com.jangjh123.shallwegoforawalk.ui.base.BaseDialogFragment

class ProgressDialog : BaseDialogFragment<DialogProgressBinding>(R.layout.dialog_progress) {

    override fun onResume() {
        super.onResume()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val animation: AnimationDrawable = binding.imageviewProgress.drawable as AnimationDrawable
        animation.start()
    }
}