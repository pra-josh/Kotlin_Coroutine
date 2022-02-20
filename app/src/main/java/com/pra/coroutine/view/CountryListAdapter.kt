package com.pra.coroutine.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pra.coroutine.R
import com.pra.coroutine.model.Country

class CountryListAdapter(var countries: ArrayList<Country>) :
    RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>() {

    fun updateCountries(newCountries: List<Country>) {
        countries.clear()
        countries.addAll(newCountries)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = CountryViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_country, parent, false)
    )


    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position])
    }

    override fun getItemCount() = countries.size


    class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val imageView = view.findViewById<ImageView>(R.id.imageView)
        private val countryName = view.findViewById<TextView>(R.id.name)
        private val countryCapital = view.findViewById<TextView>(R.id.capital)

        fun bind(country: Country) {
            countryName.text = country.countryName
            countryCapital.text = country.capital

            imageView.loadImage(country.flag)
        }

        fun ImageView.loadImage(uri: String?) {
            val options = RequestOptions().error(R.mipmap.ic_launcher_round)
            Glide.with(this.context)
                .setDefaultRequestOptions(options)
                .load(uri)
                .into(this)
        }
    }

}