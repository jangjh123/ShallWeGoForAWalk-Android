package com.jangjh123.shallwegoforawalk.ui.component

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jangjh123.shallwegoforawalk.data.model.weather.Dog
import com.jangjh123.shallwegoforawalk.data.model.weather.WeatherVO
import com.jangjh123.shallwegoforawalk.ui.fragment.dog.DogFragment
import com.jangjh123.shallwegoforawalk.ui.fragment.dog_list.DogListFragment

class ViewPagerAdapter(
    private val weather: WeatherVO,
    private val dogs: List<Dog>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return dogs.size + 1
    }

    override fun createFragment(position: Int): Fragment {
        return when {
            position < dogs.size -> {
                DogFragment(weather, dogs[position])
            }
            else -> {
                DogListFragment(dogs)
            }
        }
    }
}