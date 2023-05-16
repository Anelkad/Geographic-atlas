package com.example.geographicatlas.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.geographicatlas.R
import com.example.geographicatlas.adapters.RegionsCountryListAdapter
import com.example.geographicatlas.databinding.FragmentCountriesListBinding
import com.example.geographicatlas.models.Country
import com.example.geographicatlas.utils.Resource
import com.example.geographicatlas.viewmodel.CountryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountriesListFragment : Fragment() {

    lateinit var binding: FragmentCountriesListBinding
    lateinit var regionAdapter: RegionsCountryListAdapter
    lateinit var regionPairList: List<Pair<String, ArrayList<Country>>>

    private val countryViewModel by activityViewModels<CountryViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        countryViewModel.getRegionsCountryList()
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCountriesListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
                    regionPairList = it.getSuccessResult()
                    regionAdapter = RegionsCountryListAdapter(regionPairList) {
                        val bundle = Bundle().apply {
                            putString("cca2", it)
                        }
                        findNavController().navigate(
                            R.id.action_countriesListFragment_to_countryDetailsFragment,
                            bundle
                        )
                    }
                    binding.regionList.adapter = regionAdapter
                }
                else -> Unit
            }
        })
    }
}