package com.jangjh123.shallwegoforawalk.ui.component

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jangjh123.shallwegoforawalk.R
import com.jangjh123.shallwegoforawalk.data.model.Size
import com.jangjh123.shallwegoforawalk.data.model.weather.Dog
import com.jangjh123.shallwegoforawalk.databinding.ItemListAddBinding
import com.jangjh123.shallwegoforawalk.databinding.ItemListDogBinding
import com.jangjh123.shallwegoforawalk.util.GenericDiffUtil

class DogListAdapter(
    private inline val onClickAddDog: () -> Unit,
    private inline val onClickRemoveDog: (id: Int) -> Unit
) :
    ListAdapter<Dog, RecyclerView.ViewHolder>(GenericDiffUtil()) {


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            0 -> {
                (holder as DogViewHolder).bind(getItem(position))
            }
            1 -> {
                (holder as AddDogViewHolder).bind()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> {
                DogViewHolder(
                    ItemListDogBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                AddDogViewHolder(
                    ItemListAddBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (currentList[position] != null) {
            0
        } else {
            1
        }
    }

    inner class DogViewHolder(private val binding: ItemListDogBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dog: Dog) {
            with(binding) {
                textviewDogName.text = dog.name
                imageviewDogFace.setImageDrawable(
                    when (dog.size) {
                        Size.Large -> {
                            ResourcesCompat.getDrawable(
                                root.resources,
                                R.drawable.ic_mini_large,
                                null
                            )
                        }
                        Size.Medium -> {
                            ResourcesCompat.getDrawable(
                                root.resources,
                                R.drawable.ic_mini_medium,
                                null
                            )
                        }
                        Size.Small -> {
                            ResourcesCompat.getDrawable(
                                root.resources,
                                R.drawable.ic_mini_small,
                                null
                            )
                        }
                    }
                )


                buttonDogDelete.setOnClickListener {
                    onClickRemoveDog(dog.id)
                }
            }
        }
    }

    inner class AddDogViewHolder(private val binding: ItemListAddBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.buttonAddDog.setOnClickListener {
                onClickAddDog()
            }
        }
    }
}