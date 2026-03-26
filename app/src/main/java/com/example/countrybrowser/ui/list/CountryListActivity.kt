package com.example.countrybrowser.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.countrybrowser.R
import com.example.countrybrowser.ui.detail.CountryDetailActivity
import com.example.countrybrowser.utils.Resource

class CountryListActivity : AppCompatActivity() {

    private lateinit var viewModel: CountryListViewModel
    private lateinit var adapter: CountryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_list)

        viewModel = ViewModelProvider(this)[CountryListViewModel::class.java]
        setupRecyclerView()
        setupSearch()
        observeCountries()
    }

    private fun setupRecyclerView() {
        adapter = CountryAdapter { country ->
            val intent = Intent(this, CountryDetailActivity::class.java)
            intent.putExtra("COUNTRY_NAME", country.name.common)
            intent.putExtra("COUNTRY_OFFICIAL", country.name.official)
            intent.putExtra("COUNTRY_CAPITAL", country.capital?.firstOrNull() ?: "N/A")
            intent.putExtra("COUNTRY_REGION", country.region)
            intent.putExtra("COUNTRY_POPULATION", country.population)
            intent.putExtra("COUNTRY_FLAG", country.flags.png)
            intent.putExtra("COUNTRY_LANGUAGES",
                country.languages?.values?.joinToString(", ") ?: "N/A")
            startActivity(intent)
        }
        findViewById<RecyclerView>(R.id.recyclerView).apply {
            layoutManager = LinearLayoutManager(this@CountryListActivity)
            adapter = this@CountryListActivity.adapter
        }
    }

    private fun setupSearch() {
        findViewById<SearchView>(R.id.searchView).setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?) = false
                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.search(newText ?: "")
                    return true
                }
            }
        )
    }

    private fun observeCountries() {
        viewModel.countries.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    findViewById<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE
                    findViewById<RecyclerView>(R.id.recyclerView).visibility = View.GONE
                    findViewById<TextView>(R.id.tvError).visibility = View.GONE
                }
                is Resource.Success -> {
                    findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
                    findViewById<RecyclerView>(R.id.recyclerView).visibility = View.VISIBLE
                    findViewById<TextView>(R.id.tvError).visibility = View.GONE
                    adapter.submitList(resource.data)
                }
                is Resource.Error -> {
                    findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
                    findViewById<RecyclerView>(R.id.recyclerView).visibility = View.GONE
                    findViewById<TextView>(R.id.tvError).apply {
                        visibility = View.VISIBLE
                        text = resource.message
                    }
                }
            }
        }
    }
}