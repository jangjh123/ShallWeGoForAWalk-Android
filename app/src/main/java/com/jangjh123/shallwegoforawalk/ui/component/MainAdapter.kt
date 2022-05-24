package com.jangjh123.shallwegoforawalk.ui.component

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jangjh123.shallwegoforawalk.R
import com.jangjh123.shallwegoforawalk.data.model.DogListTypes
import com.jangjh123.shallwegoforawalk.data.model.weather.HourlyWeather
import com.jangjh123.shallwegoforawalk.data.model.weather.WeatherVO
import com.jangjh123.shallwegoforawalk.databinding.ItemMainBinding
import com.jangjh123.shallwegoforawalk.util.GenericDiffUtil
import java.text.SimpleDateFormat
import java.util.*

class MainAdapter(
    private val weatherData: WeatherVO,
    private val address: String
) :
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
        fun bind(dog: DogListTypes.Dog) {
            with(binding) {
                textviewDogName.text = dog.name
                textviewAddress.text = address

                val curPoint = calculatePoint(dog, weatherData.hourlyWeatherList[0])

                textviewPointDesc.text = when {
                    curPoint > 80 -> {
                        root.context.getString(R.string.item_main_point_desc_1)
                    }
                    curPoint > 60 -> {
                        root.context.getString(R.string.item_main_point_desc_2)
                    }
                    curPoint > 40 -> {
                        root.context.getString(R.string.item_main_point_desc_3)
                    }
                    curPoint > 20 -> {
                        root.context.getString(R.string.item_main_point_desc_4)
                    }
                    else -> {
                        root.context.getString(R.string.item_main_point_desc_5)
                    }
                }

                background.setBackgroundColor(
                    when {
                        curPoint > 80 -> {
                            ContextCompat.getColor(root.context, R.color.point_color1)
                        }
                        curPoint > 60 -> {
                            ContextCompat.getColor(root.context, R.color.point_color2)
                        }
                        curPoint > 40 -> {
                            ContextCompat.getColor(root.context, R.color.point_color3)
                        }
                        curPoint > 20 -> {
                            ContextCompat.getColor(root.context, R.color.point_color4)
                        }
                        else -> {
                            ContextCompat.getColor(root.context, R.color.point_color5)
                        }
                    }
                )

                textviewMainPoint.text = curPoint.toString()
                textviewCurPoint.text = curPoint.toString()

                imageviewMain.setImageResource(
                    when {
                        curPoint > 80 -> {
                            when (dog.size) {
                                0 -> {
                                    R.drawable.dog_large_1
                                }
                                1 -> {
                                    R.drawable.dog_medium_1
                                }
                                else -> {
                                    R.drawable.dog_small_1
                                }
                            }
                        }
                        curPoint > 60 -> {
                            when (dog.size) {
                                0 -> {
                                    R.drawable.dog_large_2
                                }
                                1 -> {
                                    R.drawable.dog_medium_2
                                }
                                else -> {
                                    R.drawable.dog_small_2
                                }
                            }
                        }
                        curPoint > 40 -> {
                            when (dog.size) {
                                0 -> {
                                    R.drawable.dog_large_3
                                }
                                1 -> {
                                    R.drawable.dog_medium_3
                                }
                                else -> {
                                    R.drawable.dog_small_3
                                }
                            }
                        }
                        curPoint > 20 -> {
                            when (dog.size) {
                                0 -> {
                                    R.drawable.dog_large_4
                                }
                                1 -> {
                                    R.drawable.dog_medium_4
                                }
                                else -> {
                                    R.drawable.dog_small_5
                                }
                            }
                        }
                        else -> {
                            when (dog.size) {
                                0 -> {
                                    R.drawable.dog_large_5
                                }
                                1 -> {
                                    R.drawable.dog_medium_5
                                }
                                else -> {
                                    R.drawable.dog_small_5
                                }
                            }
                        }
                    }
                )

                val updatedTime = SimpleDateFormat("HH:mm").apply {
                    timeZone = TimeZone.getTimeZone("Asia/Seoul")
                }.format(
                    Date(System.currentTimeMillis())
                ).toString()

                textviewUpdatedTime.text = "$updatedTime 업데이트"

                textviewPointAfter1.text =
                    calculatePoint(dog, weatherData.hourlyWeatherList[1]).toString()
                textviewPointAfter2.text =
                    calculatePoint(dog, weatherData.hourlyWeatherList[2]).toString()
                textviewPointAfter3.text =
                    calculatePoint(dog, weatherData.hourlyWeatherList[3]).toString()
                textviewPointAfter4.text =
                    calculatePoint(dog, weatherData.hourlyWeatherList[4]).toString()
                textviewPointAfter5.text =
                    calculatePoint(dog, weatherData.hourlyWeatherList[5]).toString()
                textviewPointAfter6.text =
                    calculatePoint(dog, weatherData.hourlyWeatherList[6]).toString()

                val curTime = SimpleDateFormat("HH").apply {
                    timeZone = TimeZone.getTimeZone("Asia/Seoul")
                }.format(
                    Date(System.currentTimeMillis())
                ).toInt()

                val timeTable = ArrayList<Int>()

                for (i in 1..6) {
                    if (curTime + i > 23) {
                        timeTable.add((curTime + i) - 24)
                    } else {
                        timeTable.add(curTime + i)
                    }
                }

                textviewTimeAfter1.text = "${timeTable[0]}시"
                textviewTimeAfter2.text = "${timeTable[1]}시"
                textviewTimeAfter3.text = "${timeTable[2]}시"
                textviewTimeAfter4.text = "${timeTable[3]}시"
                textviewTimeAfter5.text = "${timeTable[4]}시"
                textviewTimeAfter6.text = "${timeTable[5]}시"
            }
        }

        private fun calculatePoint(dog: DogListTypes.Dog, hourlyWeather: HourlyWeather): Int {
            var point = 100

            if (dog.furType == 0 && hourlyWeather.temp > 35) {
                point = 0
            }

            if (dog.age > 10 && hourlyWeather.temp > 35) {
                point = 0
            }

            if (dog.furType == 2 && hourlyWeather.temp < 0) {
                point = 0
            }

            if (dog.age > 10 && hourlyWeather.temp < 0) {
                point = 0
            }

            point -= when {
                weatherData.ultraFineDust > 151 -> {
                    60
                }
                weatherData.ultraFineDust > 56 -> {
                    25
                }
                weatherData.ultraFineDust > 36 -> {
                    5
                }
                else -> {
                    0
                }
            }

            point -= when {
                weatherData.fineDust > 355 -> {
                    60
                }
                weatherData.fineDust > 255 -> {
                    25
                }
                weatherData.fineDust > 155 -> {
                    5
                }
                else -> {
                    0
                }
            }

            point -= when {
                hourlyWeather.temp > 45 -> {
                    90
                }
                hourlyWeather.temp > 35 -> {
                    46
                }
                hourlyWeather.temp > 30 -> {
                    12
                }
                hourlyWeather.temp > 10 -> {
                    0
                }
                hourlyWeather.temp > 0 -> {
                    11
                }
                hourlyWeather.temp > -5 -> {
                    22
                }
                hourlyWeather.temp > -10 -> {
                    52
                }
                hourlyWeather.temp > -50 -> {
                    100
                }
                else -> {
                    0
                }
            }

            point -= when {
                hourlyWeather.pop > 60 -> {
                    100
                }
                hourlyWeather.pop > 30 -> {
                    50
                }
                hourlyWeather.pop > 0 -> {
                    12
                }
                else -> {
                    0
                }
            }

            point -= when {
                hourlyWeather.windSpeed > 14 -> {
                    86
                }
                hourlyWeather.windSpeed > 9 -> {
                    24
                }
                hourlyWeather.windSpeed > 7 -> {
                    7
                }
                else -> {
                    0
                }
            }

            if (point < 0) {
                point = 0
            }

            return point
        }
    }
}