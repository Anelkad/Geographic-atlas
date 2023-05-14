package com.example.geographicatlas.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.geographicatlas.R
import com.example.geographicatlas.databinding.CountryListItemBinding
import com.example.geographicatlas.models.Country

class CountryListAdapter(
    var countryList: ArrayList<Country>
): RecyclerView.Adapter<CountryListAdapter.HolderCountry>() {

    lateinit var binding: CountryListItemBinding

    inner class HolderCountry(itemView: View): RecyclerView.ViewHolder(itemView){
        val flag = binding.countryFlagImageView
        val name = binding.CountryName
        val capital = binding.CountryCapital
        val expandButton = binding.expandButton
        val expandedDetails = binding.expandedDetails
        val population = binding.populationValue
        val area = binding.areaValue
        val currencies = binding.currenciesValue
        val countryDetailsButton = binding.countryDetailsButton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderCountry {
        binding = CountryListItemBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        )
        return HolderCountry(binding.root)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: HolderCountry, position: Int) {
        val country = countryList[position]
        val name = country.name?.common
        val flagImage = country.flags?.png
        val capital = if (country.capital.isNotEmpty()) country.capital[0] else ""
        val population = country.population
        val area = country.area
        val currencies = country.currencies.keys

        Glide
            .with(holder.flag.context)
            .load(flagImage)
            .placeholder(R.drawable.progress_animation)
            .error(R.drawable.baseline_image_not_supported_24)
            .into(holder.flag)

        holder.name.text = name
        holder.capital.text = capital
        holder.population.text = population.toString()
        holder.area.text = area.toString()
        holder.currencies.text = "$currencies"

        var rotationAngle = 0

        holder.expandButton.setOnClickListener {
            rotationAngle = if (rotationAngle == 0) 180 else 0
            holder.expandedDetails.isVisible = !holder.expandedDetails.isVisible
            holder.expandButton.animate().rotation(rotationAngle.toFloat()).start()
        }
    }

}