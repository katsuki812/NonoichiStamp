// PointManager.kt

// パッケージ名を修正
package com.example.nonoichistamp

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

// PointManager.kt の中身はご提示いただいたままでOKです。
// ただし、パッケージ宣言だけは上記のように com.example.nonoichistamp になっているか確認してください。

data class PointLog(val date: String, val reason: String, val points: Int)

object PointManager {
    private var steps = 0
    private var stamps = 0
    private var points = 0
    private lateinit var prefs: SharedPreferences
    private val logs = mutableListOf<PointLog>()

    private fun getDateFormatter(): SimpleDateFormat {
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    }

    fun init(context: Context) {
        prefs = context.getSharedPreferences("PointData", Context.MODE_PRIVATE)
        steps = prefs.getInt("steps", 0)
        stamps = prefs.getInt("stamps", 0)
        points = prefs.getInt("points", 0)

        logs.clear()
        val logsJson = prefs.getString("logs", "[]") ?: "[]"
        val jsonArray = JSONArray(logsJson)
        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)
            logs.add(
                PointLog(
                    obj.getString("date"),
                    obj.getString("reason"),
                    obj.getInt("points")
                )
            )
        }
    }

    fun addSteps(value: Int) {
        steps += value
        val earned = value / 100 // 1万歩で100ポイントと仮定
        points += earned
        addLog("歩数追加", earned)
        save()
    }

    fun addStamp() {
        stamps += 1
        val earned = 10 // スタンプ1回で10ポイントと仮定
        points += earned
        addLog("スタンプ取得", earned)
        save()
    }

    fun addNfcReadPoint() {
        val earned = 5 // NFC読み取りで5ポイント加算
        points += earned
        addLog("NFC読み取り", earned)
        save()
    }

    fun usePoints(value: Int) {
        if (points >= value) {
            points -= value
            addLog("景品交換", -value)
            save()
        }
    }

    fun reset() {
        steps = 0
        stamps = 0
        points = 0
        logs.clear()
        save()
    }

    private fun addLog(reason: String, point: Int) {
        val date = getDateFormatter().format(Date())
        logs.add(PointLog(date, reason, point))
    }

    private fun save() {
        prefs.edit().apply {
            putInt("steps", steps)
            putInt("stamps", stamps)
            putInt("points", points)

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
