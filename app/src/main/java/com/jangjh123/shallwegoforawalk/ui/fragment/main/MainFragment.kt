package com.jangjh123.shallwegoforawalk.ui.fragment.main

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.jangjh123.shallwegoforawalk.R
import com.jangjh123.shallwegoforawalk.data.DogWeather
import com.jangjh123.shallwegoforawalk.databinding.FragmentMainBinding
import com.jangjh123.shallwegoforawalk.ui.base.BaseFragment
import com.jangjh123.shallwegoforawalk.ui.component.MainAdapter


class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {
    private val mainAdapter = MainAdapter()

    override fun startProcess() {
        BottomSheetBehavior.from(binding.bottomSheet).state = BottomSheetBehavior.STATE_EXPANDED
        initView()
    }

    private fun initView() {
        binding.recyclerviewMain.apply {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        }

        mainAdapter.submitList(listOf(DogWeather("치즈볼")))
    }
}