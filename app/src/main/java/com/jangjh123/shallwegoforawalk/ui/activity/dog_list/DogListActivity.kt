package com.jangjh123.shallwegoforawalk.ui.activity.dog_list

import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.jangjh123.shallwegoforawalk.R
import com.jangjh123.shallwegoforawalk.data.model.DogsStateHandler
import com.jangjh123.shallwegoforawalk.data.model.weather.Dog
import com.jangjh123.shallwegoforawalk.databinding.ActivityDogListBinding
import com.jangjh123.shallwegoforawalk.ui.activity.home.HomeActivity
import com.jangjh123.shallwegoforawalk.ui.activity.register.RegisterActivity
import com.jangjh123.shallwegoforawalk.ui.activity.register.RegisterActivity2
import com.jangjh123.shallwegoforawalk.ui.base.BaseActivity
import com.jangjh123.shallwegoforawalk.ui.component.ConfirmDialog
import com.jangjh123.shallwegoforawalk.ui.component.DogListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DogListActivity :
    BaseActivity<ActivityDogListBinding>(R.layout.activity_dog_list) {
    private val viewModel: DogListViewModel by viewModels()
    private val originalSize by lazy {
        intent.getIntExtra("originalSize", 0)
    }
    private val dogListAdapter = DogListAdapter(
        onClickAddDog = {
            startActivity(Intent(this@DogListActivity, RegisterActivity2::class.java))
        },
        onClickRemoveDog = { id ->
            ConfirmDialog(
                title = getString(R.string.activity_remove_dialog_title),
                body = getString(R.string.activity_remove_dialog_body),
                cancelButtonText = getString(R.string.dialog_cancel),
                confirmButtonText = getString(R.string.dialog_confirm),
                onClickCancel = {},
                onClickConfirm = {
                    viewModel.removeDog(id)
                }
            ).show(supportFragmentManager, "dialog_remove_dog")
        }
    )

    override fun initViewDataBinding() {
        super.initViewDataBinding()
        binding.activity = this@DogListActivity
        binding.adapter = dogListAdapter
    }

    override fun setObserver() {
        lifecycleScope.launch {
            viewModel.dogsFlow.collect { state ->
                when (state) {
                    null -> {
                        showProgress()
                    }
                    is DogsStateHandler.Success -> {
                        val itemList = ArrayList<Dog?>().apply {
                            addAll(state.data)
                            add(null)
                        }
                        showDogList(itemList)
                        dismissProgress()
                    }
                    is DogsStateHandler.Failure -> {
                        dismissProgress()
                        if (state.errorMessage == "List is empty.") {
                            viewModel.setRegistered()
                            startActivity(
                                Intent(
                                    this@DogListActivity,
                                    RegisterActivity2::class.java
                                ).apply {
                                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                })
                        }
                    }
                }
            }
        }
    }

    private fun showDogList(dogs: List<Dog?>) {
        dogListAdapter.submitList(dogs)
    }

    fun goHome(view: View?) {
        val newSize = (viewModel.dogsFlow.value as DogsStateHandler.Success).data.size
        if (originalSize != newSize) {
            startActivity(Intent(this@DogListActivity, HomeActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            })
        } else {
            super.onBackPressed()
        }
    }

    override fun onBackPressed() { // 전체 강아지 수의 변화가 있으면 새로운 태스크 생성
        goHome(null)
    }
}