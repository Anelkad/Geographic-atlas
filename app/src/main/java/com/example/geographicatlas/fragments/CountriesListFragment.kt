package com.example.geographicatlas.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.geographicatlas.adapters.RegionsCountryListAdapter
import com.example.geographicatlas.databinding.FragmentCountriesListBinding
import com.example.geographicatlas.models.Country
import com.example.geographicatlas.utils.Resource
import com.example.geographicatlas.viewmodels.CountryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountriesListFragment : Fragment() {

    lateinit var binding: FragmentCountriesListBinding
    lateinit var regionAdapter: RegionsCountryListAdapter
    lateinit var regionMap: HashMap<String, ArrayList<Country>>
    lateinit var regionPairList: List<Pair<String, ArrayList<Country>>>

    val countryViewModel by activityViewModels<CountryViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCountriesListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        countryViewModel.getRegionsCountryList()
        countryViewModel.countryListState.observe(viewLifecycleOwner, Observer{
            when(it){
                is Resource.Failure -> {
                    binding.progressBar.isVisible = false
                }
                is Resource.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is Resource.Success -> {
                    binding.progressBar.isVisible = false
                    regionMap = it.getSuccessResult()
                    regionPairList = regionMap.toList()
                    regionAdapter = RegionsCountryListAdapter(regionPairList)
                    binding.regionList.adapter = regionAdapter
                    Log.i("country list", it.getSuccessResult().keys.toString())
                }
                else -> Unit
            }
        })
//        binding.button.setOnClickListener {
//            findNavController().navigate(R.id.action_countriesListFragment_to_countryDetailsFragment)
//        }
    }
}