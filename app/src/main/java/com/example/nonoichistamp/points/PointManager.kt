package com.example.nonoichistamp.points

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

data class PointLog(val date: String, val reason: String, val points: Int)

object PointManager {
    private var steps = 0
    private var stamps = 0
    private var points = 0
    private lateinit var prefs: SharedPreferences
    private val logs = mutableListOf<PointLog>()

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    fun init(context: Context) {
        prefs = context.getSharedPreferences("PointData", Context.MODE_PRIVATE)
        steps = prefs.getInt("steps", 0)
        stamps = prefs.getInt("stamps", 0)
        points = prefs.getInt("points", 0)

        // 履歴を読み込む
        val logsJson = prefs.getString("logs", "[]")
        logs.clear()
        val jsonArray = JSONArray(logsJson)
        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)
            logs.add(PointLog(obj.getString("date"), obj.getString("reason"), obj.getInt("points")))
        }
    }

    fun addSteps(value: Int) {
        steps += value
        val earned = value / 100
        points += earned
        addLog("歩数追加", earned)
        save()
    }

    fun addStamp() {
        stamps += 1
        val earned = 10
        points += earned
        addLog("スタンプ取得", earned)
        save()
    }

    private fun addLog(reason: String, point: Int) {
        val date = dateFormat.format(Date())
        logs.add(PointLog(date, reason, point))
    }

    private fun save() {
        prefs.edit().apply {
            putInt("steps", steps)
            putInt("stamps", stamps)
            putInt("points", points)

            // 履歴をJSONにして保存
            val jsonArray = JSONArray()
            for (log in logs) {
                val obj = JSONObject()
                obj.put("date", log.date)
                obj.put("reason", log.reason)
                obj.put("points", log.points)
                jsonArray.put(obj)
            }
            putString("logs", jsonArray.toString())
            apply()
        }
    }

    fun getSteps() = steps
    fun getStamps() = stamps
    fun getPoints() = points
    fun getLogs() = logs.toList()
}
