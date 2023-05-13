package com.example.geographicatlas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.geographicatlas.databinding.FragmentCountriesListBinding


class CountriesListFragment : Fragment() {

    lateinit var binding: FragmentCountriesListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCountriesListBinding.inflate(inflater,container,false)

//        binding.button.setOnClickListener {
//            findNavController().navigate(R.id.action_countriesListFragment_to_countryDetailsFragment)
//        }

        return binding.root
    }
}