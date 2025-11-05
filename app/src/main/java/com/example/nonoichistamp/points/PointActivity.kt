package com.example.nonoichistamp.points

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.nonoichistamp.R

class PointActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_point)

        val txtPoints = findViewById<TextView>(R.id.txtPoints)
        val txtSteps = findViewById<TextView>(R.id.txtSteps)
        val txtStamps = findViewById<TextView>(R.id.txtStamps)
        val txtAchievement = findViewById<TextView>(R.id.txtAchievement)
        val txtLog = findViewById<TextView>(R.id.txtLog)

        txtPoints.text = "現在のポイント：${PointManager.getPoints()}"
        txtSteps.text = "歩数：${PointManager.getSteps()}"
        txtStamps.text = "スタンプ取得回数：${PointManager.getStamps()}"
        txtAchievement.text = "称号：${getAchievementTitle(PointManager.getPoints())}"

        val logText = StringBuilder()
        for (log in PointManager.getLogs()) {
            logText.append("${log.date} : ${log.reason} +${log.points}pt\n")
        }
        txtLog.text = logText.toString()
    }

    private fun getAchievementTitle(points: Int): String {
        return when {
            points >= 1000 -> "ゴールドランナー"
            points >= 500 -> "シルバーウォーカー"
            points >= 100 -> "ブロンズチャレンジャー"
            else -> "まだなし"
        }
    }

    fun buttonOnClick(view: View) {
        finish()
    }
}
