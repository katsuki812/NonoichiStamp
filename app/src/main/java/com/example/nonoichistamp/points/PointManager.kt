package com.example.nonoichistamp.points

import android.content.Context
import android.content.SharedPreferences

object PointManager {
    private var steps = 0
    private var stamps = 0
    private var points = 0
    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.getSharedPreferences("PointData", Context.MODE_PRIVATE)
        steps = prefs.getInt("steps", 0)
        stamps = prefs.getInt("stamps", 0)
        points = prefs.getInt("points", 0)
    }

    fun addSteps(value: Int) {
        steps += value
        points += value / 100 // 100歩につき1ポイント
        save()
    }

    fun addStamp() {
        stamps += 1
        points += 10 // スタンプ1つで10ポイント
        save()
    }

    private fun save() {
        prefs.edit().apply {
            putInt("steps", steps)
            putInt("stamps", stamps)
            putInt("points", points)
            apply()
        }
    }

    fun getSteps() = steps
    fun getStamps() = stamps
    fun getPoints() = points
}
