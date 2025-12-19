// PointActivity.kt

package com.example.nonoichistamp.points

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
// import androidx.compose.ui.semantics.text  // ← エラーの原因なのでこの行を削除
import com.example.nonoichistamp.PointManager
import com.example.nonoichistamp.R

class PointActivity : AppCompatActivity() {

    // ▼▼▼ UI部品の変数をここで宣言 ▼▼▼
    private lateinit var totalPointsTextView: TextView
    private lateinit var historyListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_point)

        // ▼▼▼ onCreateでUI部品を初期化 ▼▼▼
        totalPointsTextView = findViewById(R.id.total_points_text)
        historyListView = findViewById(R.id.history_list_view)

        // 初回表示処理は onResume に集約できるので、ここでは何もしなくてもOK
        // または、このまま残しておいても問題ありません。
    }

    override fun onResume() {
        super.onResume()
        // この画面が表示されるたびに、ポイントと履歴を最新の状態に更新する

        // PointManagerから現在の合計ポイントを取得して表示
        val currentPoints = PointManager.getPoints()
        totalPointsTextView.text = "現在の合計ポイント: $currentPoints pt"

        // PointManagerからポイント履歴を取得
        val logs = PointManager.getLogs()

        // 履歴をListViewに表示するための文字列リストを作成
        val logStrings = logs.map { log ->
            // 例: "2024-12-19 10:30: スタンプ取得 (+10 pt)"
            "${log.date}: ${log.reason} (${if (log.points > 0) "+" else ""}${log.points} pt)"
        }.reversed() // 新しい履歴が上に来るように逆順にする

        // ArrayAdapterを使ってListViewに履歴を表示
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, logStrings)
        historyListView.adapter = adapter
    }
}
