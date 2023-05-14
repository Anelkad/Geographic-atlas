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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.geographicatlas.R
import com.example.geographicatlas.adapters.RegionsCountryListAdapter
import com.example.geographicatlas.databinding.FragmentCountryDetailsBinding
import com.example.geographicatlas.utils.Resource
import com.example.geographicatlas.viewmodels.CountryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountryDetailsFragment : Fragment() {

    lateinit var binding: FragmentCountryDetailsBinding
    val arg: CountryDetailsFragmentArgs by navArgs()

    private val countryViewModel by activityViewModels<CountryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        countryViewModel.getCountryDetails(arg.cca2)

        binding = FragmentCountryDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        countryViewModel.countryDetailsState.observe(
            viewLifecycleOwner, Observer{
                when(it){
                    is Resource.Failure -> {
                        binding.progressBar.isVisible = false
                    }
                    is Resource.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    is Resource.Success -> {
                        binding.progressBar.isVisible = false
                        Log.d("country detail",it.getSuccessResult().toString())
                    }
                    else -> Unit
                }
            }
        )

        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

    }

}