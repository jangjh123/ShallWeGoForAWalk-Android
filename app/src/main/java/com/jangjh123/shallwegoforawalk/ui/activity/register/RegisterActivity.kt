package com.jangjh123.shallwegoforawalk.ui.activity.register

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.transition.TransitionManager
import android.transition.TransitionManager.beginDelayedTransition
import android.util.TypedValue
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.jangjh123.shallwegoforawalk.R
import com.jangjh123.shallwegoforawalk.data.model.FurType
import com.jangjh123.shallwegoforawalk.databinding.ActivityRegisterBinding
import com.jangjh123.shallwegoforawalk.ui.activity.home.HomeActivity
import com.jangjh123.shallwegoforawalk.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterActivity : BaseActivity<ActivityRegisterBinding>(R.layout.activity_register) {
    private val viewModel: RegisterViewModel by viewModels()
    private var dropDownState = 0
    private var isFilledAll = false
    private val collapsedHeight by lazy {
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40F, resources.displayMetrics)
            .toInt()
    }

    private val animDimIn by lazy {
        ValueAnimator.ofObject(
            ArgbEvaluator(),
            color(R.color.transparent),
            color(R.color.dim_behind)
        ).apply {
            duration = 300
            addUpdateListener {
                binding.screenRegisterDimFilter.setBackgroundColor(it.animatedValue as Int)
            }
        }
    }

    private val animDimOut by lazy {
        ValueAnimator.ofObject(
            ArgbEvaluator(),
            color(R.color.dim_behind),
            color(R.color.transparent)
        ).apply {
            duration = 300
            addUpdateListener {
                binding.screenRegisterDimFilter.setBackgroundColor(it.animatedValue as Int)
            }
        }
    }

    private val furBehavior by lazy {
        BottomSheetBehavior.from(binding.bottomSheetFur)
    }

    override fun initViewDataBinding() {
        super.initViewDataBinding()
        binding.activity = this@RegisterActivity
        binding.viewModel = viewModel
        binding.boy = binding.buttonRegisterBoy
        binding.girl = binding.buttonRegisterGirl
    }

    override fun startProcess() {
        initView()
    }

    private fun initView() {
        with(binding) {
            edittextRegisterName.apply {
                addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }

                    override fun afterTextChanged(p0: Editable?) {
                        if (edittextRegisterName.text.length > 20) {
                            edittextRegisterName.text.replace(20, 21, "")
                            Snackbar.make(root, "강아지 이름은 20자를 넘을 수 없어요.", Snackbar.LENGTH_SHORT)
                                .show()
                            hideKeyboard(edittextRegisterName)
                        }

                    }
                })

                setOnKeyListener { _, p1, _ ->
                    if (p1 == KeyEvent.KEYCODE_ENTER) {
                        if (edittextRegisterName.text.isNotEmpty()) {
                            viewModel!!.setName(edittextRegisterName.text.toString())
                        } else {
                            if (viewModel!!.dogName.value != null) {
                                edittextRegisterName.setText(viewModel!!.dogName.value)
                            }
                        }
                        hideKeyboard(edittextRegisterName)
                        edittextRegisterName.clearFocus()
                    }
                    return@setOnKeyListener false
                }

                setOnFocusChangeListener { _, _ ->
                    if (edittextRegisterName.text.isNotEmpty()) {
                        viewModel!!.setName(edittextRegisterName.text.toString())
                    } else {
                        if (viewModel!!.dogName.value != null) {
                            edittextRegisterName.setText(viewModel!!.dogName.value)
                        }
                    }
                }
            }

            edittextRegisterAge.setOnFocusChangeListener { _, b ->
                if (b) {
                    edittextRegisterAge.setText("")
                }
            }

            edittextRegisterAge.setOnKeyListener { _, p1, _ ->
                if (p1 == KeyEvent.KEYCODE_NUMPAD_ENTER || p1 == KeyEvent.KEYCODE_ENTER) {
                    if (edittextRegisterAge.text.isNotEmpty()) {
                        viewModel!!.setAge(edittextRegisterAge.text.toString().toInt())
                        edittextRegisterAge.setText("${edittextRegisterAge.text} 살")
                    } else {
                        if (viewModel!!.dogAge.value != null) {
                            edittextRegisterAge.setText("${viewModel!!.dogAge.value.toString()} 살")
                        }
                    }
                    hideKeyboard(edittextRegisterAge)
                    edittextRegisterAge.clearFocus()
                }
                return@setOnKeyListener false
            }

            screenRegisterPreventTouch.setOnClickListener { }

            furBehavior.addBottomSheetCallback(object :
                BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                        animDimIn.start()
                        setLayoutTouchable(false)
                    } else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                        animDimOut.start()
                        setLayoutTouchable(true)
                    }

                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {

                }
            })
        }
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun setLayoutTouchable(isTouchable: Boolean) {
        when (isTouchable) {
            true -> {
                binding.screenRegisterPreventTouch.visibility = View.GONE
            }
            false -> {
                binding.screenRegisterPreventTouch.visibility = View.VISIBLE
            }
        }
    }

    fun setGender(view: View, view2: View, gender: Boolean) {
        view.background = drawable(R.drawable.box_radius15_color_main)

        (view as TextView).setTextColor(
            color(R.color.white)
        )

        view2.background = drawable(R.drawable.box_radius15_color_white)

        (view2 as TextView).setTextColor(
            color(R.color.gray_depth1)
        )

        viewModel.setGender(gender)
    }

    fun expandFurHelp(view: View) {
        with(binding) {
            TransitionManager().run {
                beginDelayedTransition(layoutRegisterFurHelp)
                beginDelayedTransition(layoutRegisterFurList)
            }

            dropDownState = if (dropDownState == 0) {
                playAnimation(binding.imageviewRegisterDropDown, R.anim.spin)
                layoutRegisterFurHelp.layoutParams.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
                1
            } else {
                playAnimation(binding.imageviewRegisterDropDown, R.anim.reverse_spin)
                layoutRegisterFurHelp.layoutParams.height = collapsedHeight
                0
            }

            layoutRegisterFurHelp.requestLayout()
            layoutRegisterFurList.requestLayout()
        }
    }

    fun expandBottomSheet(view: View) {
        furBehavior.state = BottomSheetBehavior.STATE_EXPANDED

    }

    fun selectFurType(furType: FurType) {
        viewModel.setDogFurType(furType)

        with(binding.buttonRegisterChooseFurType) {
            when (furType) {
                FurType.Long -> {
                    this.text = getString(R.string.activity_register_long_fur)
                }
                FurType.Silky -> {
                    this.text = getString(R.string.activity_register_silky_fur)
                }
                FurType.Short -> {
                    this.text = getString(R.string.activity_register_short_fur)
                }
                FurType.Strong -> {
                    this.text = getString(R.string.activity_register_strong_fur)
                }
                FurType.Curly -> {
                    this.text = getString(R.string.activity_register_curly_fur)
                }
            }
            this.setTextColor(color(R.color.gray_depth1))
        }

        lifecycleScope.launch {
            delay(200L)
            furBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    fun setSizeHelpVisibility(visibility: Boolean) {
        with(binding.layoutRegisterSizeHelp) {
            this.visibility =
                if (visibility) {
                    playAnimation(this, R.anim.fade_in)
                    View.VISIBLE
                } else {
                    playAnimation(this, R.anim.fade_out)
                    View.GONE
                }
        }
    }

    fun register(view: View) {
        if (isFilledAll) {
            viewModel.storeDog()
            viewModel.setRegistered()
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }

    override fun setObserver() {
        viewModel.infoCount.observe(this@RegisterActivity) {
            if (it == 5) {
                playColorAnimation(
                    binding.buttonRegisterDone,
                    color(R.color.gray_depth3),
                    color(R.color.brand_color1)
                )
                isFilledAll = true
            }
        }
    }
}


