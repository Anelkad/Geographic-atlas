package com.example.geographicatlas.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.geographicatlas.databinding.RegionCountriesItemBinding
import com.example.geographicatlas.models.Country

class RegionsCountryListAdapter(
    val regionPairList: List<Pair<String, ArrayList<Country>>>,
    val learnMoreClickListener:((String)-> Unit)
) : RecyclerView.Adapter<RegionsCountryListAdapter.HolderRegion>() {

    lateinit var countryListAdapter: CountryListAdapter
    lateinit var binding: RegionCountriesItemBinding

    inner class HolderRegion(itemView: View): RecyclerView.ViewHolder(itemView){
        val name = binding.regionTitle
        val countryList = binding.countryListByRegion
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HolderRegion {
        binding = RegionCountriesItemBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        )
        return HolderRegion(binding.root)
    }

    override fun onBindViewHolder(holder: HolderRegion, position: Int) {
        val region = regionPairList[position].first
        val countryList = regionPairList[position].second

        holder.name.text = region

        countryListAdapter = CountryListAdapter(countryList,learnMoreClickListener)
        holder.countryList.adapter = countryListAdapter

    }

    override fun getItemCount(): Int {
    return regionPairList.size
    }

}