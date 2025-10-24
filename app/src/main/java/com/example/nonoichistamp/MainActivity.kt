package com.example.nonoichistamp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.nonoichistamp.points.PointActivity
import com.example.nonoichistamp.points.PointManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAddSteps = findViewById<Button>(R.id.btnAddSteps)
        val btnAddStamp = findViewById<Button>(R.id.btnAddStamp)
        val btnShowPoints = findViewById<Button>(R.id.btnShowPoints)

        btnAddSteps.setOnClickListener {
            PointManager.addSteps(10_000) // 仮に1万歩追加
        }

        btnAddStamp.setOnClickListener {
            PointManager.addStamp() // スタンプ追加
        }

        btnShowPoints.setOnClickListener {
            startActivity(Intent(this, PointActivity::class.java))
        }
    }
}
