package com.example.countries.app.ui.activities

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.countries.R
import com.example.countries.app.service.Service
import com.example.countries.app.ui.adapters.CountriesAdapter
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    lateinit var itemEdtiText: EditText
    lateinit var itemsRecyclerView: RecyclerView
    lateinit var itemProgressBar: ProgressBar
    var textToSearch: String = ""


    // Override Function
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViews()
    }

    // PRIVATE FUNCTIONS
    private fun bindViews() {
        itemEdtiText = findViewById(R.id.itemEditText)
        itemsRecyclerView = findViewById(R.id.countryRecyclerView)
        itemProgressBar = findViewById(R.id.itemsProgressBar)
        itemProgressBar.visibility =  View.GONE
        itemsRecyclerView.setBackgroundColor(Color.LTGRAY)
        itemEdtiText.hint = "Ingrese un item a buscar"

        itemEdtiText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textToSearch = s.toString()
            }
        })

        itemEdtiText.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    getItems(textToSearch)
                    val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputManager.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
                    return true;
                }
                return false;
            }
        })

    }



    private fun getItems(name: String) {
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
            itemProgressBar.visibility =  View.VISIBLE
            val itemsReponse = Service().searchItems(name)
            val manager: LinearLayoutManager = LinearLayoutManager(applicationContext)
            itemsRecyclerView.layoutManager = manager
            itemsRecyclerView.adapter = CountriesAdapter(itemsReponse.results)
            itemProgressBar.visibility =  View.GONE
        }
    }
}
