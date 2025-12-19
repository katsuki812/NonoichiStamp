package com.example.nonoichistamp

import android.app.PendingIntent
import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.tech.NfcA
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nonoichistamp.points.PointActivity

class MainActivity : AppCompatActivity() {

    // クラスのプロパティとして、NFC関連の部品とUI部品を宣言します。
    private var nfcAdapter: NfcAdapter? = null
    private lateinit var pendingIntent: PendingIntent
    private val techListsArray = arrayOf(arrayOf(NfcA::class.java.name))
    private lateinit var nfcIdTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. アプリ全体で使うポイント管理機能を初期化します。
        PointManager.init(applicationContext)

        // 2. 画面上のUI部品を変数に割り当てます。
        nfcIdTextView = findViewById(R.id.nfc_id_text)

        // 3. すべてのボタンの動作を設定します。この処理はNFCとは完全に独立しています。
        setupButtons()

        // 4. NFCを読み取るための準備をします。
        initializeNfc()

        // 5. アプリがNFCタグによって起動された場合に備え、IDの表示処理を呼び出します。
        handleNfcIntent(intent)
    }

    override fun onResume() {
        super.onResume()
        // 6. アプリが画面に表示されたら、NFCの読み取りを最優先で行う設定を有効にします。
        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, null, techListsArray)
    }

    override fun onPause() {
        super.onPause()
        // 7. アプリが非表示になったら、NFCの優先読み取り設定を解除します。
        nfcAdapter?.disableForegroundDispatch(this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        // 8. アプリが既に起動している状態でNFCがスキャンされたら、このメソッドが呼ばれます。
        //    ここでもIDの表示処理を呼び出します。
        handleNfcIntent(intent)
    }

    /**
     * NFCを読み取るための初期設定を行うメソッドです。
     */
    private fun initializeNfc() {
        // スマートフォンのNFC機能を取得します。
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (nfcAdapter == null) {
            Toast.makeText(this, "この端末はNFCに対応していません", Toast.LENGTH_LONG).show()
        }

        // NFCを検知したときに、このMainActivity自身を呼び出すためのIntentを作成します。
        val nfcIntent = Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

        // Android 12以降のセキュリティ強化に対応するためのフラグを設定します。
        // NFC読み取りでは、システムがIntentを「変更」する必要があるため、`FLAG_MUTABLE`が必須です。
        val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }

        // 上記のIntentとフラグを使って、システムに渡すためのPendingIntentを作成します。
        pendingIntent = PendingIntent.getActivity(this, 0, nfcIntent, flags)
    }

    /**
     * NFCで読み取ったIDを画面に表示するための共通メソッドです。
     */
    private fun handleNfcIntent(intent: Intent) {
        // IntentのアクションがNFCの技術検知（TECH_DISCOVERED）であるかを確認します。
        if (NfcAdapter.ACTION_TECH_DISCOVERED == intent.action) {
            // タグのIDをバイト配列として取得します。
            val id: ByteArray? = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID)
            // バイト配列を人間が読める16進数の文字列に変換します。
            val nfcId = id?.joinToString(separator = "") { "%02X".format(it) } ?: "取得失敗"

            // 変換したIDを画面のTextViewに表示します。
            nfcIdTextView.text = "NFC ID: $nfcId"

            // ログと短いメッセージで、読み取りが成功したことをユーザーに知らせます。
            Log.d("NFC_READER", "NFC ID: $nfcId")
            Toast.makeText(this, "NFCタグを読み取りました", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * すべてのボタンにクリックされたときの動作を割り当てるメソッドです。
     * このメソッドはNFC関連のコードから完全に独立しているため、相互に影響しません。
     */
    private fun setupButtons() {
        findViewById<Button>(R.id.btnAddSteps).setOnClickListener {
            PointManager.addSteps(10000)
            val currentPoints = PointManager.getPoints()
            Toast.makeText(this, "歩数を追加しました。現在 $currentPoints pt", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.btnAddStamp).setOnClickListener {
            PointManager.addStamp()
            val currentPoints = PointManager.getPoints()
            Toast.makeText(this, "スタンプを取得しました。現在 $currentPoints pt", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.confirmPointsButton).setOnClickListener {
            val intent = Intent(this, PointActivity::class.java)
            startActivity(intent)
        }
    }
}
