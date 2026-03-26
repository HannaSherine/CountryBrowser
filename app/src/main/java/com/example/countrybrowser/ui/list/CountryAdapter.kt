package com.example.countrybrowser.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.countrybrowser.R
import com.example.countrybrowser.data.model.Country

class CountryAdapter(
    private val onClick: (Country) -> Unit
) : ListAdapter<Country, CountryAdapter.ViewHolder>(DiffCallback()) {

    class DiffCallback : DiffUtil.ItemCallback<Country>() {
        override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
            return oldItem.name.common == newItem.name.common
        }
        override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
            return oldItem == newItem
        }
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_country, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country = getItem(position)  // ← only change here

        holder.view.apply {
            findViewById<TextView>(R.id.tvCountryName).text = country.name.common
            findViewById<TextView>(R.id.tvCapital).text =
                country.capital?.firstOrNull() ?: "No capital"
            findViewById<TextView>(R.id.tvRegion).text = country.region

            Glide.with(context)
                .load(country.flags.png)
                .into(findViewById(R.id.imgFlag))

            setOnClickListener { onClick(country) }
        }
    }
}