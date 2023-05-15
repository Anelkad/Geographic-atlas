package com.example.geographicatlas.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.geographicatlas.databinding.CountryDetailsListItemBinding

class CountryDetailsAdapter(
    val countryDetailsPair: List<Pair<String,String>>
): RecyclerView.Adapter<CountryDetailsAdapter.HolderCountry>() {

    lateinit var binding: CountryDetailsListItemBinding

    inner class HolderCountry(itemView: View): RecyclerView.ViewHolder(itemView) {
        val property = binding.propertyText
        val value = binding.valueText
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): HolderCountry {
        binding = CountryDetailsListItemBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        )
        return HolderCountry(binding.root)
    }

    override fun getItemCount(): Int {
        return countryDetailsPair.size
    }

    override fun onBindViewHolder(holder: HolderCountry, position: Int) {
        val property = countryDetailsPair[position].first
        val value = countryDetailsPair[position].second

        holder.property.text = property
        holder.value.text = value
    }
}