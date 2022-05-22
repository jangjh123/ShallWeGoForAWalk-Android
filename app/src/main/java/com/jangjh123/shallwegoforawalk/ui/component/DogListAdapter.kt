package com.jangjh123.shallwegoforawalk.ui.component

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jangjh123.shallwegoforawalk.data.model.DogListTypes
import com.jangjh123.shallwegoforawalk.data.model.ListItemType
import com.jangjh123.shallwegoforawalk.databinding.ItemListAddBinding
import com.jangjh123.shallwegoforawalk.databinding.ItemListDogBinding
import com.jangjh123.shallwegoforawalk.util.GenericDiffUtil

class DogListAdapter(
    private inline val onClickAddDog: () -> Unit,
    private inline val onClickRemoveDog: () -> Unit
) :
    ListAdapter<DogListTypes, RecyclerView.ViewHolder>(GenericDiffUtil()) {


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            0 -> {
                (holder as DogViewHolder).bind(getItem(position) as DogListTypes.Dog)
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
        return when (getItem(position)!!.type) {
            ListItemType.DOG -> {
                0
            }
            ListItemType.ADD -> {
                1
            }
        }
    }

    inner class DogViewHolder(private val binding: ItemListDogBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dog: DogListTypes.Dog) {
            with(binding) {
                textviewDogName.text = dog.name
                buttonDogDelete.setOnClickListener {
                    onClickRemoveDog
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