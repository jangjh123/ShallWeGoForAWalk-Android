package com.jangjh123.shallwegoforawalk.ui.fragment.dog_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jangjh123.shallwegoforawalk.R
import com.jangjh123.shallwegoforawalk.databinding.FragmentDogListBinding
import com.jangjh123.shallwegoforawalk.ui.activity.home.HomeActivity
import com.jangjh123.shallwegoforawalk.ui.base.BaseFragment

class DogListFragment : BaseFragment<FragmentDogListBinding>(R.layout.fragment_dog_list) {

    override fun onResume() {
        super.onResume()
        binding.fragment = this@DogListFragment
    }

    fun showHomeFragment(view: View) {
        (requireContext() as HomeActivity).showMainScreen()
    }
}