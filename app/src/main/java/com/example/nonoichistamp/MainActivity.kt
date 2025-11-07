package com.example.nonoichistamp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nonoichistamp.points.PointActivity
import com.example.nonoichistamp.points.PointManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 保存データを読み込む
        PointManager.init(this)

        // ボタン取得
        val btnAddSteps = findViewById<Button>(R.id.btnAddSteps)
        val btnAddStamp = findViewById<Button>(R.id.btnAddStamp)
        val btnShowPoints = findViewById<Button>(R.id.btnShowPoints)
        val btnUseReward = findViewById<Button>(R.id.btnUseReward)
        val btnReset = findViewById<Button>(R.id.btnReset)

        // 歩数追加（仮に1万歩）
        btnAddSteps.setOnClickListener {
            PointManager.addSteps(10_000, this)
            Toast.makeText(this, "歩数を追加しました", Toast.LENGTH_SHORT).show()
        }

        // スタンプ追加
        btnAddStamp.setOnClickListener {
            PointManager.addStamp(this)
            Toast.makeText(this, "スタンプを追加しました", Toast.LENGTH_SHORT).show()
        }

        // ポイント画面に移動
        btnShowPoints.setOnClickListener {
            startActivity(Intent(this, PointActivity::class.java))
        }

        // 景品交換（50ポイント使用）
        btnUseReward.setOnClickListener {
            if (PointManager.getPoints() >= 50) {
                PointManager.subtractPoints(50, this)
                Toast.makeText(this, "50ポイントを使用しました", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "ポイントが足りません", Toast.LENGTH_SHORT).show()
            }
        }

        // ポイントリセット
        btnReset.setOnClickListener {
            PointManager.resetPoints(this)
        }
    }
}
