package com.example.nonoichistamp.points

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nonoichistamp.R

class PointActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_point)

        val txtPoints = findViewById<TextView>(R.id.txtPoints)
        val txtSteps = findViewById<TextView>(R.id.txtSteps)
        val txtStamps = findViewById<TextView>(R.id.txtStamps)
        val txtLog = findViewById<TextView>(R.id.txtLog)

        val btnUseReward = findViewById<Button>(R.id.btnUseReward)
        val btnReset = findViewById<Button>(R.id.btnReset)

        txtPoints.text = "現在のポイント：${PointManager.getPoints()}"
        txtSteps.text = "歩数：${PointManager.getSteps()}"
        txtStamps.text = "スタンプ取得回数：${PointManager.getStamps()}"

        // 履歴をセット
        val logText = StringBuilder()
        for (log in PointManager.getLogs()) {
            logText.append("${log.date} : ${log.reason} ${log.points}pt\n")
        }
        txtLog.text = logText.toString()

        // 景品交換ボタン
        btnUseReward.setOnClickListener {
            if (PointManager.getPoints() < 100) {
                Toast.makeText(this, "ポイントが足りません（100pt必要）", Toast.LENGTH_SHORT).show()
            } else {
                PointManager.usePoints(100)
                Toast.makeText(this, "景品を交換しました！", Toast.LENGTH_SHORT).show()
                recreate()
            }
        }

        // リセットボタン
        btnReset.setOnClickListener {
            PointManager.reset()
            Toast.makeText(this, "データをリセットしました", Toast.LENGTH_SHORT).show()
            recreate()
        }
    }

    fun buttonOnClick(view: View) {
        finish()
    }
}
