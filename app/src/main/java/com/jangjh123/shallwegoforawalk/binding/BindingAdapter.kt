package com.jangjh123.shallwegoforawalk.binding

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*


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
    @BindingAdapter("setImageWithGlide")
    fun setImageWithGlide(view: ImageView, url: String?) {
        if (url != null) {
            Glide.with(view.context)
                .load(url)
                .into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("setCurrentTime")
    fun setCurrentTime(view: TextView, boolean: Boolean) {
        if (boolean) {
            view.text = SimpleDateFormat("HH:mm").apply {
                timeZone = TimeZone.getTimeZone("Asia/Seoul")
            }.format(
                Date(System.currentTimeMillis())
            ).toString() + " 업데이트"
        }
    }
}