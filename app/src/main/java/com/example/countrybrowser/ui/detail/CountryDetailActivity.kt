package com.example.countrybrowser.ui.detail

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.countrybrowser.R

class CountryDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_detail)

        val name = intent.getStringExtra("COUNTRY_NAME")
        val official = intent.getStringExtra("COUNTRY_OFFICIAL")
        val capital = intent.getStringExtra("COUNTRY_CAPITAL")
        val region = intent.getStringExtra("COUNTRY_REGION")
        val population = intent.getLongExtra("COUNTRY_POPULATION", 0)
        val flagUrl = intent.getStringExtra("COUNTRY_FLAG")
        val languages = intent.getStringExtra("COUNTRY_LANGUAGES")

        findViewById<TextView>(R.id.tvNameDetail).text = name
        findViewById<TextView>(R.id.tvOfficialName).text = official
        findViewById<TextView>(R.id.tvCapitalDetail).text = "Capital: $capital"
        findViewById<TextView>(R.id.tvRegionDetail).text = "Region: $region"
        findViewById<TextView>(R.id.tvPopulationDetail).text = "Population: ${String.format("%,d", population)}"
        findViewById<TextView>(R.id.tvLanguagesDetail).text = "Languages: $languages"

        Glide.with(this)
            .load(flagUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .into(findViewById<ImageView>(R.id.imgFlagDetail))
    }
}