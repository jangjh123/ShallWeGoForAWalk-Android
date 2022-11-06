package com.jangjh123.shallwegoforawalk.ui.component

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jangjh123.shallwegoforawalk.data.model.DogListTypes
import com.jangjh123.shallwegoforawalk.data.model.weather.WeatherVO
import com.jangjh123.shallwegoforawalk.ui.fragment.dog.DogFragment
import com.jangjh123.shallwegoforawalk.ui.fragment.dog_list.DogListFragment
import java.util.*

class ViewPagerAdapter(
    private val list: LinkedList<DogListTypes.Dog>,
    private val weather: WeatherVO,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            list.size - 1 -> {
//                DogListFragment(list[position], weather)
                DogListFragment()
            }
            else -> {
                DogFragment()
            }
        }
    }
}