package com.jangjh123.shallwegoforawalk.ui.component

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jangjh123.shallwegoforawalk.data.model.DogListTypes
import com.jangjh123.shallwegoforawalk.databinding.ItemMainBinding
import com.jangjh123.shallwegoforawalk.util.GenericDiffUtil

class MainAdapter :
    ListAdapter<DogListTypes.Dog, RecyclerView.ViewHolder>(GenericDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            ItemMainBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val dogWeather = getItem(position)
            holder.bind(dogWeather)
        }
    }

    inner class ViewHolder(private val binding: ItemMainBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dogWeather: DogListTypes.Dog) {
            with(binding) {
                textviewDogName.text = dogWeather.name
            }
        }
    }
}