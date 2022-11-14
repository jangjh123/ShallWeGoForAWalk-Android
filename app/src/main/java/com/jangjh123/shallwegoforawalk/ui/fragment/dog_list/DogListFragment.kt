package com.jangjh123.shallwegoforawalk.ui.fragment.dog_list

import android.content.Intent
import androidx.fragment.app.viewModels
import com.jangjh123.shallwegoforawalk.R
import com.jangjh123.shallwegoforawalk.data.model.weather.Dog
import com.jangjh123.shallwegoforawalk.databinding.FragmentDogListBinding
import com.jangjh123.shallwegoforawalk.ui.activity.register.RegisterActivity
import com.jangjh123.shallwegoforawalk.ui.base.BaseFragment
import com.jangjh123.shallwegoforawalk.ui.component.ConfirmDialog
import com.jangjh123.shallwegoforawalk.ui.component.DogListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DogListFragment(private val dogs: List<Dog?>) :
    BaseFragment<FragmentDogListBinding>(R.layout.fragment_dog_list) {
    private val viewModel: DogListViewModel by viewModels()
    private val dogListAdapter = DogListAdapter(
        onClickAddDog = {
            startActivity(Intent(activity, RegisterActivity::class.java))
        },
        onClickRemoveDog = { id ->
            ConfirmDialog(
                title = getString(R.string.fragment_remove_dialog_title),
                body = getString(R.string.fragment_remove_dialog_body),
                cancelButtonText = getString(R.string.dialog_cancel),
                confirmButtonText = getString(R.string.dialog_confirm),
                onClickCancel = {},
                onClickConfirm = {
                    viewModel.removeDog(id,
                        onNoDog = {
                            startActivity(
                                Intent(
                                    requireContext(),
                                    RegisterActivity::class.java
                                ).apply {
                                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                })
                        },
                        onRefresh = {

                        })
                }
            ).show(childFragmentManager, "dialog_remove_dog")
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

    private fun showDogList() {
        (dogs as ArrayList).add(null)
        dogListAdapter.submitList(dogs)
    }
}