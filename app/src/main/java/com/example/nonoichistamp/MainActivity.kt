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

        // SharedPreferences 読み込み
        PointManager.init(this)

        val btnAddSteps = findViewById<Button>(R.id.btnAddSteps)
        val btnAddStamp = findViewById<Button>(R.id.btnAddStamp)
        val btnShowPoints = findViewById<Button>(R.id.btnShowPoints)

        btnAddSteps.setOnClickListener {
            PointManager.addSteps(10000) // 1万歩で100pt
        }

        btnAddStamp.setOnClickListener {
            PointManager.addStamp() // 1スタンプで10pt
        }

        btnShowPoints.setOnClickListener {
            startActivity(Intent(this, PointActivity::class.java))
        }
    }
}
