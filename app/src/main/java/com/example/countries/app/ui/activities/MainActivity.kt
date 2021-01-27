package com.example.countries.app.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.countries.R
import com.example.countries.app.service.Service
import com.example.countries.app.ui.adapters.CountriesAdapter
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {


    // Override Function
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViews()

        getAllCountries()
    }

    // PRIVATE FUNCTIONS
    private fun bindViews() {

    }

    private fun getAllCountries() {
        val mainActivityJob = Job()
        val errorHandler = CoroutineExceptionHandler { _, exception ->
            Log.d("MainActivity", exception.toString())
            AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(exception.message)
                .setPositiveButton(android.R.string.ok) { _, _ ->}
                .setIcon(android.R.drawable.stat_notify_error).show()

        }

        val coroutineScope = CoroutineScope(mainActivityJob + Dispatchers.Main)
        coroutineScope.launch(errorHandler) {
            val itemsReponse = Service().searchItems()
            val contriesView = findViewById<RecyclerView>(R.id.countryRecyclerView)
            val manager: LinearLayoutManager = LinearLayoutManager(applicationContext)
            contriesView.layoutManager = manager
            contriesView.adapter = CountriesAdapter(itemsReponse.results)
        }
    }
}
