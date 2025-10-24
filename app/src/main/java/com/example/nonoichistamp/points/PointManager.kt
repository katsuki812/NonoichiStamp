package com.example.nonoichistamp.points

object PointManager {
    private var steps = 0
    private var stamps = 0
    private var points = 0

    fun addSteps(value: Int) {
        steps += value
        points += value / 100 // 100歩につき1ポイント
    }

    fun addStamp() {
        stamps += 1
        points += 10 // スタンプ1つで10ポイント
    }

    fun getSteps() = steps
    fun getStamps() = stamps
    fun getPoints() = points
}
