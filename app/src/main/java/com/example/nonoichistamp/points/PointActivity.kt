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

        // 保存データを読み込む
        PointManager.init(this)

        // TextView の取得
        val txtPoints = findViewById<TextView>(R.id.txtPoints)
        val txtSteps = findViewById<TextView>(R.id.txtSteps)
        val txtStamps = findViewById<TextView>(R.id.txtStamps)
        // Achievement や Log はレイアウトに追加するか、不要ならコメントアウト
        // val txtAchievement = findViewById<TextView>(R.id.txtAchievement)
        // val txtLog = findViewById<TextView>(R.id.txtLog)

        // 表示更新
        txtPoints.text = "現在のポイント：${PointManager.getPoints()}"
        txtSteps.text = "歩数：${PointManager.getSteps()}"
        txtStamps.text = "スタンプ取得回数：${PointManager.getStamps()}"
    }

    fun buttonOnClick(view: View) {
        finish() // 前の画面に戻る
    }
}
