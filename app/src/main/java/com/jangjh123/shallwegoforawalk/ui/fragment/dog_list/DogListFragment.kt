package com.jangjh123.shallwegoforawalk.ui.fragment.dog_list

import android.content.Intent
import android.view.View
import androidx.fragment.app.viewModels
import com.jangjh123.shallwegoforawalk.R
import com.jangjh123.shallwegoforawalk.databinding.FragmentDogListBinding
import com.jangjh123.shallwegoforawalk.ui.activity.home.HomeActivity
import com.jangjh123.shallwegoforawalk.ui.activity.register.RegisterActivity
import com.jangjh123.shallwegoforawalk.ui.base.BaseFragment
import com.jangjh123.shallwegoforawalk.ui.component.ConfirmDialog
import com.jangjh123.shallwegoforawalk.ui.component.DogListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DogListFragment : BaseFragment<FragmentDogListBinding>(R.layout.fragment_dog_list) {
    private val viewModel: DogListViewModel by viewModels()
    private val dogListAdapter = DogListAdapter(
        onClickAddDog = {
            (requireActivity() as HomeActivity).addNewDog()
        },
        onClickRemoveDog = { id, position ->
            ConfirmDialog(
                title = getString(R.string.fragment_remove_dialog_title),
                body = getString(R.string.fragment_remove_dialog_body),
                cancelButtonText = getString(R.string.dialog_cancel),
                confirmButtonText = getString(R.string.dialog_confirm),
                onClickCancel = {

                },
                onClickConfirm = {
                    viewModel.removeDog(id, position,
                        onNoDog = {
                            startActivity(Intent(requireContext(), RegisterActivity::class.java))
                            requireActivity().finish()
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

    fun showHomeFragment(view: View) {
        (requireActivity() as HomeActivity).showMainScreen()
    }

    private fun showDogList() {
        viewModel.dogList.observe(viewLifecycleOwner) {
            dogListAdapter.submitList(it.toMutableList())
        }
    }
}