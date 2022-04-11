package com.jangjh123.shallwegoforawalk.ui.activity.register

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.jangjh123.shallwegoforawalk.R
import com.jangjh123.shallwegoforawalk.databinding.ActivityRegisterBinding
import com.jangjh123.shallwegoforawalk.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : BaseActivity<ActivityRegisterBinding>(R.layout.activity_register) {
    private val viewModel: RegisterViewModel by viewModels()

    override fun startProcess() {
        initView()
    }

    private fun initView() {
        with(binding) {
            etName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    if (etName.text.length > 20) {
                        etName.text.replace(20, 21, "")
                        Snackbar.make(root, "강아지 이름은 20자를 넘을 수 없어요.", Snackbar.LENGTH_SHORT).show()
                        hideKeyboard(etName)
                    }

                }
            })

            btnBoy.setOnClickListener {
                it.background = getDrawable(R.drawable.box_radius_color_main)
                btnBoy.setTextColor(ContextCompat.getColor(this@RegisterActivity, R.color.white))

                btnGirl.background = getDrawable(R.drawable.box_radius_color_white)
                btnGirl.setTextColor(ContextCompat.getColor(this@RegisterActivity, R.color.black))

                viewModel.setGender(true)
            }

            btnGirl.setOnClickListener {
                it.background = getDrawable(R.drawable.box_radius_color_main)
                btnGirl.setTextColor(ContextCompat.getColor(this@RegisterActivity, R.color.white))

                btnBoy.background = getDrawable(R.drawable.box_radius_color_white)
                btnBoy.setTextColor(ContextCompat.getColor(this@RegisterActivity, R.color.black))

                viewModel.setGender(false)
            }
        }
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}