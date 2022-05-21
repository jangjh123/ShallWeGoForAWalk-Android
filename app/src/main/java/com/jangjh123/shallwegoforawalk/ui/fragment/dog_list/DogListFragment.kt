package com.jangjh123.shallwegoforawalk.ui.fragment.dog_list

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.jangjh123.shallwegoforawalk.R
import com.jangjh123.shallwegoforawalk.databinding.FragmentDogListBinding
import com.jangjh123.shallwegoforawalk.ui.activity.home.HomeActivity
import com.jangjh123.shallwegoforawalk.ui.base.BaseFragment
import com.jangjh123.shallwegoforawalk.ui.component.DogListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DogListFragment : BaseFragment<FragmentDogListBinding>(R.layout.fragment_dog_list) {
    private val viewModel: DogListViewModel by viewModels()
    private val dogListAdapter = DogListAdapter(
        onClickAddDog = {

        },
        onClickRemoveDog = {

        }
    )

    override fun startProcess() {
        super.startProcess()
        showDogList()
    }

    override fun initViewDataBinding() {
        binding.fragment = this@DogListFragment
        binding.adapter = dogListAdapter
    }

    fun showHomeFragment(view: View) {
        (requireActivity() as HomeActivity).showMainScreen()
    }

    private fun showDogList() {
        viewModel.dogList.observe(viewLifecycleOwner) {
            dogListAdapter.submitList(it)
            Log.d("TEST", it.toString())
        }
    }
}