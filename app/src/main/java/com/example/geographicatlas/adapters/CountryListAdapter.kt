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
    var countryList: ArrayList<Country>,
    val learnMoreClickListener:((String)-> Unit)
): RecyclerView.Adapter<CountryListAdapter.HolderCountry>() {

    lateinit var binding: CountryListItemBinding
    lateinit var countryExpandedDetailsAdapter: CountryExpandedDetailsAdapter

    inner class HolderCountry(itemView: View): RecyclerView.ViewHolder(itemView){
        val flag = binding.countryFlagImageView
        val name = binding.CountryName
        val capital = binding.CountryCapital
        val expandButton = binding.expandButton
        val expandedDetails = binding.expandedDetails
        val expandedDetailsList = binding.expandedDetailsList
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
        val cca2 = country.cca2

        Glide
            .with(holder.flag.context)
            .load(flagImage)
            .placeholder(R.drawable.progress_animation)
            .error(R.drawable.baseline_image_not_supported_24)
            .into(holder.flag)

        holder.name.text = name
        holder.capital.text = capital

        countryExpandedDetailsAdapter = CountryExpandedDetailsAdapter(country.toExpandedDetailsMap().toList())
        holder.expandedDetailsList.adapter = countryExpandedDetailsAdapter

        holder.countryDetailsButton.setOnClickListener { learnMoreClickListener(cca2) }
        holder.expandedDetails.isVisible = country.isExpanded

        var rotationAngle = 0
        if (country.isExpanded) {
            holder.expandButton.rotation = 180F
            rotationAngle = 180
        }
        holder.expandButton.setOnClickListener {
            if (!country.isExpanded)
                rotationAngle = if (rotationAngle == 0) 180 else 0
            else
                rotationAngle = if (rotationAngle == 180) 0 else 180

            country.changeExpandStatus()
            holder.expandedDetails.isVisible = country.isExpanded
            holder.expandButton.animate().rotation(rotationAngle.toFloat()).start()
            //Log.i("Rotation angle", rotationAngle.toString())
        }
    }

}