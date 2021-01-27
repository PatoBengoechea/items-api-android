package com.example.countries.app.ui.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.countries.R
import com.example.countries.app.service.Item
import com.example.countries.app.service.Service
import kotlinx.coroutines.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.math.log

class DetailActivity : AppCompatActivity() {

    // Views

    // Override functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val itemId = intent.getStringExtra("ITEM_ID")
        getItem(itemId!!)

    }

    // Private functions
    private fun getItem(id: String) {
        val mainActivityJob = Job()
        val errorHandler = CoroutineExceptionHandler { _, exception ->
            Log.d("DetailActivity", exception.toString())
            AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(exception.message)
                .setPositiveButton(android.R.string.ok) { _, _ ->}
                .setIcon(android.R.drawable.stat_notify_error).show()
        }
        val coroutineScope = CoroutineScope(mainActivityJob + Dispatchers.Main)
        coroutineScope.launch(errorHandler) {
            val item = Service().getItem(id)
            val titleTextView = findViewById<TextView>(R.id.titleTextView)
            titleTextView.text = item.title
        }
    }

    private fun customize() {
//        titleTextView.setTextColor(Color.YELLOW)
    }
}