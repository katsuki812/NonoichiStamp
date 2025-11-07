package com.example.nonoichistamp.points

import android.content.Context
import android.widget.Toast

object PointManager {
    private var steps = 0
    private var stamps = 0
    private var points = 0

    // ポイント追加
    fun addSteps(value: Int, context: Context) {
        steps += value
        points += value / 100
        savePoints(context)
    }

    fun addStamp(context: Context) {
        stamps += 1
        points += 10
        savePoints(context)
    }

    // ポイント減算（景品交換用）
    fun subtractPoints(amount: Int, context: Context) {
        points -= amount
        if (points < 0) points = 0
        savePoints(context)
    }

    // リセット
    fun resetPoints(context: Context) {
        steps = 0
        stamps = 0
        points = 0
        savePoints(context)
        Toast.makeText(context, "ポイントをリセットしました", Toast.LENGTH_SHORT).show()
    }

    // Getter
    fun getSteps() = steps
    fun getStamps() = stamps
    fun getPoints() = points

    // 永続化
    private fun savePoints(context: Context) {
        val prefs = context.getSharedPreferences("points", Context.MODE_PRIVATE)
        prefs.edit()
            .putInt("steps", steps)
            .putInt("stamps", stamps)
            .putInt("points", points)
            .apply()
    }

    fun loadPoints(context: Context) {
        val prefs = context.getSharedPreferences("points", Context.MODE_PRIVATE)
        steps = prefs.getInt("steps", 0)
        stamps = prefs.getInt("stamps", 0)
        points = prefs.getInt("points", 0)
    }
}
