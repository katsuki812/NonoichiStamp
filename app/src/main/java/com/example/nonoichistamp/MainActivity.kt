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

        // 保存値を読み込む
        PointManager.loadPoints(this)

        val btnAddSteps = findViewById<Button>(R.id.btnAddSteps)
        val btnAddStamp = findViewById<Button>(R.id.btnAddStamp)
        val btnShowPoints = findViewById<Button>(R.id.btnShowPoints)
        val btnUseReward = findViewById<Button>(R.id.btnUseReward)
        val btnReset = findViewById<Button>(R.id.btnReset)

        btnAddSteps.setOnClickListener {
            PointManager.addSteps(10_000, this)
            Toast.makeText(this, "歩数を追加しました", Toast.LENGTH_SHORT).show()
        }

        btnAddStamp.setOnClickListener {
            PointManager.addStamp(this)
            Toast.makeText(this, "スタンプを追加しました", Toast.LENGTH_SHORT).show()
        }

        btnShowPoints.setOnClickListener {
            startActivity(Intent(this, PointActivity::class.java))
        }

        btnUseReward.setOnClickListener {
            PointManager.subtractPoints(50, this)
            Toast.makeText(this, "50ポイントを使用しました", Toast.LENGTH_SHORT).show()
        }

        btnReset.setOnClickListener {
            PointManager.resetPoints(this)
        }
    }
}
