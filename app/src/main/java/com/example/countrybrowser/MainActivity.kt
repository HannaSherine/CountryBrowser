package com.example.countrybrowser

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.countrybrowser.ui.list.CountryListActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, CountryListActivity::class.java))
        finish() // so back button doesn't return to a blank screen
    }
}