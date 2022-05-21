package com.jangjh123.shallwegoforawalk.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.*


object BindingAdapter {
    @JvmStatic
    @BindingAdapter("setLayoutManager")
    fun setLayoutManager(view: RecyclerView, orientation: Int) {
        view.layoutManager = LinearLayoutManager(view.context, orientation, false)
    }

    @JvmStatic
    @BindingAdapter("setAdapter")
    fun setAdapter(view: RecyclerView, adapter: ListAdapter<Any, RecyclerView.ViewHolder>) {
        view.adapter = adapter
    }


    @JvmStatic
    @BindingAdapter("setSnapHelper")
    fun setSnapHelper(view: RecyclerView, boolean: Boolean) {
        if (boolean) {
            PagerSnapHelper().attachToRecyclerView(view)
        }
    }
}