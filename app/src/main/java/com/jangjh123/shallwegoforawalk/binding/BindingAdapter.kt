package com.jangjh123.shallwegoforawalk.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper


object BindingAdapter {
    @JvmStatic
    @BindingAdapter("setLayoutManager")
    fun setLayoutManager(view: RecyclerView, orientation: Int) {
        view.layoutManager = LinearLayoutManager(view.context, orientation, false)
    }

    @JvmStatic
    @BindingAdapter("setSnapHelper")
    fun setSnapHelper(view: RecyclerView, boolean: Boolean) {
        if (boolean) {
            PagerSnapHelper().attachToRecyclerView(view)
        }
    }
}