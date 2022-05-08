package com.jangjh123.shallwegoforawalk.ui.fragment.main

import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.jangjh123.shallwegoforawalk.R
import com.jangjh123.shallwegoforawalk.databinding.FragmentMainBinding
import com.jangjh123.shallwegoforawalk.ui.base.BaseFragment


class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {

    override fun startProcess() {
        BottomSheetBehavior.from(binding.bottomSheet).state = BottomSheetBehavior.STATE_EXPANDED
    }
}