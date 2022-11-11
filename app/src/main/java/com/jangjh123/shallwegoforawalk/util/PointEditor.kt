package com.jangjh123.shallwegoforawalk.util

import android.content.Context
import androidx.core.content.ContextCompat
import com.jangjh123.shallwegoforawalk.R
import com.jangjh123.shallwegoforawalk.data.model.weather.WeatherVO

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

    fun getColorByPoint(point: Int): Int {
        return ContextCompat.getColor(
            context, when {
                point > 80 -> {
                    R.color.point_color1
                }
                point > 60 -> {
                    R.color.point_color2
                }
                point > 40 -> {
                    R.color.point_color3
                }
                point > 20 -> {
                    R.color.point_color4
                }
                else -> {
                    R.color.point_color5
                }
            }
        )
    }
}

private fun ArrayList<String>?.addIfExists(text: String) {
    this.let {
        it?.add(text)
    }
}