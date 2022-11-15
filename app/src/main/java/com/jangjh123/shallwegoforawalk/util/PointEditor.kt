package com.jangjh123.shallwegoforawalk.util

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.jangjh123.shallwegoforawalk.R
import com.jangjh123.shallwegoforawalk.data.model.Size
import com.jangjh123.shallwegoforawalk.data.model.weather.WeatherVO
import java.text.SimpleDateFormat
import java.util.*

class WalkInfoProvider(private val context: Context) {
    fun editPoint(weather: WeatherVO, index: Int, reasonList: ArrayList<String>?): Int {
        var point = 100
        val temperature = weather.hourlyList[index].temp
        val fine = weather.fine
        val uFine = weather.uFine
        val pop = weather.hourlyList[index].pop
        val windSpeed = weather.hourlyList[index].windSpeed
        when {
            temperature > 45 -> {
                reasonList.addIfExists("매우 높은 온도 (-90)")
                point -= 90
            }
            temperature > 35 -> {
                reasonList.addIfExists("매우 높은 온도 (-46)")
                point -= 46
            }
            temperature > 30 -> {
                reasonList.addIfExists("높은 온도 (-12)")
                point -= 12
            }
            temperature > 0 -> {
                reasonList.addIfExists("약간 낮은 온도 (-11)")
                point -= 11
            }
            temperature > -5 -> {
                reasonList.addIfExists("낮은 온도 (-22)")
                point -= 22
            }
            temperature > -10 -> {
                reasonList.addIfExists("매우 낮은 온도 (-52)")
                point -= 52
            }
            temperature > -50 -> {
                reasonList.addIfExists("매우 낮은 온도 (-100)")
                point -= 100
            }
        }

        when {
            fine > 355 -> {
                reasonList.addIfExists("미세먼지 매우 나쁨 (-60)")
                point -= 60
            }
            fine > 255 -> {
                reasonList.addIfExists("미세먼지 나쁨 (-25)")
                point -= 25
            }
            fine > 155 -> {
                reasonList.addIfExists("미세먼지 약간 나쁨 (-5)")
                point -= 5
            }
        }

        when {
            uFine > 151 -> {
                reasonList.addIfExists("초미세먼지 매우 나쁨 (-60)")
                point -= 60
            }
            uFine > 56 -> {
                reasonList.addIfExists("초미세먼지 나쁨 (-25)")
                point -= 25
            }
            uFine > 36 -> {
                reasonList.addIfExists("초미세먼지 약간 나쁨 (-5)")
                point -= 5
            }
        }

        when {
            pop > 60 -> {
                reasonList.addIfExists("강수 확률 높음 (-100)")
                point -= 100
            }
            pop > 30 -> {
                reasonList.addIfExists("강수 확률 약간 높음 (-50)")
                point -= 50
            }
            pop > 0 -> {
                reasonList.addIfExists("강수 확률 조금 (-12)")
                point -= 12
            }
        }

        when {
            windSpeed > 14 -> {
                reasonList.addIfExists("매우 빠른 풍속 (-86)")
                point -= 86
            }
            windSpeed > 9 -> {
                reasonList.addIfExists("빠른 풍속 (-24)")
                point -= 24
            }
            windSpeed > 7 -> {
                reasonList.addIfExists("다소 빠른 풍속 (-7)")
                point -= 7
            }
        }

        return if (point < 0) {
            0
        } else {
            point
        }
    }

    fun getBackgroundByPoint(point: Int): Drawable? {
        return ContextCompat.getDrawable(
            context, when {
                point > 80 -> R.drawable.background_gradient_1
                point > 60 -> R.drawable.background_gradient_2
                point > 40 -> R.drawable.background_gradient_3
                point > 20 -> R.drawable.background_gradient_4
                else -> R.drawable.background_gradient_5
            }
        )
    }

    fun getImageByPointAndSize(point: Int, size: Size): Drawable? {
        return ContextCompat.getDrawable(
            context, when (size) {
                Size.Small -> {
                    when {
                        point > 80 -> R.drawable.dog_small_1
                        point > 60 -> R.drawable.dog_small_2
                        point > 40 -> R.drawable.dog_small_3
                        point > 20 -> R.drawable.dog_small_4
                        else -> R.drawable.dog_small_5
                    }
                }
                Size.Medium -> {
                    when {
                        point > 80 -> R.drawable.dog_medium_1
                        point > 60 -> R.drawable.dog_medium_2
                        point > 40 -> R.drawable.dog_medium_3
                        point > 20 -> R.drawable.dog_medium_4
                        else -> R.drawable.dog_medium_5
                    }
                }
                Size.Large -> {
                    when {
                        point > 80 -> R.drawable.dog_large_1
                        point > 60 -> R.drawable.dog_large_2
                        point > 40 -> R.drawable.dog_large_3
                        point > 20 -> R.drawable.dog_large_4
                        else -> R.drawable.dog_large_5
                    }
                }
            }
        )
    }

    fun getMainTextByPoint(point: Int): String {
        return when {
            point > 80 -> context.getString(R.string.item_main_point_desc_1)
            point > 60 -> context.getString(R.string.item_main_point_desc_2)
            point > 40 -> context.getString(R.string.item_main_point_desc_3)
            point > 20 -> context.getString(R.string.item_main_point_desc_4)
            else -> context.getString(R.string.item_main_point_desc_5)
        }
    }

    fun getTimeTable(): List<String> {
        val curTime = SimpleDateFormat("HH").apply {
            timeZone = TimeZone.getTimeZone("Asia/Seoul")
        }.format(
            Date(System.currentTimeMillis())
        ).toInt()

        val timeTable = ArrayList<String>()
        for (i in 1..6) {
            if (curTime + i > 23) {
                timeTable.add("${(curTime + i) - 24}시")
            } else {
                timeTable.add("${curTime + i}시")
            }
        }
        return timeTable
    }
}

private fun ArrayList<String>?.addIfExists(text: String) {
    this.let {
        it?.add(text)
    }
}