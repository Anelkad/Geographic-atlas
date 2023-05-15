package com.example.geographicatlas.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.geographicatlas.databinding.ExpandedDetailsItemBinding

class ExpandedDetailsAdapter(
    val countryExpandedDetailsPair: List<Pair<String,String>>
): RecyclerView.Adapter<ExpandedDetailsAdapter.HolderDetail>()  {

    lateinit var binding: ExpandedDetailsItemBinding

    inner class HolderDetail(itemView: View): RecyclerView.ViewHolder(itemView){
        val property = binding.propertyText
        val value = binding.valueText
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExpandedDetailsAdapter.HolderDetail {
        binding = ExpandedDetailsItemBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        )
        return HolderDetail(binding.root)
    }

    override fun onBindViewHolder(holder: ExpandedDetailsAdapter.HolderDetail, position: Int) {
        val property = countryExpandedDetailsPair[position].first
        val value = countryExpandedDetailsPair[position].second

        holder.property.text = property
        holder.value.text = value
    }

    override fun getItemCount(): Int {
        return countryExpandedDetailsPair.size
    }
}