package com.jangjh123.shallwegoforawalk.ui.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.jangjh123.shallwegoforawalk.R
import com.jangjh123.shallwegoforawalk.databinding.DialogCautionBinding
import com.jangjh123.shallwegoforawalk.databinding.DialogConfirmBinding

class CautionDialog: DialogFragment() {
    private lateinit var binding: DialogCautionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_caution, container, false)
        binding.dialog = this@CautionDialog
        isCancelable = false

        return binding.root
    }

    fun quit(view: View) {
        dismiss()
    }
}